package com.example.agriapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Laboursearch extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.laboursearch);
	}
	public void lab1(View v)
	{
			Intent i=new Intent(this,Availablelabours.class);
			startActivity(i);
		
	}
	public void lab2(View v)
	{
			Intent i=new Intent(this,Availablelabours.class);
			startActivity(i);
		
	}
}
