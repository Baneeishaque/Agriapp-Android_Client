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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Labour_profile_edit extends Activity {
	
	ProgressDialog pd;
	View v;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;
	EditText name,address,contact,category;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.labour_profile_edit);
		name=(EditText) findViewById(R.id.name);
		address=(EditText) findViewById(R.id.address);
		contact=(EditText) findViewById(R.id.contact);
		category=(EditText) findViewById(R.id.category);
		name.setText(getIntent().getStringExtra
                ("name"));
		address.setText(getIntent().getStringExtra
                ("address"));
		contact.setText(getIntent().getStringExtra
                ("contact"));
		category.setText(getIntent().getStringExtra
                ("category"));

	}
	
	public void save(View v){
		pd=ProgressDialog.show(this,"", "Adding Notification...");
		new Thread(new Runnable() {
			public void run() {
				savings();
				
			}

			
		}).start();
	}
	
	private void savings() {
		// TODO Auto-generated method stub
		try
		{
			httpcnt=new DefaultHttpClient();
			httpost=new HttpPost("http://"+General_Data.SERVER_APPLICATION_ADDRESS+"/agriappserver/android/uplabour.php");
			nvp=new ArrayList<NameValuePair>(5);
			nvp.add(new BasicNameValuePair("name",name.getText().toString()));
			nvp.add(new BasicNameValuePair("address",address.getText().toString()));
			nvp.add(new BasicNameValuePair("category",category.getText().toString()));
			nvp.add(new BasicNameValuePair("contact",contact.getText().toString()));
			SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
	                Context.MODE_PRIVATE);
			nvp.add(new BasicNameValuePair("id",settings.getString("user_id","0")));
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
					if(response.equals("Update labour successfully"))
					{
						Toast.makeText(Labour_profile_edit.this,"Labour updated successfully!", Toast.LENGTH_LONG).show();
						Intent i=new Intent(Labour_profile_edit.this,Labourprofile.class);
						startActivity(i);
						finish();
						//clear(v);
					}
					else
					{
						Toast.makeText(Labour_profile_edit.this,"Labour updation failure!", Toast.LENGTH_LONG).show();
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
					Toast.makeText(Labour_profile_edit.this,"error"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.labour_profile_edit, menu);
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
}
