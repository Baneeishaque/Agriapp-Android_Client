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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Labourprofile extends Activity {

	ProgressDialog pd;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;
	TextView txt_name, txt_address, txt_contact, txt_category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.labourprofile);
		txt_name = (TextView) findViewById(R.id.quantity);
		txt_address = (TextView) findViewById(R.id.textView4);
		txt_contact = (TextView) findViewById(R.id.txt_contact);
		txt_category = (TextView) findViewById(R.id.textView8);
		get_labourprofile_thread();

	}

	public void get_labourprofile_thread() {
		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				get_labourprofile();

			}

		}).start();

	}

	private void get_labourprofile() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_APPLICATION_ADDRESS + "/agriappserver/android/get_labour.php");
			nvp = new ArrayList<NameValuePair>(1);
			SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
					Context.MODE_PRIVATE);
			nvp.add(new BasicNameValuePair("id", settings.getString("user_id", "0")));

			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Toast.makeText(getApplicationContext(), response,
					// Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
					pd.dismiss();

					try {
						JSONArray json = new JSONArray(response);
						txt_name.setText(json.getJSONObject(0).getString("name"));
						txt_address.setText(json.getJSONObject(0).getString("address"));
						txt_contact.setText(json.getJSONObject(0).getString("contact"));
						txt_category.setText(json.getJSONObject(0).getString("category"));

					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
								.show();
						Log.d(General_Data.TAG, e.getLocalizedMessage());
					}

				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
							.show();
					Log.d(General_Data.TAG, e.getLocalizedMessage());
					pd.dismiss();
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.labourprofile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void pro_edit(View v)
	{
		Intent i = new Intent(this, Labour_profile_edit.class);
		i.putExtra("name", txt_name.getText().toString());
		i.putExtra("address",txt_address.getText().toString());
		i.putExtra("contact",txt_contact.getText().toString());
		i.putExtra("category",txt_category.getText().toString());
		startActivity(i);
	}
}
