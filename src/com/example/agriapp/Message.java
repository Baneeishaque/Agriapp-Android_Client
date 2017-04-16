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

import com.example.agriapp.modals.Message_Modal;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Message extends Activity {

	ListView listView;
	EditText editText;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;
	ProgressDialog pd;

	ArrayList<String> item_list_array;
	ArrayList<Message_Modal> message_Modal_list;

	// used for register alarm manager
	PendingIntent pendingIntent;
	// used to store running alarmmanager instance
	AlarmManager alarmManager;
	// Callback function for Alarmmanager event
	BroadcastReceiver mReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		RegisterAlarmBroadcast();
		
		listView = (ListView) findViewById(R.id.listView1);
		editText = (EditText) findViewById(R.id.waterhigh);
		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				getmsg();

			}

		}).start();

		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
		editText.clearFocus();

	}

	private void RegisterAlarmBroadcast() {
		// Toast.makeText(getBaseContext(), "Going to register
		// Intent.RegisterAlramBroadcast", Toast.LENGTH_LONG).show();

		// This is the call back function(BroadcastReceiver) which will be call
		// when your
		// alarm time will reached.
		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				// Toast.makeText(context, "Congrats!. Your Alarm time has been
				// reached", Toast.LENGTH_LONG).show();

				alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);

				// adapter = new DiscussArrayAdapter(getApplicationContext(),
				// R.layout.listitem_discuss);
				//
				//
				// lv.setAdapter(adapter);
				new Thread(new Runnable() {
					public void run() {
						getmsg();

					}

				}).start();
				// if (!editText1.isFocused()) {
				// lv.requestFocus();
				//
				// }
				// refresh(v);

			}
		};

		// register the alarm broadcast here
		registerReceiver(mReceiver, new IntentFilter("com.example.agriapp"));
		pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("com.example.agriapp"), 0);
		alarmManager = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
	}

	public void send(View v) {
		pd = ProgressDialog.show(this, "", "Sending Message...");
		new Thread(new Runnable() {
			public void run() {
				sendmsg();

			}

		}).start();
	}

	private void sendmsg() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_APPLICATION_ADDRESS + "/agriappserver/android/sendmessage.php");
			nvp = new ArrayList<NameValuePair>(3);
			SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
					Context.MODE_PRIVATE);
			nvp.add(new BasicNameValuePair("sender", settings.getString("user_id", "0")));
			nvp.add(new BasicNameValuePair("receiver", getIntent().getStringExtra("notification_id")));
			nvp.add(new BasicNameValuePair("message", editText.getText().toString()));

			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Toast.makeText(Addcrop.this,response,
					// Toast.LENGTH_LONG).show();
					pd.dismiss();
					if (response.equals("Message send successfully")) {
						Toast.makeText(Message.this, "Message send successfully!", Toast.LENGTH_LONG).show();
						Intent i = new Intent(Message.this, Message.class);
						i.putExtra("notification_id", getIntent().getStringExtra("notification_id"));
						startActivity(i);
						finish();
					} else {
						Toast.makeText(Message.this, "Message sending is failure!", Toast.LENGTH_LONG).show();
					}
				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Message.this, "error" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
	}

	private void getmsg() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_APPLICATION_ADDRESS + "/agriappserver/android/getmessage" + ".php");
			nvp = new ArrayList<NameValuePair>(2);

			SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
					Context.MODE_PRIVATE);
			nvp.add(new BasicNameValuePair("sender", settings.getString("user_id", "0")));
			nvp.add(new BasicNameValuePair("receiver", getIntent().getStringExtra("notification_id")));
			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Toast.makeText(Notification.this, response,
					// Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
					pd.dismiss();

					item_list_array = new ArrayList<String>();
					message_Modal_list = new ArrayList<>();
					try {

						JSONArray json = new JSONArray(response);
						for (int i = 0; i < json.length(); i++) {

							// Populate spinner with country names
							item_list_array.add(json.getJSONObject(i).getString("message"));

							Message_Modal sample_messageModal = new Message_Modal();
							sample_messageModal.setId(json.getJSONObject(i).getString("id"));
							sample_messageModal.setReceiver(json.getJSONObject(i).getString("receiver"));
							sample_messageModal.setDatetime(json.getJSONObject(i).getString("chatdate"));
							sample_messageModal.setContent(json.getJSONObject(i).getString("message"));
							sample_messageModal.setProvider(json.getJSONObject(i).getString("sender"));

							message_Modal_list.add(sample_messageModal);

						}
						listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
								R.layout.list_item_textview, item_list_array));
						/*
						 * list_notifications.setOnItemClickListener(new
						 * OnItemClickListener() {
						 * 
						 * @Override public void onItemClick(AdapterView<?>
						 * parent, View view, int position, long id) {
						 * 
						 * Intent i=new
						 * Intent(getApplicationContext(),Notification2.class);
						 * i.putExtra("notification_id",
						 * notification_Modal_list.get(position).getId());
						 * startActivity(i); } });
						 */
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
					Toast.makeText(Message.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					// Log.d(General_Data.TAG, e.getLocalizedMessage());
					pd.dismiss();
				}
			});
		}

	}

	
}
