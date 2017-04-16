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
import android.widget.TextView;
import android.widget.Toast;

public class Product_seller extends Activity {
	
	
	DefaultHttpClient httpcnt;
    HttpPost httpost;
    ArrayList<NameValuePair> nvp;
    String response;
    ArrayList<String> spinner_list;
    ProgressDialog pd;

  
    TextView txt_date,txt_title, txt_content;
	
	TextView name,cost,quantity;
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_seller);
		name=(TextView) findViewById(R.id.name);
		cost=(TextView) findViewById(R.id.cost);
		quantity=(TextView) findViewById(R.id.quantity);
		name.setText(getIntent().getStringExtra
                ("name"));
		cost.setText(getIntent().getStringExtra
                ("unit_cost"));
		quantity.setText(getIntent().getStringExtra
                ("minimum_quantity"));
		
	}
	
	public void edit(View v)
	{
    	Intent i = new Intent(this, Sellproduct_edit.class);
    	i.putExtra("name", name.getText().toString());
    	i.putExtra("cost", cost.getText().toString());
    	i.putExtra("quantity",quantity.getText().toString());
    	i.putExtra("id", getIntent().getStringExtra
    	        ("id"));
    	
		startActivity(i);

	}
	
	public void del(View v)
	{
		pd = ProgressDialog.show(this, "", "Please wait...");
        new Thread(new Runnable() {
            public void run() {
                delete_blog();

            }


        }).start();

	}
	
	private void delete_blog() {
        // TODO Auto-generated method stub
        try {
            httpcnt = new DefaultHttpClient();
            httpost = new HttpPost("http://" + General_Data.SERVER_APPLICATION_ADDRESS +
                    "/agriappserver/android/deleteproduct.php");
            nvp = new ArrayList<NameValuePair>(1);
            nvp.add(new BasicNameValuePair("id", getIntent().getStringExtra
                    ("id")));


            httpost.setEntity(new UrlEncodedFormEntity(nvp));
            ResponseHandler<String> s = new BasicResponseHandler();
            response = httpcnt.execute(httpost, s);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                   // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                	Log.d(General_Data.TAG, response);
					pd.dismiss();
					if(response.equals("0"))
					{
						Toast.makeText(getApplicationContext(),"product deleted successfully!", Toast.LENGTH_LONG).show();
						Intent i=new Intent(getApplicationContext(),Productlist.class);
						startActivity(i);
						finish();
					}
					else
					{
						Toast.makeText(getApplicationContext(),"product deletion failure!", Toast.LENGTH_LONG).show();
					}
                    
                }
            });
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast
                            .LENGTH_LONG).show();
                    Log.d(General_Data.TAG, e.getLocalizedMessage());
                    pd.dismiss();
                }
            });
        }
	}

	
}
