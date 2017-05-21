package com.example.agriapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.agriapp.R.id;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Sellproduct extends Activity {
	
	EditText prod,cost,min;
	ImageView pic1;
	ProgressDialog pd;
	View v;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sellproduct);
		prod=(EditText) findViewById(R.id.name);
		cost=(EditText) findViewById(R.id.cost);
		min=(EditText) findViewById(R.id.office);
		pic1=(ImageView) findViewById(id.img1);
	}

	public void save(View v)
	{
		pd=ProgressDialog.show(this,"", "Adding Product...");
		new Thread(new Runnable() {
			public void run() {
				sellprod();
				
			}

			
		}).start();
	}
	private void sellprod() {
		// TODO Auto-generated method stub
		try
		{
			httpcnt=new DefaultHttpClient();
			httpost=new HttpPost("http://"+Globaldata.ip+"/agriappserver/android/addproduct.php");
			nvp=new ArrayList<NameValuePair>(6);
			nvp.add(new BasicNameValuePair("name",prod.getText().toString()));
			nvp.add(new BasicNameValuePair("unit_cost",cost.getText().toString()));
			nvp.add(new BasicNameValuePair("minimum_quantity",min.getText().toString()));
			nvp.add(new BasicNameValuePair("pic1","0"));
			nvp.add(new BasicNameValuePair("pic2","0"));
			nvp.add(new BasicNameValuePair("pic3","0"));
			nvp.add(new BasicNameValuePair("seller_id","0"));

			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s=new BasicResponseHandler();
			response=httpcnt.execute(httpost,s);
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//Toast.makeText(Addcrop.this,response, Toast.LENGTH_LONG).show();
					pd.dismiss();
					if(response.equals("product add successfully"))
					{
						Toast.makeText(Sellproduct.this,"Product added successfully!", Toast.LENGTH_LONG).show();
					}
					else
					{
						Toast.makeText(Sellproduct.this,"Product addition failure!", Toast.LENGTH_LONG).show();
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
					Toast.makeText(Sellproduct.this,"error"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
	}
}