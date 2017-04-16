package com.example.agriapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import com.example.agriapp.modals.Product_Modal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Addaccessory extends Activity {

	String[] role = { "Fertilizers", "Pesticides", "Seeds" };
	private Spinner s1;

	ProgressDialog pd;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	

	ArrayList<String> item_list_array;

	ArrayList<Product_Modal> product_Modal_list;
	private Spinner s2;
	private EditText name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addaccessory);
		ArrayAdapter<String> roleadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, role);
		roleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s1 = (Spinner) findViewById(R.id.spinner_soil);
		s2 = (Spinner) findViewById(R.id.spinner_crop);
		s1.setAdapter(roleadapter);
		name=(EditText) findViewById(R.id.name);
		populate_soil_spinner_thread();
	}

	private void populate_soil_spinner_thread() {
		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				populate_soil_spinner();

			}

		}).start();
	}

	private void populate_soil_spinner() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_APPLICATION_ADDRESS + "/agriappserver/android/getcropall.php");
			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Toast.makeText(Farmer_Home.this, response,
					// Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
					pd.dismiss();
					if (response.equals("[]")) {
						Toast.makeText(getApplicationContext(), "No Compatible Items, Please Consult " + "Officers....",
								Toast.LENGTH_LONG).show();

					} else {
						item_list_array = new ArrayList<String>();
						product_Modal_list = new ArrayList<>();
						try {

							JSONArray json = new JSONArray(response);
							for (int i = 0; i < json.length(); i++) {

								// Populate spinner with country names
								item_list_array.add(json.getJSONObject(i).getString("name"));

								Product_Modal sample_productModal = new Product_Modal();
								sample_productModal.setId(json.getJSONObject(i).getString("id"));
								sample_productModal.setName(json.getJSONObject(i).getString("name"));
								
								product_Modal_list.add(sample_productModal);

							}
							ArrayAdapter<String> roleadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, item_list_array);
							roleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							s2.setAdapter(roleadapter);
						} catch (JSONException e) {
							Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(),
									Toast.LENGTH_LONG).show();
							Log.d(General_Data.TAG, e.getLocalizedMessage());
						}

					}

				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
							.show();
					Log.d(General_Data.TAG, e.getLocalizedMessage());
					pd.dismiss();
				}
			});
		}
	}

	
	public void search(View v){
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
			if(s1.getSelectedItem().toString().equals("Fertilizers"))
			{
				httpost=new HttpPost("http://"+General_Data.SERVER_APPLICATION_ADDRESS+"/agriappserver/android/addfertilizer.php");
				
			}
			else if(s1.getSelectedItem().toString().equals("Pesticides"))
			{
				httpost=new HttpPost("http://"+General_Data.SERVER_APPLICATION_ADDRESS+"/agriappserver/android/addpesticide.php");
				
			}
			else if(s1.getSelectedItem().toString().equals("Seeds"))
			{
				httpost=new HttpPost("http://"+General_Data.SERVER_APPLICATION_ADDRESS+"/agriappserver/android/addseed.php");
				
			}
			
			nvp=new ArrayList<NameValuePair>(4);
			nvp.add(new BasicNameValuePair("name",name.getText().toString()));
			//nvp.add(new BasicNameValuePair("datetime",date.getText().toString()));
			nvp.add(new BasicNameValuePair("cropid",product_Modal_list.get(s2.getSelectedItemPosition()).getId()));			
			SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
	                Context.MODE_PRIVATE);
			nvp.add(new BasicNameValuePair("supplierid",settings.getString("user_id","0")));
			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s=new BasicResponseHandler();
			response=httpcnt.execute(httpost,s);
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//Toast.makeText(Addcrop.this,response, Toast.LENGTH_LONG).show();
					pd.dismiss();
					if(response.equals("0"))
					{
						Toast.makeText(getApplicationContext(),"notification added successfully!", Toast.LENGTH_LONG).show();
						//clear(v);
					}
					else
					{
						Toast.makeText(getApplicationContext(),"notification addition failure!", Toast.LENGTH_LONG).show();
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
					Toast.makeText(getApplicationContext(),"error"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
	}
	
}
