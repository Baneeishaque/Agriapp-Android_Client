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

public class Addnotification extends Activity {
	ProgressDialog pd;
	View v;
	EditText title,date,content;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnotification);
		title=(EditText) findViewById(R.id.title);
		date=(EditText) findViewById(R.id.date);
		content=(EditText) findViewById(R.id.content);
	}
	public void add(View v){
		pd=ProgressDialog.show(this,"", "Adding Crops...");
		new Thread(new Runnable() {
			public void run() {
				addnotif();
				
			}

			
		}).start();
	}
	private void addnotif() {
		// TODO Auto-generated method stub
		try
		{
			httpcnt=new DefaultHttpClient();
			httpost=new HttpPost("http://"+Globaldata.ip+"/agriappserver/android/addnotification.php");
			nvp=new ArrayList<NameValuePair>(4);
			nvp.add(new BasicNameValuePair("title",title.getText().toString()));
			nvp.add(new BasicNameValuePair("datetime",date.getText().toString()));
			nvp.add(new BasicNameValuePair("content",content.getText().toString()));
			
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
					if(response.equals("Add nontification successfully"))
					{
						Toast.makeText(Addnotification.this,"content_notification added successfully!", Toast.LENGTH_LONG).show();
						//clear(v);
					}
					else
					{
						Toast.makeText(Addnotification.this,"content_notification addition failure!", Toast.LENGTH_LONG).show();
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
					Toast.makeText(Addnotification.this,"error"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
	}
	
}
