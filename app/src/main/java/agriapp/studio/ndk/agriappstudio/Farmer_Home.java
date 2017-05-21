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
import android.widget.Toast;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Farmer_Home extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    DefaultHttpClient httpcnt;
    HttpPost httpost;
    String response;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.button_search_crop)
    public void search(View v) {
        populate_soil_spinner_thread();


    }

    private void populate_soil_spinner_thread() {
        pd = ProgressDialog.show(this, "", "Please wait...");
        new Thread(new Runnable() {
            public void run() {
                populate_soil_spinner();

            }


        }).start();
    }

    private void populate_soil_spinner() {
        // TODO Auto-generated method stub
        try {
            httpcnt = new DefaultHttpClient();
            httpost = new HttpPost("http://" + General_Data.SERVER_IP_ADDRESS +
                    "/agriappserver/android/get_soil.php");
            ResponseHandler<String> s = new BasicResponseHandler();
            response = httpcnt.execute(httpost, s);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    //Toast.makeText(Farmer_Home.this, response, Toast.LENGTH_LONG).show();
                    Log.d(General_Data.TAG, response);
                    pd.dismiss();
                    if (response.equals("[]")) {
                        Toast.makeText(Farmer_Home.this, "No Compatible Items, Please Consult " +
                                "Officers....", Toast
                                .LENGTH_LONG).show();

                    } else {
                        Intent i = new Intent(Farmer_Home.this, Search_Crop.class);
                        i.putExtra("soil_response", response);
                        startActivity(i);

                    }


                }
            });
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(Farmer_Home.this, "Error : " + e.getLocalizedMessage(), Toast
                            .LENGTH_LONG).show();
                    Log.d(General_Data.TAG, e.getLocalizedMessage());
                    pd.dismiss();
                }
            });
        }
    }


    @OnClick(R.id.fab_notifications)
    public void notification(View v) {
        Intent i = new Intent(this, Notification_List.class);
        startActivity(i);

    }

    @OnClick(R.id.button_weather)
    public void weather(View v) {
        Intent i = new Intent(this, Weather.class);
        startActivity(i);

    }

    @OnClick(R.id.button_accessories_services)
    public void accessories(View v) {
        Intent i = new Intent(this, Accessories.class);
        startActivity(i);

    }

    @OnClick(R.id.button_articles)
    public void blog(View v) {
        Intent i = new Intent(this, Blog_List.class);
        startActivity(i);

    }

    @OnClick(R.id.fab_consultation)
    public void consult(View v) {
        Intent i = new Intent(this, Consultation_List.class);
        startActivity(i);

    }

    @OnClick(R.id.fab_logout)
    public void logout(View v) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString("login_status", "0");
        editor.commit();
        Intent i = new Intent(this, Login.class);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
    }
}
