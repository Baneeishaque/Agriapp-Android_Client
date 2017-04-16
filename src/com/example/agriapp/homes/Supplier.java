package com.example.agriapp.homes;

import com.example.agriapp.Accessorylist;
import com.example.agriapp.Addaccessory;
import com.example.agriapp.Addnewaccessory;
import com.example.agriapp.Labourchatbox;
import com.example.agriapp.Message;
import com.example.agriapp.R;
import com.example.agriapp.R.id;
import com.example.agriapp.R.layout;
import com.example.agriapp.R.menu;
import com.example.agriapp.Supplierchatbox;
import com.example.agriapp.profiles.Supplierprof;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Supplier extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.supplier);
	}

	public void supplier(View v) {
		Intent i = new Intent(this, Supplierprof.class);
		startActivity(i);

	}

	public void accessory(View v) {
		Intent i = new Intent(this, Accessorylist.class);
		startActivity(i);

	}

	public void addnew(View v) {

		Intent i = new Intent(this, Addnewaccessory.class);
		startActivity(i);

	}

	public void add(View v) {
		Intent i = new Intent(this, Addaccessory.class);
		startActivity(i);

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
			Intent i = new Intent(getApplicationContext(), Login.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onBackPressed() {
	}

	public void chat(View v) {
		Intent i = new Intent(getApplicationContext(), Supplierchatbox.class);
		startActivity(i);
	}
}
