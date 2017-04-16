package com.example.agriapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Farmermachineview extends Activity {
	TextView accessoryname,category,rentamount,rentscheme,purchaseamount,type,supplier_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.farmermachineview);
		accessoryname=(TextView) findViewById(R.id.accessory);
		category=(TextView) findViewById(R.id.category);
		rentamount=(TextView) findViewById(R.id.rent);
		type=(TextView) findViewById(R.id.txt_contact);
		rentscheme=(TextView) findViewById(R.id.scheme);
		purchaseamount=(TextView) findViewById(R.id.purchase);
		supplier_id=(TextView) findViewById(R.id.textView4);
		
		Log.d(General_Data.TAG, getIntent().getStringExtra
                ("name"));
		Log.d(General_Data.TAG, getIntent().getStringExtra
                ("category"));
		Log.d(General_Data.TAG, getIntent().getStringExtra
                ("rentamount"));
		Log.d(General_Data.TAG, getIntent().getStringExtra
                ("purchaseamount"));
		Log.d(General_Data.TAG, getIntent().getStringExtra
                ("supplier_id"));
		Log.d(General_Data.TAG, getIntent().getStringExtra
                ("rent"));
		Log.d(General_Data.TAG, getIntent().getStringExtra
                ("purchase"));
		accessoryname.setText(getIntent().getStringExtra
                ("name"));
		category.setText(getIntent().getStringExtra
                    ("category"));
		
		rentamount.setText(getIntent().getStringExtra
                ("rentamount"));
		rentscheme.setText(getIntent().getStringExtra
                ("rentscheme"));
		purchaseamount.setText(getIntent().getStringExtra
                ("purchaseamount"));
		supplier_id.setText(getIntent().getStringExtra
                ("supplier_id"));
		if(getIntent().getStringExtra("rent").equals("true"))
		{
			if(getIntent().getStringExtra("purchase").equals("true"))
			{
				type.setText("rent and purchase");
			}
			else
			{
				type.setText("purchase");
			}
				
		}
		else if(getIntent().getStringExtra("purchase").equals("true"))
		{

			type.setText("purchase");
		}
	}

	
	public void supplier(View v)
    {
    	Intent i = new Intent(getApplicationContext(),Supplierdetails.class);
    	i.putExtra("notification_id",getIntent().getStringExtra("supplier_id"));
      	startActivity(i);
    }
}
