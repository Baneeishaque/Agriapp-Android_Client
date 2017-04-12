package com.example.agriapp.registration;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.agriapp.General_Data;
import com.example.agriapp.R;
import com.example.agriapp.homes.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registration4 extends Activity {

	ProgressDialog pd;
	EditText name,address,category,contact;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration4);
		name=(EditText) findViewById(R.id.name);
		address=(EditText) findViewById(R.id.address);
		contact=(EditText) findViewById(R.id.contact);
		category=(EditText) findViewById(R.id.category);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration4, menu);
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
	
	public void register(View v)
	{
		pd=ProgressDialog.show(this,"", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				reg();
				
			}
			
		}).start();			
	}
	private void reg() {
		// TODO Auto-generated method stub
		try
		{
			httpcnt=new DefaultHttpClient();
			httpost=new HttpPost("http://"+General_Data.SERVER_APPLICATION_ADDRESS+"/agriappserver/android/registerlabour.php");
			nvp=new ArrayList<NameValuePair>(5);
			nvp.add(new BasicNameValuePair("name",name.getText().toString()));
			nvp.add(new BasicNameValuePair("address",address.getText().toString()));
			nvp.add(new BasicNameValuePair("contact",contact.getText().toString()));
			nvp.add(new BasicNameValuePair("category",category.getText().toString()));
			nvp.add(new BasicNameValuePair("username", getIntent().getStringExtra
                    ("user")));
			nvp.add(new BasicNameValuePair("password", getIntent().getStringExtra
                    ("pass")));
			//nvp.add(new BasicNameValuePair("provider","0"));
			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s=new BasicResponseHandler();
			response=httpcnt.execute(httpost,s);
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Registration4.this,response, Toast.LENGTH_LONG).show();
					pd.dismiss();
					if(response.equals("labour registered successfully."))
					{
						Toast.makeText(Registration4.this,"Registration successfully completed!", Toast.LENGTH_LONG).show();
						//clear(v);
					}
					else
					{
						Toast.makeText(Registration4.this,"Registration failure!", Toast.LENGTH_LONG).show();
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
					Toast.makeText(Registration4.this,"error"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
		Intent i=new Intent(this,Login.class);
		startActivity(i);

	}
}
