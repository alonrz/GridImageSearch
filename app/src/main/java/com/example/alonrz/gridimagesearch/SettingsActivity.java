package com.example.alonrz.gridimagesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;


public class SettingsActivity extends ActionBarActivity {

    private SettingsClass settings;
    Spinner spImageSize;
    Spinner spColorFilters;
    Spinner spImageType;
    EditText etSiteFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settings = (SettingsClass) getIntent().getSerializableExtra("settings");
        //Get the views
        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spColorFilters = (Spinner) findViewById(R.id.spColorFilters);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);

        //preset fields
        setFields();

    }

    private void setFields() {
        setSpinnerToValue(spImageSize, settings.getImageSize());
        setSpinnerToValue(spColorFilters, settings.getColorFilter());
        setSpinnerToValue(spImageType, settings.getImageType());
        etSiteFilter.setText(settings.getSiteFilter());
    }

    public void setSpinnerToValue(Spinner spinner, String value) {
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break; // terminate loop
            }
        }
        spinner.setSelection(index);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSubmit(View view) {

        //Extract data if needed to be sent
        saveSettings();

        Intent data = new Intent();
        data.putExtra("settings", this.settings);
        setResult(RESULT_OK, data);
        this.finish();
    }

    private void saveSettings() {
        //image size

        String imageSize = spImageSize.getSelectedItem().toString();
        this.settings.setImageSize(imageSize);
        //image color

        String colorFilters = spColorFilters.getSelectedItem().toString();
        this.settings.setColorFilter(colorFilters);
        //image type

        String imageType = spImageType.getSelectedItem().toString();
        this.settings.setImageType(imageType);
        //image site

        String siteFilter = etSiteFilter.getText().toString();
        this.settings.setSiteFilter(siteFilter);

    }
}
