package agriapp.studio.ndk.agriappstudio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                try {
                    Thread.sleep(2000);
                    load();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    Log.d(General_Data.TAG, e.getLocalizedMessage());
                    e.printStackTrace();
                }


            }
        }).start();

    }

    private void load() {

        SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        if (settings.getString("login_status", "0").equals("1")) {
            String role = settings.getString("user_role", "login");


            switch (role) {

                case "farmer":

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(Launcher.this, Farmer_Home.class);
                            startActivity(i);
                            finish();
                        }
                    });

                    break;
                case "officer":

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(Launcher.this, Officer.class);
                            startActivity(i);
                            finish();
                        }
                    });
                    break;
                case "seller":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(Launcher.this, Seller.class);
                            startActivity(i);
                            finish();
                        }
                    });
                    break;

                case "supplier":

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(Launcher.this, Accessory_Supplier.class);
                            startActivity(i);
                            finish();
                        }
                    });
                    break;
                case "labour":

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(Launcher.this, Labour.class);
                            startActivity(i);
                            finish();
                        }
                    });
                    break;
                case "login":

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Launcher.this, "Settings Error : Login Please...", Toast
                                    .LENGTH_LONG).show();
                            Intent target = new Intent(Launcher.this, Login.class);
                            startActivity(target);
                            finish();
                        }
                    });

            }
        } else {
            if (settings.getString("introduction_status", "0").equals("1")) {
                Intent target = new Intent(this, Login.class);
                startActivity(target);
                finish();
            } else {
                Intent target = new Intent(this, Introduction1.class);
                startActivity(target);
                finish();
            }
        }

    }


}
