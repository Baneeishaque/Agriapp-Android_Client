package agriapp.studio.ndk.agriappstudio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Search_Crop extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_water)
    EditText water_availability;
    @BindView(R.id.txt_temp)
    EditText average_temperature;

    @BindView(R.id.txt_ph)
    EditText ph;

    @BindView(R.id.spinner_soil)
    Spinner spinner_soil;
    DefaultHttpClient httpcnt;
    HttpPost httpost;
    ArrayList<NameValuePair> nvp;
    String response;
    ArrayList<String> spinner_list;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_crop);

        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinner_list = new ArrayList<String>();
        try {

            JSONArray json = new JSONArray(getIntent().getStringExtra
                    ("soil_response"));
            for (int i = 0; i < json.length(); i++) {

                // Populate spinner with country names
                spinner_list.add(json.getJSONObject(i).getString("soil_type"));

            }
            spinner_soil
                    .setAdapter(new ArrayAdapter<String>(Search_Crop.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            spinner_list));
        } catch (JSONException e) {
            Toast.makeText(Search_Crop.this, "Error : " + e.getLocalizedMessage(), Toast
                    .LENGTH_LONG).show();
            Log.d(General_Data.TAG, e.getLocalizedMessage());
        }


    }


    @OnClick(R.id.button_search_crop)
    public void search(View v) {
        pd = ProgressDialog.show(this, "", "Please wait...");
        new Thread(new Runnable() {
            public void run() {
                search_crop();

            }


        }).start();

    }

    private void search_crop() {
        // TODO Auto-generated method stub
        try {
            httpcnt = new DefaultHttpClient();
            httpost = new HttpPost("http://" + General_Data.SERVER_IP_ADDRESS +
                    "/agriappserver/android/get_crop_search.php");
            nvp = new ArrayList<NameValuePair>(4);
            nvp.add(new BasicNameValuePair("water_availability", water_availability.getText().toString()));

            nvp.add(new BasicNameValuePair("Avg_temperature", average_temperature.getText().toString()));
            nvp.add(new BasicNameValuePair("pH", ph.getText().toString()));

            nvp.add(new BasicNameValuePair("soil_type", spinner_soil.getSelectedItem().toString()));


            httpost.setEntity(new UrlEncodedFormEntity(nvp));
            ResponseHandler<String> s = new BasicResponseHandler();
            response = httpcnt.execute(httpost, s);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    //Toast.makeText(Search_Crop.this, response, Toast.LENGTH_LONG).show();
                    Log.d(General_Data.TAG, response);
                    pd.dismiss();

                    if (response.equals("[]")) {
                        Toast.makeText(Search_Crop.this, "No Compatible Items, Please Consult " +
                                "Officers....", Toast
                                .LENGTH_LONG).show();

                    } else {

                        SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("soil_type", spinner_soil.getSelectedItem().toString());
                        editor.commit();
                        Intent i = new Intent(getApplicationContext(), Search_Crop_Result.class);
                        i.putExtra("crop_search_response", response);
                        startActivity(i);
                    }
                    /*try {
                        JSONArray json = new JSONArray(response);
                        String count = json.getJSONObject(0).getString("count");
                        switch (count) {
                            case "1":
                                String accessories = json.getJSONObject(0).getString("accessories");
                                Intent i;
                                SharedPreferences settings;
                                SharedPreferences.Editor editor;
                                switch (accessories) {

                                    case "farmer":
                                        settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
                                                Context.MODE_PRIVATE);
                                        editor = settings.edit();
                                        editor.putString("login_status","1");
                                        editor.putString("user_role",accessories);
                                        editor.putString("user_id",json.getJSONObject(0).getString("userid"));
                                        editor.commit();
                                        i = new Intent(Login.this, Farmer_Home.class);
                                        startActivity(i);
                                        finish();
                                        break;
                                    case "officer":
                                        settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
                                                Context.MODE_PRIVATE);
                                        editor = settings.edit();
                                        editor.putString("login_status","1");
                                        editor.putString("user_role",accessories);
                                        editor.putString("user_id",json.getJSONObject(0).getString("userid"));
                                        editor.commit();
                                        i = new Intent(Login.this, Officer.class);
                                        startActivity(i);
                                        finish();
                                        break;
                                    case "seller":
                                        settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
                                                Context.MODE_PRIVATE);
                                        editor = settings.edit();
                                        editor.putString("login_status","1");
                                        editor.putString("user_role",accessories);
                                        editor.putString("user_id",json.getJSONObject(0).getString("userid"));
                                        editor.commit();
                                        i = new Intent(Login.this, Seller.class);
                                        startActivity(i);
                                        finish();
                                        break;
                                    case "supplier":
                                        settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
                                                Context.MODE_PRIVATE);
                                        editor = settings.edit();
                                        editor.putString("login_status","1");
                                        editor.putString("user_role",accessories);
                                        editor.putString("user_id",json.getJSONObject(0).getString("userid"));
                                        editor.commit();
                                        i = new Intent(Login.this, Accessory_Supplier.class);
                                        startActivity(i);
                                        finish();
                                        break;
                                    case "labour":
                                        settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
                                                Context.MODE_PRIVATE);
                                        editor = settings.edit();
                                        editor.putString("login_status","1");
                                        editor.putString("user_role",accessories);
                                        editor.putString("user_id",json.getJSONObject(0).getString("userid"));
                                        editor.commit();
                                        i = new Intent(Login.this, Labour.class);
                                        startActivity(i);
                                        finish();
                                        break;
                                    default:
                                        Toast.makeText(Login.this, "Error : Check json", Toast
                                                .LENGTH_LONG).show();
                                }
                                break;
                            case "0":
                                Toast.makeText(Login.this, "Login Failure!", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(Login.this, "Error : Check json", Toast
                                        .LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(Login.this, "Error : " + e.getLocalizedMessage(), Toast
                                .LENGTH_LONG).show();
                        Log.d(General_Data.TAG, e.getLocalizedMessage());
                    }

                    /*if (response.equals("Login Successfully!")) {
                        Toast.makeText(Login.this, "Login Successfully!", Toast.LENGTH_LONG).show();
                        if (spinner_accessory.getSelectedItem().toString().equals("Farmer")) {
                            Intent i = new Intent(Login.this, Farmer_Home.class);
                            startActivity(i);
                        }
                        if (spinner_accessory.getSelectedItem().toString().equals("Agriculture Officer")) {
                            Intent i = new Intent(Login.this, Officer.class);
                            startActivity(i);
                        }
                        if (spinner_accessory.getSelectedItem().toString().equals("Labour")) {
                            Intent i = new Intent(Login.this, Labour.class);
                            startActivity(i);
                        }
                        if (spinner_accessory.getSelectedItem().toString().equals("Seller")) {
                            Intent i = new Intent(Login.this, Seller.class);
                            startActivity(i);
                        }
                        if (spinner_accessory.getSelectedItem().toString().equals("Supplier")) {
                            Intent i = new Intent(Login.this, Accessory_Supplier.class);
                            startActivity(i);
                        }
                    } else {
                        Toast.makeText(Login.this, "Login Failure!", Toast.LENGTH_LONG).show();

                    }*/
                    //Toast.makeText(Login.this,user.getText().toString(),Toast.LENGTH_LONG).show();
                    //Toast.makeText(Login.this,pass.getText().toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(Login.this,spinner_accessory.getSelectedItem().toString(), Toast.LENGTH_LONG).show();


                }
            });
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(Search_Crop.this, "Error : " + e.getLocalizedMessage(), Toast
                            .LENGTH_LONG).show();
                    Log.d(General_Data.TAG, e.getLocalizedMessage());
                    pd.dismiss();
                }
            });
        }
    }


}
