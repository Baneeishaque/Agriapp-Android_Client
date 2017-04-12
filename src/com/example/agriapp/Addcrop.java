package com.example.agriapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Addcrop extends Activity {
	EditText crop,waterlow,waterhigh,templow,temphigh,stype,phlow,phhigh;
	ProgressDialog pd;
	View v;
	
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcrop);
		crop=(EditText) findViewById(R.id.name);
		waterlow=(EditText) findViewById(R.id.waterlow);
		waterhigh=(EditText) findViewById(R.id.waterhigh);
		templow=(EditText) findViewById(R.id.templow);
		temphigh=(EditText) findViewById(R.id.temphigh);
		stype=(EditText) findViewById(R.id.stype);
		phlow=(EditText) findViewById(R.id.phlow);
		phhigh=(EditText) findViewById(R.id.phhigh);

		
	}
	public void clear(View v)
	{
		crop.setText(null);
		waterlow.setText(null);
		waterhigh.setText(null);
		templow.setText(null);
		temphigh.setText(null);
		stype.setText(null);
		phlow.setText(null);
		phhigh.setText(null);

	}
	public void save(View v)
	{
		pd=ProgressDialog.show(this,"", "Adding Crops...");
		new Thread(new Runnable() {
			public void run() {
				addcrop();
				
			}

			
		}).start();
	}
	private void addcrop() {
		// TODO Auto-generated method stub
		try
		{
			httpcnt=new DefaultHttpClient();
			httpost=new HttpPost("http://"+General_Data.SERVER_APPLICATION_ADDRESS+"/agriappserver/android/addcrop.php");
			nvp=new ArrayList<NameValuePair>(9);
			nvp.add(new BasicNameValuePair("name",crop.getText().toString()));
			nvp.add(new BasicNameValuePair("water_availability_low",waterlow.getText().toString()));
			nvp.add(new BasicNameValuePair("water_availability_high",waterhigh.getText().toString()));
			nvp.add(new BasicNameValuePair("Avg_temperature_low",templow.getText().toString()));
			nvp.add(new BasicNameValuePair("Avg_temperature_high",temphigh.getText().toString()));
			nvp.add(new BasicNameValuePair("soil_type",stype.getText().toString()));
			nvp.add(new BasicNameValuePair("pH_low",phlow.getText().toString()));
			nvp.add(new BasicNameValuePair("pH_high",phhigh.getText().toString()));
			SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
	                Context.MODE_PRIVATE);
			nvp.add(new BasicNameValuePair("provider",settings.getString("user_id","0")));
			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s=new BasicResponseHandler();
			response=httpcnt.execute(httpost,s);
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//Toast.makeText(Addcrop.this,response, Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
					pd.dismiss();
					if(response.equals("0"))
					{
						Toast.makeText(Addcrop.this,"crop added successfully!", Toast.LENGTH_LONG).show();
						clear(v);
					}
					else
					{
						Toast.makeText(Addcrop.this,"crop addition failure!", Toast.LENGTH_LONG).show();
					}
				}
			});
		}
		catch(final Exception e)
		{
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Addcrop.this,"error"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
	}
}
