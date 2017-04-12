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
import com.example.agriapp.modals.Notification_Modal;
import com.example.agriapp.profiles.Supplierprof;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		listView=(ListView) findViewById(R.id.listView1);
		editText=(EditText) findViewById(R.id.waterhigh);
		pd = ProgressDialog.show(this, "", "Please wait...");
        new Thread(new Runnable() {
            public void run() {
                getmsg();

            }


        }).start();
		

	}
	public void send(View v)
	{
		pd=ProgressDialog.show(this,"", "Sending Message...");
		new Thread(new Runnable() {
			public void run() {
				sendmsg();
						
			}

			
		}).start();
	}

	private void sendmsg() {
		// TODO Auto-generated method stub
		try
		{
			httpcnt=new DefaultHttpClient();
			httpost=new HttpPost("http://"+General_Data.SERVER_APPLICATION_ADDRESS+"/agriappserver/android/sendmessage.php");
			nvp=new ArrayList<NameValuePair>(3);
			SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
	                Context.MODE_PRIVATE);
			nvp.add(new BasicNameValuePair("sender",settings.getString("user_id","0")));
			nvp.add(new BasicNameValuePair("receiver",getIntent().getStringExtra
                    ("notification_id")));
			nvp.add(new BasicNameValuePair("message",editText.getText().toString()));
			
				
			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s=new BasicResponseHandler();
			response=httpcnt.execute(httpost,s);
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//Toast.makeText(Addcrop.this,response, Toast.LENGTH_LONG).show();
					pd.dismiss();
					if(response.equals("Message send successfully"))
					{
						Toast.makeText(Message.this,"Message send successfully!", Toast.LENGTH_LONG).show();
						Intent i=new Intent(Message.this,Message.class);
						i.putExtra("notification_id", getIntent().getStringExtra
				                ("notification_id"));
						startActivity(i);
						finish();
					}
					else
					{
						Toast.makeText(Message.this,"Message sending is failure!", Toast.LENGTH_LONG).show();
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
					Toast.makeText(Message.this,"error"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
	}
	private void getmsg() {
        // TODO Auto-generated method stub
        try {
            httpcnt = new DefaultHttpClient();
            httpost = new HttpPost("http://" + General_Data.SERVER_APPLICATION_ADDRESS + "/agriappserver/android/getmessage" +
                    ".php");
			nvp = new ArrayList<NameValuePair>(2);

            SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
	                Context.MODE_PRIVATE);
            nvp.add(new BasicNameValuePair("sender",settings.getString("user_id","0")));
			nvp.add(new BasicNameValuePair("receiver",getIntent().getStringExtra
                    ("notification_id")));
			httpost.setEntity(new UrlEncodedFormEntity(nvp));
            ResponseHandler<String> s = new BasicResponseHandler();
            response = httpcnt.execute(httpost, s);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    //Toast.makeText(Notification.this, response, Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
                	pd.dismiss();

                    item_list_array = new ArrayList<String>();
                    message_Modal_list =new ArrayList<>();
                    try {

                        JSONArray json = new JSONArray(response);
                        for (int i = 0; i < json.length(); i++) {


                            // Populate spinner with country names
                            item_list_array.add(json.getJSONObject(i).getString("message"));

                            Message_Modal sample_messageModal = new Message_Modal();
                            sample_messageModal.setId(json.getJSONObject(i).getString("id"));
                            sample_messageModal.setReceiver(json.getJSONObject(i).getString("receiver"));
                            sample_messageModal.setDatetime(json.getJSONObject(i).getString
                                    ("chatdate"));
                            sample_messageModal.setContent(json.getJSONObject(i).getString
                                    ("message"));
                            sample_messageModal.setProvider(json.getJSONObject(i).getString
                                    ("sender"));

                            message_Modal_list.add(sample_messageModal);


                        }
                        listView
                        .setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                        R.layout.list_item_textview,
                                        item_list_array));
                       /* list_notifications.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent i=new Intent(getApplicationContext(),Notification2.class);
                                i.putExtra("notification_id", notification_Modal_list.get(position).getId());
                                startActivity(i);
                            }
                        });*/
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
                    Toast.makeText(Message.this, "Error : " + e.getLocalizedMessage(), Toast
                            .LENGTH_LONG).show();
                    //Log.d(General_Data.TAG, e.getLocalizedMessage());
                    pd.dismiss();
                }
            });
        }

    } 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message, menu);
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
}
