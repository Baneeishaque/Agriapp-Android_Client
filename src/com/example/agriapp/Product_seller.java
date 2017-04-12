package com.example.agriapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Product_seller extends Activity {
	
	TextView name,cost,quantity;
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_seller);
		name=(TextView) findViewById(R.id.name);
		cost=(TextView) findViewById(R.id.cost);
		quantity=(TextView) findViewById(R.id.quantity);
		name.setText(getIntent().getStringExtra
                ("name"));
		cost.setText(getIntent().getStringExtra
                ("unit_cost"));
		quantity.setText(getIntent().getStringExtra
                ("minimum_quantity"));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_seller, menu);
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
