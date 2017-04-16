package com.example.agriapp.homes;

import com.example.agriapp.Addaccessory;
import com.example.agriapp.Productlist;
import com.example.agriapp.R;
import com.example.agriapp.Sellerchatbox;
import com.example.agriapp.Sellproduct;
import com.example.agriapp.profiles.Sellerprofile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Seller extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seller);
	}

	public void prod(View v) {

		Intent i = new Intent(this, Sellproduct.class);
		startActivity(i);

	}

	public void prodlist(View v) {

		Intent i = new Intent(this, Productlist.class);
		startActivity(i);

	}

	public void prof(View v) {
		Intent i = new Intent(this, Sellerprofile.class);
		startActivity(i);

	}
	
	public void add(View v) {
		Intent i = new Intent(this, Addaccessory.class);
		startActivity(i);

	}

	public void onBackPressed() {
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void chat(View v) {
		Intent i = new Intent(getApplicationContext(), Sellerchatbox.class);
		startActivity(i);
	}
}
