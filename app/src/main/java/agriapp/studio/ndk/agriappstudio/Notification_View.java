package agriapp.studio.ndk.agriappstudio;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
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

public class Notification_View extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    DefaultHttpClient httpcnt;
    HttpPost httpost;
    ArrayList<NameValuePair> nvp;
    String response;
    ArrayList<String> spinner_list;
    ProgressDialog pd;

    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.txt_title)
    TextView txt_title;
    @BindView(R.id.txt_content)
    TextView txt_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_view);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        get_notification_thread();

    }

    public void get_notification_thread() {
        pd = ProgressDialog.show(this, "", "Please wait...");
        new Thread(new Runnable() {
            public void run() {
                get_notification();

            }


        }).start();

    }

    private void get_notification() {
        // TODO Auto-generated method stub
        try {
            httpcnt = new DefaultHttpClient();
            httpost = new HttpPost("http://" + General_Data.SERVER_IP_ADDRESS +
                    "/agriappserver/android/getnotification.php");
            nvp = new ArrayList<NameValuePair>(1);
            nvp.add(new BasicNameValuePair("id", getIntent().getStringExtra
                    ("notification_id")));


            httpost.setEntity(new UrlEncodedFormEntity(nvp));
            ResponseHandler<String> s = new BasicResponseHandler();
            response = httpcnt.execute(httpost, s);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    Log.d(General_Data.TAG, response);
                    pd.dismiss();


                    try {
                        JSONArray json = new JSONArray(response);
                        txt_date.setText(json.getJSONObject(0).getString("datetime"));
                        txt_content.setText(json.getJSONObject(0).getString("content"));
                        txt_title.setText(json.getJSONObject(0).getString("title"));


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast
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
                    Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast
                            .LENGTH_LONG).show();
                    Log.d(General_Data.TAG, e.getLocalizedMessage());
                    pd.dismiss();
                }
            });
        }
    }

}
