package com.example.agriapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import com.example.agriapp.modals.Fertilizer_Modal;
import com.example.agriapp.modals.Machine_Modal;
import com.example.agriapp.modals.Notification_Modal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Accessorylist extends Activity {

	ProgressDialog pd;

	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;
	ListView list_machines;

	ArrayList<String> item_list_array;
	ArrayList<Machine_Modal> machine_Modal_list;
	ArrayList<Fertilizer_Modal> Fertilizer_Modal_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accessorylist);

		list_machines = (ListView) findViewById(R.id.listView1);

		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				get_machines();

			}

		}).start();
	}

	private void get_machines() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_APPLICATION_ADDRESS + "/agriappserver/android/get_accessoriesall" + ".php");

			nvp = new ArrayList<NameValuePair>(1);
			SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
					Context.MODE_PRIVATE);
			nvp.add(new BasicNameValuePair("id", settings.getString("user_id", "0")));
			httpost.setEntity(new UrlEncodedFormEntity(nvp));

			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Toast.makeText(Notification.this, response,
					// Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
					pd.dismiss();

					item_list_array = new ArrayList<String>();
					machine_Modal_list = new ArrayList<>();
					Fertilizer_Modal_list = new ArrayList<>();
					try {

						JSONArray json = new JSONArray(response);
						for (int i = 0; i < json.length(); i++) {

							// Populate spinner with country names
							item_list_array.add(json.getJSONObject(i).getString("name"));
							if (json.getJSONObject(i).has("cropid")) {
								Fertilizer_Modal sample_Fertilizer_Modal = new Fertilizer_Modal();
								sample_Fertilizer_Modal.setId(json.getJSONObject(i).getString("id"));
								sample_Fertilizer_Modal.setName(json.getJSONObject(i).getString("name"));
								sample_Fertilizer_Modal.setCropid(json.getJSONObject(i).getString("cropid"));
								sample_Fertilizer_Modal.setSupplierid(json.getJSONObject(i).getString("supplierid"));

								Fertilizer_Modal_list.add(sample_Fertilizer_Modal);
//								Toast.makeText(getApplicationContext(),"Fertilizer" +json.getJSONObject(i).getString("name") , Toast.LENGTH_SHORT).show();
							} else {
								Machine_Modal sample_machineModal = new Machine_Modal();
								sample_machineModal.setId(json.getJSONObject(i).getString("id"));
								sample_machineModal.setName(json.getJSONObject(i).getString("name"));
								sample_machineModal.setCategory(json.getJSONObject(i).getString("category"));
								sample_machineModal.setRent(json.getJSONObject(i).getString("rent"));
								sample_machineModal.setRentscheme(json.getJSONObject(i).getString("rentscheme"));
								sample_machineModal.setRentamount(json.getJSONObject(i).getString("rentamount"));
								sample_machineModal.setPurchase(json.getJSONObject(i).getString("purchase"));
								sample_machineModal.setPurchaseamount(json.getJSONObject(i).getString("purchaseamount"));
								sample_machineModal.setsupplier_id(json.getJSONObject(i).getString("supplier_id"));
								machine_Modal_list.add(sample_machineModal);
//								Toast.makeText(getApplicationContext(),"Machine" +json.getJSONObject(i).getString("name") , Toast.LENGTH_SHORT).show();
							}

						}
						list_machines.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
								R.layout.list_item_textview, item_list_array));
						list_machines.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								int flag = 0, item_position = 0, i2 = 0;
								for (Fertilizer_Modal item : Fertilizer_Modal_list) {
									if (item.getName().equals(list_machines.getItemAtPosition(position).toString())) {
										item_position = i2;
										flag=1;
										break;
										
									}
									i2++;
								}
								if (flag == 1) {
									Intent i = new Intent(getApplicationContext(), Farmer_Accessory_View.class);

									i.putExtra("id", Fertilizer_Modal_list.get(item_position).getId());
									i.putExtra("name", Fertilizer_Modal_list.get(item_position).getName());
									i.putExtra("cropid", Fertilizer_Modal_list.get(item_position).getCropid());
									i.putExtra("supplierid", Fertilizer_Modal_list.get(item_position).getSupplierid());
									startActivity(i);
								} else {
									int item_position2=0, i3 = 0;
									for (Machine_Modal item : machine_Modal_list) {
										if (item.getName()
												.equals(list_machines.getItemAtPosition(position).toString())) {
											item_position2 = i3;
											
										}
										i3++;
										
									}
									Intent i = new Intent(getApplicationContext(), Farmermachineview.class);

									i.putExtra("id", machine_Modal_list.get(item_position2).getId());
									i.putExtra("name", machine_Modal_list.get(item_position2).getName());
									i.putExtra("category", machine_Modal_list.get(item_position2).getCategory());
									i.putExtra("rent", machine_Modal_list.get(item_position2).getRent());
									i.putExtra("rentscheme", machine_Modal_list.get(item_position2).getRentscheme());
									i.putExtra("rentamount", machine_Modal_list.get(item_position2).getRentamount());
									i.putExtra("purchase", machine_Modal_list.get(item_position2).getPurchase());
									i.putExtra("purchaseamount", machine_Modal_list.get(item_position2).getPurchaseamount());
									i.putExtra("supplier_id", machine_Modal_list.get(item_position2).getsupplier_id());
									startActivity(i);
								}
								/*for (Machine_Modal item : machine_Modal_list) {
									Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_SHORT).show();
								}
								for (Fertilizer_Modal item : Fertilizer_Modal_list) {
									Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_SHORT).show();
								}*/

							}
						});
					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
								.show();
						Log.d(General_Data.TAG, e.getLocalizedMessage());
					}

				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Accessorylist.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, e.getLocalizedMessage());
					pd.dismiss();
				}
			});
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accessorylist, menu);
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
