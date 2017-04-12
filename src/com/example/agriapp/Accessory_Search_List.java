package com.example.agriapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.agriapp.modals.Fertilizer_Modal;
import com.example.agriapp.modals.Machine_Modal;

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
import android.widget.TextView;
import android.widget.Toast;

public class Accessory_Search_List extends Activity {
	
	ListView list_notifications;
	

	ArrayList<String> item_list_array;
	ArrayList<Fertilizer_Modal> Fertilizer_Modal_list;
	ArrayList<Machine_Modal> Machine_Modal_list;
	TextView key;
	String role;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accessory_search_result);
		role=getIntent().getStringExtra("role");
//		Toast.makeText(getApplicationContext(),getIntent().getStringExtra
//		         ("role"), Toast.LENGTH_LONG).show();

		list_notifications=(ListView) findViewById(R.id.listView1);
		SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
	                Context.MODE_PRIVATE);
			
	        setTitle("Results : Service Type - "+settings.getString("service_type","Selected Soil Type"));
	        SharedPreferences.Editor editor=settings.edit();
	        editor.remove("service_type");
	        editor.commit();
	        key = (TextView) findViewById(R.id.quantity);
	        key.setText(getIntent().getStringExtra
                    ("key"));
	        item_list_array = new ArrayList<String>();
            Fertilizer_Modal_list =new ArrayList<>();
            Machine_Modal_list =new ArrayList<>();
            try {

                JSONArray json = new JSONArray(getIntent().getStringExtra
	                    ("service_search_response"));
                for (int i = 0; i < json.length(); i++) {


                    // Populate spinner with country names
                    item_list_array.add(json.getJSONObject(i).getString("name"));
                    if(role.equals("Machines"))
                    {
                    	Machine_Modal sample_Machine_Modal = new Machine_Modal();
                        sample_Machine_Modal.setId(json.getJSONObject(i).getString("id"));
                        sample_Machine_Modal.setName(json.getJSONObject(i).getString("name"));
                        sample_Machine_Modal.setCategory(json.getJSONObject(i).getString
                                ("category"));
                        sample_Machine_Modal.setRent(json.getJSONObject(i).getString
                                ("rent"));
                        sample_Machine_Modal.setRentscheme(json.getJSONObject(i).getString
                                ("rentscheme"));
                        sample_Machine_Modal.setRentamount(json.getJSONObject(i).getString
                                ("rentamount"));
                        sample_Machine_Modal.setPurchase(json.getJSONObject(i).getString
                                ("purchase"));
                        sample_Machine_Modal.setPurchaseamount(json.getJSONObject(i).getString
                                ("purchaseamount"));
                        sample_Machine_Modal.setsupplier_id(json.getJSONObject(i).getString
                                ("supplier_id"));
                        Machine_Modal_list.add(sample_Machine_Modal);

                    }
                    else{
                    	Fertilizer_Modal sample_Fertilizer_Modal = new Fertilizer_Modal();
                    	sample_Fertilizer_Modal.setId(json.getJSONObject(i).getString("id"));
                    	sample_Fertilizer_Modal.setName(json.getJSONObject(i).getString("name"));
                    	sample_Fertilizer_Modal.setCropid(json.getJSONObject(i).getString
                            ("cropid"));
                    	sample_Fertilizer_Modal.setSupplierid(json.getJSONObject(i).getString
                            ("supplierid"));
                  
                    	Fertilizer_Modal_list.add(sample_Fertilizer_Modal);
                    }

                }
                list_notifications
                        .setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.list_item_textview,
                                item_list_array));
                list_notifications.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    	Toast.makeText(getApplicationContext(), "Search", Toast
//                                .LENGTH_LONG).show();
                    	if(role.equals("Machines")) 
                    	{
                    		Intent i=new Intent(getApplicationContext(),Farmermachineview.class);
                    		i.putExtra("role", getIntent().getStringExtra
                    				("role"));
                    		i.putExtra("id", Machine_Modal_list.get(position).getId());
                    		i.putExtra("name", Machine_Modal_list.get(position).getName());
                    		i.putExtra("category", Machine_Modal_list.get(position).getCategory());
                    		i.putExtra("rent", Machine_Modal_list.get(position).getRent());
                    		i.putExtra("rentscheme", Machine_Modal_list.get(position).getRentscheme());
                    		i.putExtra("rentamount", Machine_Modal_list.get(position).getRentamount());
                    		i.putExtra("purchase", Machine_Modal_list.get(position).getPurchase());
                    		i.putExtra("purchaseamount", Machine_Modal_list.get(position).getPurchaseamount());
                    		i.putExtra("supplier_id", Machine_Modal_list.get(position).getsupplier_id());
                    		startActivity(i);
                    	}
                    	else{
                    		Intent i=new Intent(getApplicationContext(),Farmer_Accessory_View.class);
                    		i.putExtra("role", getIntent().getStringExtra
                    				("role"));
                    		i.putExtra("id", Fertilizer_Modal_list.get(position).getId());
                    		i.putExtra("name", Fertilizer_Modal_list.get(position).getName());
                    		i.putExtra("cropid", Fertilizer_Modal_list.get(position).getCropid());
                    		i.putExtra("supplierid", Fertilizer_Modal_list.get(position).getSupplierid());
                    		startActivity(i);
                    	}
                    }
                });
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast
                        .LENGTH_LONG).show();
                Log.d(General_Data.TAG, e.getLocalizedMessage());
            }


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accessorysearch, menu);
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
