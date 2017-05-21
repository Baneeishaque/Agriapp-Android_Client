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

public class Labour extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.labour);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
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
