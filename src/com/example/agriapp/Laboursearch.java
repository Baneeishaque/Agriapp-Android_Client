package com.example.agriapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Laboursearch extends Activity {

	String [] role={"Kilakkal","Naduka"};
	Spinner s1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.laboursearch);
		ArrayAdapter<String> roleadapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,role);
		roleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s1=(Spinner) findViewById(R.id.spinner_soil);
		s1.setAdapter(roleadapter);
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
	public void search(View v)
	{
			Intent i=new Intent(this,Laboursearchresult.class);
			i.putExtra("s", s1.getSelectedItem().toString());
			startActivity(i);
		
	}
}
