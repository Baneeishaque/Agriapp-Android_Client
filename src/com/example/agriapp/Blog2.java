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
import android.widget.TextView;
import android.widget.Toast;

public class Blog2 extends Activity {

	 DefaultHttpClient httpcnt;
	    HttpPost httpost;
	    ArrayList<NameValuePair> nvp;
	    String response;
	    ArrayList<String> spinner_list;
	    ProgressDialog pd;
	    TextView txt_date,txt_title,txt_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blog2);
		txt_date=(TextView) findViewById(R.id.txt_date);
		txt_title=(TextView) findViewById(R.id.txt_title);
		txt_content=(TextView) findViewById(R.id.txt_content);
		pd = ProgressDialog.show(this, "", "Please wait...");
        new Thread(new Runnable() {
            public void run() {
                get_notification();

            }


        }).start();
	}

	private void get_notification() {
        // TODO Auto-generated method stub
        try {
            httpcnt = new DefaultHttpClient();
            httpost = new HttpPost("http://" + General_Data.SERVER_APPLICATION_ADDRESS +
                    "/agriappserver/android/getblog.php");
            nvp = new ArrayList<NameValuePair>(1);
            nvp.add(new BasicNameValuePair("id", getIntent().getStringExtra
                    ("notification_id")));


            httpost.setEntity(new UrlEncodedFormEntity(nvp));
            ResponseHandler<String> s = new BasicResponseHandler();
            response = httpcnt.execute(httpost, s);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                   // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    Log.d(General_Data.TAG, response);
                    pd.dismiss();


                    try {
                        JSONArray json = new JSONArray(response);
                        txt_date.setText(json.getJSONObject(0).getString("datetime"));
                        txt_content.setText(json.getJSONObject(0).getString("content"));
                        txt_title.setText(json.getJSONObject(0).getString("title"));


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
