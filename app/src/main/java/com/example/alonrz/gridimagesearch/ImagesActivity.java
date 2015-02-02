package com.example.alonrz.gridimagesearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ImagesActivity extends ActionBarActivity {

    public static final String BASE_SEARCH_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";
    public static final String FILTER_IMG_SIZE = "&imgsz=";
    public static final String FILTER_IMG_COLOR = "&imgcolor=";
    public static final String FILTER_IMG_TYPE = "&imgtype=";
    public static final String CURSOR_POSITION = "&start=";
    private final int REQUEST_CODE = 888;

    private StaggeredGridView gvImages;
    private ArrayList<String> mImagesUrls;
    private ImageAdapter adapter;
    private SettingsClass settings;
    private SearchView searchView;
    private String searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        this.settings = SettingsClass.getInstance();
        gvImages = (StaggeredGridView)findViewById(R.id.gvImages);
        gvImages.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                if(page>=8) //Google search only supports upto 8 pages of seach.
                    return;
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                onImageSearch(page);
            }
        });

        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ImageActivity.class);
                i.putExtra("url", mImagesUrls.get(position));
                startActivity(i);
            }
        });

        mImagesUrls = new ArrayList<>();
        adapter = new ImageAdapter(this, mImagesUrls);
        gvImages.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pictures, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                searchString = "&q=" + s;
                mImagesUrls.clear();
                adapter.notifyDataSetChanged();
                int cursorStart = 0;
                onImageSearch(cursorStart);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {
            this.settings = (SettingsClass) data.getSerializableExtra("settings");
            onImageSearch(0);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            i.putExtra("settings", this.settings);
            startActivityForResult(i, REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method will NOT clear the image array and is used to add more queries with new cursor position.
     * @param cursorStart
     */
    private void onImageSearch(int cursorStart) {
        searchString = addQueryArgs(searchString, cursorStart);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_SEARCH_URL + searchString, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("PicturesActivity", "JSON received successfully");
                loadImagesFromJson(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("PicturesActivity", "JSON failed to return from internet");
            }
        });
    }

    private String addQueryArgs(String searchString, int cursorStart) {
        StringBuilder builder = new StringBuilder(searchString);
        if(settings.getImageSize().isEmpty() == false)
            builder.append(FILTER_IMG_SIZE).append(settings.getImageSize());
        if(settings.getColorFilter().isEmpty() == false)
            builder.append(FILTER_IMG_COLOR).append(settings.getColorFilter());
        if(settings.getImageType().isEmpty() == false)
            builder.append(FILTER_IMG_TYPE).append(settings.getImageType());

        builder.append(CURSOR_POSITION).append(cursorStart);

        return builder.toString();
    }

    private void loadImagesFromJson(JSONObject response)
    {
        try {
            //responseData --> @results --> url
            JSONArray results = response.getJSONObject("responseData").getJSONArray("results");
            for(int i=0; i<results.length();i++)
            {
                JSONObject image = results.getJSONObject(i);
                mImagesUrls.add(image.getString("url"));
            }
            adapter.notifyDataSetChanged();

        }
        catch (JSONException e)
        {
            Log.e("PicturesActivity", e.getMessage());
        }

    }
}
