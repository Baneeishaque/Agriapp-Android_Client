package agriapp.studio.ndk.agriappstudio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Seller extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.fab_sell)
    public void prod(View v) {

//			Intent i=new Intent(this,Sellproduct.class);
//			startActivity(i);

    }

    @OnClick(R.id.button_product_list)
    public void prodlist(View v) {

//			Intent i=new Intent(this,Productlist.class);
//			startActivity(i);

    }

    @OnClick(R.id.button_seller_profile)
    public void prof(View v) {
//			Intent i=new Intent(this,Sellerprofile.class);
//			startActivity(i);

    }

    @Override
    public void onBackPressed() {
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
}
