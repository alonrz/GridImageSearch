package com.example.alonrz.gridimagesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PicturesActivity extends ActionBarActivity {

    public static final String BASE_SEARCH_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";
    private final int REQUEST_CODE = 888;

    private GridView gvImages;
    private ArrayList<String> mImagesUrls;
    private ImageAdapter adapter;
    private SettingsClass settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        this.settings = new SettingsClass();
        gvImages = (GridView)findViewById(R.id.gvImages);
        mImagesUrls = new ArrayList<>();
        adapter = new ImageAdapter(this, mImagesUrls);
        gvImages.setAdapter(adapter);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //What to do with a image clicked. Optional
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pictures, menu);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {
            this.settings = (SettingsClass)data.getSerializableExtra("settings");
            Toast.makeText(this, "Object settings returned", Toast.LENGTH_LONG).show();
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



    public void onImageSearch(View view) {
        EditText etSearchString = (EditText) findViewById(R.id.etSearchString);
        String searchString = "&q=" + etSearchString.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_SEARCH_URL + searchString, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("PicturesActivity", "JSON returned successfuly from internet");
                loadImagesFromJson(response);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("PicturesActivity", "JSON failed to return from internet");
            }
        });

    }

    private void loadImagesFromJson(JSONObject response)
    {
        mImagesUrls.clear();
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
