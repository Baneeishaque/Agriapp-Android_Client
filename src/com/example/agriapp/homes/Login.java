package com.example.agriapp.homes;

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
import com.example.agriapp.R;
import com.example.agriapp.registration.Registration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	EditText user, pass;

	ProgressDialog pd;
	View v;

	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		user = (EditText) findViewById(R.id.user);
		pass = (EditText) findViewById(R.id.pass);

	}

	public void reset(View v) {
		user.setText(null);
		pass.setText(null);
	}

	public void login(View view) {

		// TODO login data to server...
		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				checklogin();

			}

		}).start();
	}

	private void checklogin() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost("http://" + General_Data.SERVER_APPLICATION_ADDRESS + "/agriappserver/android/getlogin.php");
			nvp = new ArrayList<NameValuePair>(2);
			nvp.add(new BasicNameValuePair("username", user.getText().toString()));

			nvp.add(new BasicNameValuePair("password", pass.getText().toString()));

			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					Log.d(General_Data.TAG, response);
					pd.dismiss();

					try {
						JSONArray json = new JSONArray(response);
						String count = json.getJSONObject(0).getString("count");
						switch (count) {
						case "1":
							String role = json.getJSONObject(0).getString("role");
							Intent i;
							SharedPreferences settings;
							SharedPreferences.Editor editor;
							switch (role) {

							case "farmer":
								settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
										Context.MODE_PRIVATE);
								editor = settings.edit();
								editor.putString("login_status", "1");
								editor.putString("user_role", role);
								editor.putString("user_id", json.getJSONObject(0).getString("userid"));
								editor.commit();
								i = new Intent(Login.this, Farmer.class);
								startActivity(i);
								finish();
								break;
							case "officer":
								settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
										Context.MODE_PRIVATE);
								editor = settings.edit();
								editor.putString("login_status", "1");
								editor.putString("user_role", role);
								editor.putString("user_id", json.getJSONObject(0).getString("userid"));
								editor.commit();
								i = new Intent(Login.this, Officer.class);
								startActivity(i);
								finish();
								break;
							case "seller":
								settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
										Context.MODE_PRIVATE);
								editor = settings.edit();
								editor.putString("login_status", "1");
								editor.putString("user_role", role);
								editor.putString("user_id", json.getJSONObject(0).getString("userid"));
								editor.commit();
								i = new Intent(Login.this, Seller.class);
								startActivity(i);
								finish();
								break;
							case "supplier":
								settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
										Context.MODE_PRIVATE);
								editor = settings.edit();
								editor.putString("login_status", "1");
								editor.putString("user_role", role);
								editor.putString("user_id", json.getJSONObject(0).getString("userid"));
								editor.commit();
								i = new Intent(Login.this, Supplier.class);
								startActivity(i);
								finish();
								break;
							case "labour":
								settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
										Context.MODE_PRIVATE);
								editor = settings.edit();
								editor.putString("login_status", "1");
								editor.putString("user_role", role);
								editor.putString("user_id", json.getJSONObject(0).getString("userid"));
								editor.commit();
								i = new Intent(Login.this, Labour.class);
								startActivity(i);
								finish();
								break;
							default:
								Toast.makeText(Login.this, "Error : Check json", Toast.LENGTH_LONG).show();
							}
							break;
						case "0":
							Toast.makeText(Login.this, "Login Failure!", Toast.LENGTH_LONG).show();
							break;
						default:
							Toast.makeText(Login.this, "Error : Check json", Toast.LENGTH_LONG).show();
						}

					} catch (JSONException e) {
						Toast.makeText(Login.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
						Log.d(General_Data.TAG, e.getLocalizedMessage());
					}

				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Login.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, e.getLocalizedMessage());
					pd.dismiss();
				}
			});
		}
	}

	public void register(View v) {

		Intent i = new Intent(Login.this, Registration.class);
		startActivity(i);
	}
}
