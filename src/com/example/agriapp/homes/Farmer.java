package com.example.agriapp.homes;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.agriapp.Blog;
import com.example.agriapp.Consultengg;
import com.example.agriapp.Find_Accessories;
import com.example.agriapp.General_Data;
import com.example.agriapp.Laboursearch;
import com.example.agriapp.Listofsellers;
import com.example.agriapp.Notification;
import com.example.agriapp.R;
import com.example.agriapp.Searchcrop;
import com.example.agriapp.Weather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Farmer extends Activity {


    ProgressDialog pd;
    DefaultHttpClient httpcnt;
    HttpPost httpost;

    String response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.farmer);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i=new Intent(Farmer.this,Login.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void search(View v)
	{
			/*Intent i=new Intent(this,Searchcrop.class);
			startActivity(i);*/
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
            httpost = new HttpPost("http://" + General_Data.SERVER_APPLICATION_ADDRESS +
                    "/agriappserver/android/get_soil.php");
            ResponseHandler<String> s = new BasicResponseHandler();
            response = httpcnt.execute(httpost, s);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    //Toast.makeText(Farmer_Home.this, response, Toast.LENGTH_LONG).show();
                    Log.d(General_Data.TAG, response);
                    pd.dismiss();
                    if (response.equals("[]")) {
                        Toast.makeText(Farmer.this, "No Compatible Items, Please Consult " +
                                "Officers....", Toast
                                .LENGTH_LONG).show();

                    } else {
                        Intent i=new Intent(Farmer.this,Searchcrop.class);
                        i.putExtra("soil_response",response);
                        startActivity(i);

                    }


                }
            });
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(Farmer.this, "Error : " + e.getLocalizedMessage(), Toast
                            .LENGTH_LONG).show();
                    Log.d(General_Data.TAG, e.getLocalizedMessage());
                    pd.dismiss();
                }
            });
        }
    }


	public void notification(View v)
	{
			Intent i=new Intent(this,Notification.class);
			startActivity(i);
		
	}
	
	public void weather(View v)
	{
			Intent i=new Intent(this,Weather.class);
			startActivity(i);
		
	}
	public void accessories(View v)
	{
			Intent i=new Intent(this,Find_Accessories.class);
			startActivity(i);
		
	}
	public void blog(View v)
	{
			Intent i=new Intent(this,Blog.class);
			startActivity(i);
		
	}
	
	public void consult(View v)
	{
			Intent i=new Intent(this,Consultengg.class);
			startActivity(i);
		
	}
	public void labour(View v)
	{
			Intent i=new Intent(this,Laboursearch.class);
			startActivity(i);
		
	}
	public void connect(View v)
	{
			Intent i=new Intent(this,Listofsellers.class);
			startActivity(i);
		
	}
	public void onBackPressed()
	{
	}
}
