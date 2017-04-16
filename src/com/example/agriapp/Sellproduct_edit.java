package com.example.agriapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.agriapp.profiles.Supplierprof;

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
import android.widget.EditText;
import android.widget.Toast;

public class Sellproduct_edit extends Activity {

	ProgressDialog pd;
	EditText name, cost, quantity;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sellproduct_edit);
		name = (EditText) findViewById(R.id.name);
		cost = (EditText) findViewById(R.id.cost);
		quantity = (EditText) findViewById(R.id.office);

		name.setText(getIntent().getStringExtra("name"));
		cost.setText(getIntent().getStringExtra("cost"));
		quantity.setText(getIntent().getStringExtra("quantity"));

	}

	public void edit(View v) {
		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				savings();

			}

		}).start();
	}

	private void savings() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_APPLICATION_ADDRESS + "/agriappserver/android/upproduct.php");
			nvp = new ArrayList<NameValuePair>(5);
			nvp.add(new BasicNameValuePair("name", name.getText().toString()));
			nvp.add(new BasicNameValuePair("unit_cost", cost.getText().toString()));
			nvp.add(new BasicNameValuePair("minimum_quantity", quantity.getText().toString()));
			SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
					Context.MODE_PRIVATE);
			nvp.add(new BasicNameValuePair("id", getIntent().getStringExtra("id")));
			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Toast.makeText(Addcrop.this,response,
					// Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
					pd.dismiss();
					if (response.equals("0")) {
						Toast.makeText(Sellproduct_edit.this, "Seller product updated successfully!", Toast.LENGTH_LONG)
								.show();
						Intent i = new Intent(Sellproduct_edit.this, Productlist.class);
						startActivity(i);
						finish();
						// clear(v);
					} else {
						Toast.makeText(Sellproduct_edit.this, "Seller product updation failure!", Toast.LENGTH_LONG)
								.show();
					}
				}
			});
		}

		catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Sellproduct_edit.this, "error" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
	}

}
