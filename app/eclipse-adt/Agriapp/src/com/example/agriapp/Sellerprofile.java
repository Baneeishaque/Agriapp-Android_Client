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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Sellerprofile extends Activity {
	
	EditText name,office,godown,contact;
	ProgressDialog pd;
	View v;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sellerprofile);
		name=(EditText) findViewById(R.id.name);
		office=(EditText) findViewById(R.id.office);
		godown=(EditText) findViewById(R.id.godown);
		contact=(EditText) findViewById(R.id.contact);
	}
	public void add(View v)
	{
		pd=ProgressDialog.show(this,"", "Adding Seller Profile...");
		new Thread(new Runnable() {
			public void run() {
				addseller();
				
			}

			
		}).start();
	}
	private void addseller() {
		// TODO Auto-generated method stub
		try
		{
			httpcnt=new DefaultHttpClient();
			httpost=new HttpPost("http://"+Globaldata.ip+"/agriappserver/android/registerceller.php");
			nvp=new ArrayList<NameValuePair>(6);
			nvp.add(new BasicNameValuePair("name",name.getText().toString()));
			nvp.add(new BasicNameValuePair("office_address",office.getText().toString()));
			nvp.add(new BasicNameValuePair("godown_address",godown.getText().toString()));
			nvp.add(new BasicNameValuePair("contact_no",contact.getText().toString()));
			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s=new BasicResponseHandler();
			response=httpcnt.execute(httpost,s);
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//Toast.makeText(Addcrop.this,response, Toast.LENGTH_LONG).show();
					pd.dismiss();
					if(response.equals("content_seller registered successfully"))
					{
						Toast.makeText(Sellerprofile.this,"crop added successfully!", Toast.LENGTH_LONG).show();
						
					}
					else
					{
						Toast.makeText(Sellerprofile.this,"crop addition failure!", Toast.LENGTH_LONG).show();
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
					Toast.makeText(Sellerprofile.this,"error"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
	}

}
