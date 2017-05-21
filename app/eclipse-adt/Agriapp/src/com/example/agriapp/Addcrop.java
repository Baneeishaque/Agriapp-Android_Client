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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Addcrop extends Activity {
	EditText crop,water,avgtemp,stype,ph1;
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
		water=(EditText) findViewById(R.id.water);
		avgtemp=(EditText) findViewById(R.id.avgtemp);
		stype=(EditText) findViewById(R.id.stype);
		ph1=(EditText) findViewById(R.id.ph1);
		
	}
	public void clear(View v)
	{
		crop.setText(null);
		water.setText(null);
		avgtemp.setText(null);
		stype.setText(null);
		ph1.setText(null);
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
			httpost=new HttpPost("http://"+Globaldata.ip+"/agriappserver/android/addcrop.php");
			nvp=new ArrayList<NameValuePair>(6);
			nvp.add(new BasicNameValuePair("name",crop.getText().toString()));
			nvp.add(new BasicNameValuePair("water_availability",water.getText().toString()));
			nvp.add(new BasicNameValuePair("Avg_temperature",avgtemp.getText().toString()));
			nvp.add(new BasicNameValuePair("soil_type",stype.getText().toString()));
			nvp.add(new BasicNameValuePair("pH",ph1.getText().toString()));
			nvp.add(new BasicNameValuePair("provider","0"));
			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s=new BasicResponseHandler();
			response=httpcnt.execute(httpost,s);
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//Toast.makeText(Addcrop.this,response, Toast.LENGTH_LONG).show();
					pd.dismiss();
					if(response.equals("crop added successfully."))
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
