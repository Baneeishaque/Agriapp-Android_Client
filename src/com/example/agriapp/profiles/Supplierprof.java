package com.example.agriapp.profiles;

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

import com.example.agriapp.General_Data;
import com.example.agriapp.Labour_profile_edit;
import com.example.agriapp.R;
import com.example.agriapp.Supplierprofedit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Supplierprof extends Activity {

	private TextView txt_user;
	private TextView txt_password;
	private TextView txt_name;
	private TextView txt_con;
	private TextView txt_off;
	private TextView txt_go;

	ProgressDialog pd;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.supplierprof);
		txt_user = (TextView) findViewById(R.id.txt_user);
		txt_password = (TextView) findViewById(R.id.txt_password);
		txt_name = (TextView) findViewById(R.id.txt_nme);
		txt_con = (TextView) findViewById(R.id.txt_con);
		txt_off = (TextView) findViewById(R.id.txt_off);
		txt_go = (TextView) findViewById(R.id.txt_go);
		get_supplierprof_thread();
	}

	public void get_supplierprof_thread() {
		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				get_supplierprof();

			}

		}).start();

	}

	private void get_supplierprof() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_APPLICATION_ADDRESS + "/agriappserver/android/get_supplier.php");
			nvp = new ArrayList<NameValuePair>(1);
			SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
					Context.MODE_PRIVATE);
			nvp.add(new BasicNameValuePair("id", settings.getString("user_id", "0")));

			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Toast.makeText(getApplicationContext(), response,
					// Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
					pd.dismiss();

					try {
						JSONArray json = new JSONArray(response);
						txt_user.setText(json.getJSONObject(0).getString("username"));
						txt_password.setText(json.getJSONObject(0).getString("password"));
						txt_name.setText(json.getJSONObject(0).getString("name"));
						txt_con.setText(json.getJSONObject(0).getString("contact"));

						txt_off.setText(json.getJSONObject(0).getString("office_address"));
						txt_go.setText(json.getJSONObject(0).getString("godown_address"));

					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
								.show();
						Log.d(General_Data.TAG, e.getLocalizedMessage());
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.supplierprof, menu);
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
	
	public void pro_edit(View v)
	{
		Intent i = new Intent(this, Supplierprofedit.class);
		i.putExtra("username", txt_user.getText().toString());
		i.putExtra("password",txt_password.getText().toString());
		i.putExtra("name",txt_name.getText().toString());
		i.putExtra("contact",txt_con.getText().toString());
		i.putExtra("office_address",txt_off.getText().toString());
		i.putExtra("godown_address",txt_go.getText().toString());
		startActivity(i);
	}
}
