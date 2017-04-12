package com.example.agriapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Crops extends Activity {

	   DefaultHttpClient httpcnt;
	    HttpPost httpost;
	    ArrayList<NameValuePair> nvp;
	    String response;
	    ArrayList<String> spinner_list;
	    ProgressDialog pd;
	    
	    TextView txt_name;
	    TextView txt_water;
	    TextView txt_temp;
	    TextView txt_soil;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crops);
		txt_name=(TextView) findViewById(R.id.txt_name);
		txt_water=(TextView) findViewById(R.id.txt_water);
		txt_soil=(TextView) findViewById(R.id.txt_soil);
		txt_temp=(TextView) findViewById(R.id.txt_temp);
		get_crop_thread();
	}

	public void get_crop_thread() {
        pd = ProgressDialog.show(this, "", "Please wait...");
        new Thread(new Runnable() {
            public void run() {
                get_crop();

            }


        }).start();

    }

    private void get_crop() {
        // TODO Auto-generated method stub
        try {
            httpcnt = new DefaultHttpClient();
            httpost = new HttpPost("http://" + General_Data.SERVER_APPLICATION_ADDRESS +
                    "/agriappserver/android/getcrop.php");
            nvp = new ArrayList<NameValuePair>(1);
            nvp.add(new BasicNameValuePair("id", getIntent().getStringExtra
                    ("crop_id")));


            httpost.setEntity(new UrlEncodedFormEntity(nvp));
            ResponseHandler<String> s = new BasicResponseHandler();
            response = httpcnt.execute(httpost, s);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
//                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    Log.d(General_Data.TAG, response);
                    pd.dismiss();


                    try {
                        JSONArray json = new JSONArray(response);
                        txt_name.setText(json.getJSONObject(0).getString("name"));
                        txt_temp.setText(json.getJSONObject(0).getString("Avg_temperature_low") + "-" + json.getJSONObject(0).getString("Avg_temperature_high"));
                        txt_water.setText(json.getJSONObject(0).getString
                                ("water_availability_low") + "-" + json.getJSONObject(0).getString
                                ("water_availability_high"));
                        txt_soil.setText(json.getJSONObject(0).getString("soil_type"));


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast
                                .LENGTH_LONG).show();
                        Log.d(General_Data.TAG, e.getLocalizedMessage());
                    }

                   

                }
            });
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast
                            .LENGTH_LONG).show();
                    Log.d(General_Data.TAG, e.getLocalizedMessage());
                    pd.dismiss();
                }
            });
        }
    }
}
