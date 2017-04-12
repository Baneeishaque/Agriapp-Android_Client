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

public class Registration3 extends Activity {
	
	ProgressDialog pd;
	EditText name,officeaddress,contact,godownaddress;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration3);
		name=(EditText) findViewById(R.id.name);
		officeaddress=(EditText) findViewById(R.id.office);
		contact=(EditText) findViewById(R.id.contactno);
		godownaddress=(EditText) findViewById(R.id.godown);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration3, menu);
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
			if(getIntent().getStringExtra
                    ("role").equals("Seller"))
			{
				httpost=new HttpPost("http://"+General_Data.SERVER_APPLICATION_ADDRESS+"/agriappserver/android/registerseller.php");
			}
			if(getIntent().getStringExtra
                    ("role").equals("Supplier"))
			{
				
				httpost=new HttpPost("http://"+General_Data.SERVER_APPLICATION_ADDRESS+"/agriappserver/android/registersupplier.php");
			}
			nvp=new ArrayList<NameValuePair>(6);
			nvp.add(new BasicNameValuePair("name",name.getText().toString()));
			nvp.add(new BasicNameValuePair("office_address",officeaddress.getText().toString()));
			nvp.add(new BasicNameValuePair("contact",contact.getText().toString()));
			nvp.add(new BasicNameValuePair("godown_address",godownaddress.getText().toString()));
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
					Toast.makeText(Registration3.this,response, Toast.LENGTH_LONG).show();
					pd.dismiss();
					if(response.equals("Seller registered successfully."))
					{
						Toast.makeText(Registration3.this,"Registration successfully completed!", Toast.LENGTH_LONG).show();
						//clear(v);
					}
					else if(response.equals("supplier registered successfully."))
					{
						Toast.makeText(Registration3.this,"Registration successfully completed!", Toast.LENGTH_LONG).show();
						//clear(v);
					}
					else
					{
						Toast.makeText(Registration3.this,"Registration failure!", Toast.LENGTH_LONG).show();
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
					Toast.makeText(Registration3.this,"error"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
		Intent i=new Intent(this,Login.class);
		startActivity(i);

	}


}
