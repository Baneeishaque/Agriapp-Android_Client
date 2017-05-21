package com.example.agriapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Agriengg extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agriengg);
	}

	public void crop(View v)
	{
			Intent i=new Intent(this,Addcrop.class);
			startActivity(i);
		
	}
	public void noti(View v)
	{
			Intent i=new Intent(this,Addnotification.class);
			startActivity(i);
		
	}
}
