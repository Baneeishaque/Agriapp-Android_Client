package com.example.agriapp.registration;

import com.example.agriapp.R;
import com.example.agriapp.R.id;
import com.example.agriapp.R.layout;
import com.example.agriapp.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Registration extends Activity {
	
	
	EditText txt_username,txt_password;
//	public static String username,password;

	String [] role={"Agriculture Officer","Farmer","Labour","Seller","Supplier"};
	Spinner s1;
	//ArrayList<String> spinner_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		ArrayAdapter<String> roleadapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,role);
		roleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s1=(Spinner) findViewById(R.id.spinner_crop);
		s1.setAdapter(roleadapter);
		txt_username=(EditText) findViewById(R.id.user);
		txt_password=(EditText) findViewById(R.id.passwd);
		//content=(EditText) findViewById(R.id.content);
		//spinner_list = new ArrayList<String>();
		/*try {

			JSONArray json = new JSONArray(getIntent().getStringExtra("soil_response"));
			for (int i = 0; i < json.length(); i++) {

				// Populate spinner with country names
				spinner_list.add(json.getJSONObject(i).getString("soil_type"));

			}
			s1.setAdapter(new ArrayAdapter<String>(Registration.this,
					android.R.layout.simple_spinner_dropdown_item, spinner_list));
		} catch (JSONException e) {
			Toast.makeText(Registration.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
			Log.d(General_Data.TAG, e.getLocalizedMessage());
		}*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	

	
	public void submit(View view) {
		
//		Toast.makeText(getApplicationContext(),txt_username.getText(), Toast.LENGTH_LONG).show();
//		Toast.makeText(getApplicationContext(),txt_password.getText(), Toast.LENGTH_LONG).show();
//
//		username=txt_username.getText().toString();
//		password=txt_password.getText().toString();
		if(s1.getSelectedItem().toString().equals("Farmer")||(s1.getSelectedItem().toString().equals("Agriculture Officer"))){
			Intent target = new Intent(this, Registration2.class);
			 target.putExtra("user", txt_username.getText().toString());
             target.putExtra("pass", txt_password.getText().toString());
             target.putExtra("role",s1.getSelectedItem().toString());
			startActivity(target);
		}
		else if (s1.getSelectedItem().toString().equals("Seller")||s1.getSelectedItem().toString().equals("Supplier")) {
			Intent target = new Intent(this, Registration3.class);
			 target.putExtra("user", txt_username.getText().toString());
             target.putExtra("pass", txt_password.getText().toString());
             target.putExtra("role",s1.getSelectedItem().toString());
			startActivity(target);
		}	
		else if (s1.getSelectedItem().toString().equals("Labour")) {
			Intent target = new Intent(this, Registration4.class);
			 target.putExtra("user", txt_username.getText().toString());
             target.putExtra("pass", txt_password.getText().toString());
             target.putExtra("role",s1.getSelectedItem().toString());
			startActivity(target);
		}	 
	
	
	}	
	
	public void reset(View v)
	{
		txt_username.setText(null);
		txt_password.setText(null);
	}
}
