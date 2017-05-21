package agriapp.studio.ndk.agriappstudio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Blog_List extends AppCompatActivity {

    ProgressDialog pd;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.list_search_crop_result)
    ListView list_notifications;

    DefaultHttpClient httpcnt;
    HttpPost httpost;
    ArrayList<NameValuePair> nvp;
    String response;

    ArrayList<String> item_list_array;

    ArrayList<Notification_Modal> notification_Modal_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_crop_result);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        pd = ProgressDialog.show(this, "", "Please wait...");
        new Thread(new Runnable() {
            public void run() {
                get_blog();

            }


        }).start();
    }

    private void get_blog() {
        // TODO Auto-generated method stub
        try {
            httpcnt = new DefaultHttpClient();
            httpost = new HttpPost("http://" + General_Data.SERVER_IP_ADDRESS +
                    "/agriappserver/android/getblogall" +
                    ".php");

            ResponseHandler<String> s = new BasicResponseHandler();
            response = httpcnt.execute(httpost, s);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    pd.dismiss();

                    item_list_array = new ArrayList<String>();
                    notification_Modal_list = new ArrayList<>();
                    try {

                        JSONArray json = new JSONArray(response);
                        for (int i = 0; i < json.length(); i++) {


                            // Populate spinner with country names
                            item_list_array.add(json.getJSONObject(i).getString("title"));

                            Notification_Modal sample_notificationModal = new Notification_Modal();
                            sample_notificationModal.setId(json.getJSONObject(i).getString("id"));
                            sample_notificationModal.setTitle(json.getJSONObject(i).getString("title"));
                            sample_notificationModal.setDatetime(json.getJSONObject(i).getString
                                    ("datetime"));
                            sample_notificationModal.setContent(json.getJSONObject(i).getString
                                    ("content"));
                            sample_notificationModal.setProvider(json.getJSONObject(i).getString
                                    ("username"));

                            notification_Modal_list.add(sample_notificationModal);


                        }
                        list_notifications
                                .setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                        R.layout.list_item_textview,
                                        item_list_array));
                        list_notifications.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent i = new Intent(getApplicationContext(), Blog_View.class);
                                i.putExtra("notification_id", notification_Modal_list.get(position).getId());
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
