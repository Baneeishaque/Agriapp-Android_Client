package com.example.agriapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import com.example.agriapp.modals.Notification_Modal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Consultengg extends Activity {

	ListView list_notifications;

	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;
	ProgressDialog pd;
	ArrayList<String> item_list_array;

	ArrayList<Notification_Modal> notification_Modal_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consultengg);
		list_notifications=(ListView) findViewById(R.id.listView1);
		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				get_blog();

			}


		}).start();
	}

	public void engg1(View v)
	{
			Intent i=new Intent(this,Consultengg2.class);
			startActivity(i);
		
	}
	public void engg2(View v)
	{
			Intent i=new Intent(this,Consultengg2.class);
			startActivity(i);
		
	}
	public void eng1(View v)
	{
			Intent i=new Intent(this,Consultengg3.class);
			startActivity(i);
		
	}
	public void eng2(View v)
	{
			Intent i=new Intent(this,Consultengg3.class);
			startActivity(i);
		
	}
	
	private void get_blog() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost("http://" + General_Data.SERVER_APPLICATION_ADDRESS +
					"/agriappserver/android/getengineerall" +
					".php");

			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					//Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
					pd.dismiss();

					item_list_array = new ArrayList<String>();
					notification_Modal_list = new ArrayList<>();
					try {

						JSONArray json = new JSONArray(response);
						for (int i = 0; i < json.length(); i++) {


							// Populate spinner with country names
							item_list_array.add(json.getJSONObject(i).getString("name"));

							Notification_Modal sample_notificationModal = new Notification_Modal();
							sample_notificationModal.setId(json.getJSONObject(i).getString("id"));
							sample_notificationModal.setTitle(json.getJSONObject(i).getString
                                    ("name"));
							sample_notificationModal.setDatetime(json.getJSONObject(i).getString
									("address"));
							sample_notificationModal.setContent(json.getJSONObject(i).getString
									("contact"));

							notification_Modal_list.add(sample_notificationModal);


						}
						list_notifications
								.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
										R.layout.list_item_textview,
										item_list_array));
						list_notifications.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

								Intent i = new Intent(getApplicationContext(), Consultengg2.class);
								i.putExtra("notification_id", notification_Modal_list.get(position).getId());
								startActivity(i);
							}
						});
					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast
								.LENGTH_LONG).show();
						Log.d(General_Data.TAG, e.getLocalizedMessage());
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
