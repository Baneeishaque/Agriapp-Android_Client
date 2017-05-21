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

public class Officer extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.officer);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.button_add_notification)
    public void addnotif(View v) {

//			Intent i=new Intent(this,Addnotification.class);
//			startActivity(i);

    }

    @OnClick(R.id.button_add_cop)
    public void addcrop(View v) {

//			Intent i=new Intent(this,Addcrop.class);
//			startActivity(i);

    }

    @OnClick(R.id.button_articles)
    public void articles(View v) {
//			Intent i=new Intent(this,Blog_List.class);
//			startActivity(i);

    }

    @OnClick(R.id.fab_consultation)
    public void msg(View v) {
//
//			Intent i=new Intent(this,Message.class);
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
