package com.example.agriapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Find_Accessories extends Activity {
	String[] role = { "Fertilizers", "Pesticides", "Seeds","Machines" };
	Spinner s1;
	ProgressDialog pd;
	View v;
	EditText key;

	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accessories);
		ArrayAdapter<String> roleadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, role);
		roleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s1 = (Spinner) findViewById(R.id.spinner_soil);
		s1.setAdapter(roleadapter);
		key = (EditText) findViewById(R.id.name);
	}

	public void search_accessories(View v) {
		// TODO login data to server...
		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				check_accessories();

			}

		}).start();
		/*
		 * if(s1.getSelectedItem().toString().equals("Fertilizer")) { Intent
		 * i=new Intent(this,Fertilizers.class); startActivity(i); }
		 */
	}

	private void check_accessories() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_APPLICATION_ADDRESS + "/agriappserver/android/get_accessories.php");
			nvp = new ArrayList<NameValuePair>(2);
			nvp.add(new BasicNameValuePair("type", s1.getSelectedItem().toString()));

			nvp.add(new BasicNameValuePair("key", key.getText().toString()));

			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
//					Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
					pd.dismiss();
					if (response.equals("[]")) {
						Toast.makeText(Find_Accessories.this, "No Compatible Items, Please Consult " + "Officers....",
								Toast.LENGTH_LONG).show();

					} else {

						SharedPreferences settings = getApplicationContext()
								.getSharedPreferences(General_Data.SHARED_PREFERENCE, Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = settings.edit();
						editor.putString("service_type", s1.getSelectedItem().toString());
						editor.commit();
						Intent i = new Intent(getApplicationContext(), Accessory_Search_List.class);
						i.putExtra("service_search_response", response);
						i.putExtra("key", key.getText().toString());
						i.putExtra("role",s1.getSelectedItem().toString());
						startActivity(i);
					}
					
				}
			});

		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Find_Accessories.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
							.show();
					Log.d(General_Data.TAG, e.getLocalizedMessage());
					pd.dismiss();
				}
			});
		}
	}

}
