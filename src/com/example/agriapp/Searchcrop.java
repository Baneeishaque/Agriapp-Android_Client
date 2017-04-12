package com.example.agriapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import android.content.SharedPreferences;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.client.ResponseHandler;
import android.content.Context;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Searchcrop extends Activity {

	ArrayList<NameValuePair> nvp;
	String response;
	ArrayList<String> spinner_list;
	ProgressDialog pd;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	EditText water_availability;
	EditText average_temperature;
	EditText ph;

	Spinner spinner_soil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchcrop);
		spinner_soil = (Spinner) findViewById(R.id.spinner_soil);
		water_availability=(EditText)findViewById(R.id.waterlow);
		average_temperature=(EditText)findViewById(R.id.temp);
		ph=(EditText)findViewById(R.id.phlow);
		spinner_list = new ArrayList<String>();
		try {

			JSONArray json = new JSONArray(getIntent().getStringExtra("soil_response"));
			for (int i = 0; i < json.length(); i++) {

				// Populate spinner with country names
				spinner_list.add(json.getJSONObject(i).getString("soil_type"));

			}
			spinner_soil.setAdapter(new ArrayAdapter<String>(Searchcrop.this,
					android.R.layout.simple_spinner_dropdown_item, spinner_list));
		} catch (JSONException e) {
			Toast.makeText(Searchcrop.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
			Log.d(General_Data.TAG, e.getLocalizedMessage());
		}

	}

	public void search(View v) {
		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				search_crop();

			}

		}).start();

	}

	private void search_crop() {
	        // TODO Auto-generated method stub
	        try {
	            httpcnt = new DefaultHttpClient();
	            httpost = new HttpPost("http://" + General_Data.SERVER_APPLICATION_ADDRESS +
	                    "/agriappserver/android/get_crop_search.php");
	            nvp = new ArrayList<NameValuePair>(4);
	            nvp.add(new BasicNameValuePair("water_availability", water_availability.getText().toString()));

	            nvp.add(new BasicNameValuePair("Avg_temperature", average_temperature.getText().toString()));
	            nvp.add(new BasicNameValuePair("pH", ph.getText().toString()));

	            nvp.add(new BasicNameValuePair("soil_type", spinner_soil.getSelectedItem().toString()));


	            httpost.setEntity(new UrlEncodedFormEntity(nvp));
	            ResponseHandler<String> s = new BasicResponseHandler();
	            response = httpcnt.execute(httpost, s);
	            runOnUiThread(new Runnable() {

	                @Override
	                public void run() {
	                    // TODO Auto-generated method stub
	                    //Toast.makeText(Search_Crop.this, response, Toast.LENGTH_LONG).show();
	                    Log.d(General_Data.TAG, response);
	                    pd.dismiss();

	                    if (response.equals("[]")) {
	                        Toast.makeText(Searchcrop.this, "No Compatible Items, Please Consult " +
	                                "Officers....", Toast
	                                .LENGTH_LONG).show();

	                    } else {

	                        SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
	                                Context.MODE_PRIVATE);
	                        SharedPreferences.Editor editor = settings.edit();
	                        editor.putString("soil_type", spinner_soil.getSelectedItem().toString());
	                        editor.commit();
	                        Intent i=new Intent(getApplicationContext(),Availablecrop.class);
	                        i.putExtra("crop_search_response",response);
	                        startActivity(i);
	                    }





	                }
	            });
	        } catch (final Exception e) {
	            runOnUiThread(new Runnable() {

	                @Override
	                public void run() {
	                    // TODO Auto-generated method stub
	                    Toast.makeText(Searchcrop.this, "Error : " + e.getLocalizedMessage(), Toast
	                            .LENGTH_LONG).show();
	                    Log.d(General_Data.TAG, e.getLocalizedMessage());
	                    pd.dismiss();
	                }
	            });
	        }
	    }



}
