package com.example.agriapp.homes;

import com.example.agriapp.Addcrop;
import com.example.agriapp.Addnotification;
import com.example.agriapp.Blog;
import com.example.agriapp.Enggchatbox;
import com.example.agriapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Officer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.officer);
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
			Intent i=new Intent(getApplicationContext(),Login.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addnotif(View v)
	{
		
			Intent i=new Intent(this,Addnotification.class);
			startActivity(i);
		
	}
	public void addcrop(View v)
	{
		
			Intent i=new Intent(this,Addcrop.class);
			startActivity(i);
		
	}
	public void articles(View v)
	{
			Intent i=new Intent(this,Blog.class);
			startActivity(i);
		
	}
	public void msg(View v)
	{
		
			Intent i=new Intent(this,Enggchatbox.class);
			startActivity(i);
		
	}
	public void onBackPressed()
	{
	}
}
