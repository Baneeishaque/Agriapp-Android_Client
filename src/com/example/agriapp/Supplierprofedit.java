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
import android.widget.TextView;
import android.widget.Toast;

public class Supplierprofedit extends Activity {
	
	ProgressDialog pd;
	EditText name,off_address,god_address,contact;	
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.supplierprofedit);
		name=(EditText) findViewById(R.id.name);
		off_address=(EditText) findViewById(R.id.office);
		god_address=(EditText) findViewById(R.id.godown);
		contact=(EditText) findViewById(R.id.contact);
		name.setText(getIntent().getStringExtra
                ("name"));
		off_address.setText(getIntent().getStringExtra
                ("office_address"));
		god_address.setText(getIntent().getStringExtra
                ("godown_address"));
		contact.setText(getIntent().getStringExtra
                ("contact"));
	}

	
	public void add(View v){
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
			httpost=new HttpPost("http://"+General_Data.SERVER_APPLICATION_ADDRESS+"/agriappserver/android/upsupplier.php");
			nvp=new ArrayList<NameValuePair>(5);
			nvp.add(new BasicNameValuePair("name",name.getText().toString()));
			nvp.add(new BasicNameValuePair("office_address",off_address.getText().toString()));
			nvp.add(new BasicNameValuePair("godown_address",god_address.getText().toString()));
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
					if(response.equals("Update supplier details  successfully"))
					{
						Toast.makeText(Supplierprofedit.this,"Supplier updated successfully!", Toast.LENGTH_LONG).show();
						Intent i=new Intent(Supplierprofedit.this,Supplierprof.class);
						startActivity(i);
						finish();
						//clear(v);
					}
					else
					{
						Toast.makeText(Supplierprofedit.this,"Supplier updation failure!", Toast.LENGTH_LONG).show();
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
					Toast.makeText(Supplierprofedit.this,"error"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
	}
	
}
