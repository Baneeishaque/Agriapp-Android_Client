package com.example.agriapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Farmer_Accessory_View extends Activity {
	
	
	TextView crop,seller,accessoryname,category;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.farmer_accessory_view);
		crop=(TextView) findViewById(R.id.cropid);
		seller=(TextView) findViewById(R.id.sellerid);
		accessoryname=(TextView) findViewById(R.id.accessory);
		category=(TextView) findViewById(R.id.category);
		crop.setText(getIntent().getStringExtra
                    ("cropid"));
		seller.setText(getIntent().getStringExtra
                ("supplierid"));
		accessoryname.setText(getIntent().getStringExtra
                ("name"));
		category.setText(getIntent().getStringExtra
                ("role"));
	}

	
	
	public void supplier(View v)
    {
    	Intent i = new Intent(getApplicationContext(),Supplierdetails.class);
    	i.putExtra("notification_id",getIntent().getStringExtra("supplierid"));
    	startActivity(i);
    }
}
