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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Farmer_Accessory_View extends Activity {
	
	
	TextView crop,seller,accessoryname,category;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.farmer_accessory_view);
		crop=(TextView) findViewById(R.id.cropid);
		seller=(TextView) findViewById(R.id.sellerid);
		accessoryname=(TextView) findViewById(R.id.accessory);
		category=(TextView) findViewById(R.id.category);
		crop.setText(getIntent().getStringExtra
                    ("cropid"));
		seller.setText(getIntent().getStringExtra
                ("supplierid"));
		accessoryname.setText(getIntent().getStringExtra
                ("name"));
		category.setText(getIntent().getStringExtra
                ("role"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.farmeraccessoryview, menu);
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
	
	public void seller(View v)
    {
    	Intent i = new Intent(getApplicationContext(),Supplierdetails.class);
    	i.putExtra("notification_id",getIntent().getStringExtra("supplierid"));
    	startActivity(i);
    }
}
