package agriapp.studio.ndk.agriappstudio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Accessory_Supplier extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplier);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }


    @OnClick(R.id.button_supplier_profile)
    public void supplier(View v) {
//			Intent i=new Intent(this,Supplierprof.class);
//			startActivity(i);

    }

    @OnClick(R.id.button_accessory_list)
    public void accessory(View v) {
//			Intent i=new Intent(this,Accessorylist.class);
//			startActivity(i);

    }

    @OnClick(R.id.fab_supply)
    public void addnew(View v) {

//			Intent i=new Intent(this,Addnewaccessory.class);
//			startActivity(i);

    }

    @Override
    public void onBackPressed() {
    }
}
