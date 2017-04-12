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

import com.example.agriapp.modals.Notification_Modal;
import com.example.agriapp.modals.Product_Modal;

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

public class Productlist extends Activity {
	
	DefaultHttpClient httpcnt;
    HttpPost httpost;
    ArrayList<NameValuePair> nvp;
    String response;
    ProgressDialog pd;
    ArrayList<String> item_list_array;

    ListView product;

    ArrayList<Product_Modal> product_Modal_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productlist);
		
		product=(ListView) findViewById(R.id.listView1);
		 pd = ProgressDialog.show(this, "", "Please wait...");
	        new Thread(new Runnable() {
	            public void run() {
	                get_product();

	            }


	        }).start();
	}
	private void get_product() {
        // TODO Auto-generated method stub
        try {
            httpcnt = new DefaultHttpClient();
            httpost = new HttpPost("http://" + General_Data.SERVER_APPLICATION_ADDRESS +
                    "/agriappserver/android/getproduct_seller.php");

            nvp = new ArrayList<NameValuePair>(1);
            SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
	                Context.MODE_PRIVATE);
            nvp.add(new BasicNameValuePair("id", settings.getString("user_id","0")));


            httpost.setEntity(new UrlEncodedFormEntity(nvp));

            ResponseHandler<String> s = new BasicResponseHandler();
            response = httpcnt.execute(httpost, s);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                   // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                	Log.d(General_Data.TAG, response);
                	pd.dismiss();

                    item_list_array = new ArrayList<String>();
                    product_Modal_list = new ArrayList<>();
                    try {

                        JSONArray json = new JSONArray(response);
                        for (int i = 0; i < json.length(); i++) {


                            // Populate spinner with country names
                            item_list_array.add(json.getJSONObject(i).getString("name"));

                            Product_Modal sample_productModal = new Product_Modal();
                            sample_productModal.setId(json.getJSONObject(i).getString("id"));
                            sample_productModal.setName(json.getJSONObject(i).getString("name"));
                            sample_productModal.setMinimum_cost(json.getJSONObject(i).getString
                                    ("unit_cost"));
                            sample_productModal. setMinimum_quantity(json.getJSONObject(i).getString
                                    ("minimum_quantity"));
                            sample_productModal.setSeller_id(json.getJSONObject(i).getString
                                    ("seller_id"));
                            
                          product_Modal_list.add(sample_productModal);


                        }
                       product
                                .setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                        R.layout.list_item_textview,
                                        item_list_array));
                       product.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent i = new Intent(getApplicationContext(),Product_seller.class);
                                i.putExtra("id", product_Modal_list.get(position).getId());
                                i.putExtra("name", product_Modal_list.get(position).getName());
                                i.putExtra("unit_cost", product_Modal_list.get(position).getMinimum_cost());
                                i.putExtra("minimum_quantity", product_Modal_list.get(position).getMinimum_quantity());
                                i.putExtra("seller_id", product_Modal_list.get(position).getSeller_id());
                                startActivity(i);
                            }
                        });
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast
                                .LENGTH_LONG).show();
                        Log.d(General_Data.TAG, e.getLocalizedMessage());
                    }

                }
            });
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast
                            .LENGTH_LONG).show();
                    Log.d(General_Data.TAG, e.getLocalizedMessage());
                    pd.dismiss();
                }
            });
        }
	}
}
