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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Blog3 extends Activity {

	EditText title, content;
	ProgressDialog pd;
	View v;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blog3);
		title = (EditText) findViewById(R.id.title);
		content = (EditText) findViewById(R.id.content);
		title.setText(getIntent().getStringExtra("title"));
		content.setText(getIntent().getStringExtra("content"));

	}

	public void update(View v) {
		pd = ProgressDialog.show(this, "", "Updating...");
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
					"http://" + General_Data.SERVER_APPLICATION_ADDRESS + "/agriappserver/android/upblog.php");
			nvp = new ArrayList<NameValuePair>(3);
			nvp.add(new BasicNameValuePair("title", title.getText().toString()));
			nvp.add(new BasicNameValuePair("content", content.getText().toString()));
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
						Toast.makeText(Blog3.this, "Blog updated successfully!", Toast.LENGTH_LONG).show();
						Intent i = new Intent(Blog3.this, Myarticle.class);
						startActivity(i);
						finish();
						// clear(v);
					} else {
						Toast.makeText(Blog3.this, "Blog updation failure!", Toast.LENGTH_LONG).show();
					}
				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Blog3.this, "error" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
	}

}
