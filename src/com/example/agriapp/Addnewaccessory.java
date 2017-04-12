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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Addnewaccessory extends Activity {
	ProgressDialog pd;
	View v;
	EditText title,date,content;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	String [] role={"Day","Week"};
	private EditText accessoryname;
	private EditText category;
	private CheckBox rent;
	private CheckBox purchase;
	private Spinner rentsearch;
	private EditText rentamount;
	private EditText purchaseamount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_machinery);
		accessoryname=(EditText) findViewById(R.id.accessoryname);
		category=(EditText) findViewById(R.id.content2);
		rent=(CheckBox) findViewById(R.id.rent);
		purchase=(CheckBox) findViewById(R.id.purchase);
		rentsearch=(Spinner) findViewById(R.id.rentsearch);
		rentamount=(EditText) findViewById(R.id.rentamount);
		purchaseamount=(EditText) findViewById(R.id.purchaseamount);
		
		ArrayAdapter<String> roleadapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,role);
		roleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		rentsearch.setAdapter(roleadapter);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.addnewaccessory, menu);
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

public void add(View v){
	pd=ProgressDialog.show(this,"", "Adding Notification...");
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
		httpost=new HttpPost("http://"+General_Data.SERVER_APPLICATION_ADDRESS+"/agriappserver/android/addmachine.php");
		nvp=new ArrayList<NameValuePair>(7);
		nvp.add(new BasicNameValuePair("name",accessoryname.getText().toString()));
		nvp.add(new BasicNameValuePair("category",category.getText().toString()));
		nvp.add(new BasicNameValuePair("rent",String.valueOf(rent.isChecked())));
		nvp.add(new BasicNameValuePair("rentscheme",rentsearch.getSelectedItem().toString()));
		nvp.add(new BasicNameValuePair("rentamount",rentamount.getText().toString()));
		nvp.add(new BasicNameValuePair("purchase",String.valueOf(purchase.isChecked())));
		nvp.add(new BasicNameValuePair("purchaseamount",purchaseamount.getText().toString()));
		SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
				Context.MODE_PRIVATE);
		nvp.add(new BasicNameValuePair("id", settings.getString("user_id", "0")));

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
				if(response.equals("Add machine successfully"))
				{
					Toast.makeText(Addnewaccessory.this,"newaccessory added successfully!", Toast.LENGTH_LONG).show();
					//clear(v);
				}
				else
				{
					Toast.makeText(Addnewaccessory.this,"notification addition failure!", Toast.LENGTH_LONG).show();
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
				Toast.makeText(Addnewaccessory.this,"error"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				pd.dismiss();
			}
		});
	}
}

}

