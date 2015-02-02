package com.example.alonrz.gridimagesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.ortiz.touch.TouchImageView;
import com.squareup.picasso.Picasso;


public class ImageActivity extends ActionBarActivity {

    String imageUrl;
    TouchImageView ivImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageUrl = getIntent().getStringExtra("url");
        ivImage = (TouchImageView) findViewById(R.id.ivImage);
        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.loading_image)
                .into(ivImage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        ImageView ivImage = (ImageView) findViewById(R.id.ivImage);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/html");

            i.putExtra(Intent.EXTRA_TEXT, imageUrl);

            startActivity(Intent.createChooser(i, "Share image using"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
