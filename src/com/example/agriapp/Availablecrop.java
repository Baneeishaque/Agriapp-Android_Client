package com.example.agriapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.agriapp.modals.Crop_Modal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class Availablecrop extends Activity implements OnItemClickListener {
	String [] role={"Tomato","Beans","potato","cabbage","carrot","cucumber"};
	ListView l1;
	private ArrayList<String> item_list_array;

    ArrayList<Crop_Modal> crop_Modal_list;
    ListView list_search_crop_result;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.availablecrop);
		list_search_crop_result=(ListView) findViewById(R.id.list_search_crop_result);
		SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
	                Context.MODE_PRIVATE);

	        setTitle("Results : Soil Type - "+settings.getString("soil_type","Selected Soil Type"));
	        SharedPreferences.Editor editor=settings.edit();
	        editor.remove("soil_type");
	        editor.commit();
	        item_list_array = new ArrayList<String>();
	        crop_Modal_list =new ArrayList<>();
	        try {

	            JSONArray json = new JSONArray(getIntent().getStringExtra
	                    ("crop_search_response"));
	            for (int i = 0; i < json.length(); i++) {


	                // Populate spinner with country names
	                item_list_array.add(json.getJSONObject(i).getString("name"));

	                Crop_Modal sample_cropModal = new Crop_Modal();
	                sample_cropModal.setId(json.getJSONObject(i).getString("id"));
	                sample_cropModal.setName(json.getJSONObject(i).getString("name"));
	                sample_cropModal.setAvg_temperature_high(json.getJSONObject(i).getString("Avg_temperature_high"));
	                sample_cropModal.setAvg_temperature_low(json.getJSONObject(i).getString
	                        ("Avg_temperature_low"));
	                sample_cropModal.setpH_high(json.getJSONObject(i).getString("pH_high"));
	                sample_cropModal.setpH_low(json.getJSONObject(i).getString("pH_low"));
	                sample_cropModal.setWater_availability_high(json.getJSONObject(i).getString
	                        ("water_availability_high"));
	                sample_cropModal.setWater_availability_low(json.getJSONObject(i).getString
	                        ("water_availability_low"));
	                crop_Modal_list.add(sample_cropModal);


	            }
	            list_search_crop_result
	                    .setAdapter(new ArrayAdapter<String>(getApplicationContext(),
	                            R.layout.list_item_textview,
	                            item_list_array));
	            list_search_crop_result.setOnItemClickListener(this);
	        } catch (JSONException e) {
	            Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast
	                    .LENGTH_LONG).show();
	            Log.d(General_Data.TAG, e.getLocalizedMessage());
	        }

	    

	}
	
	 @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        // TODO Auto-generated method stub

			Intent i=new Intent(this,Crops.class);
	        i.putExtra("crop_id", crop_Modal_list.get(position).getId());
			startActivity(i);
	    }
	
	
	
}
