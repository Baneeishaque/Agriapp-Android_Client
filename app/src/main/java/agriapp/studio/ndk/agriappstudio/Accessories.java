package agriapp.studio.ndk.agriappstudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Accessories extends AppCompatActivity {
    String[] accessories = {"Fertilizers", "Pesticides", "Seeds"};
    @BindView(R.id.spinner_accessory)
    Spinner spinner_accessory;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accessories);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ArrayAdapter<String> accessories_adaptor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, accessories);
        accessories_adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_accessory.setAdapter(accessories_adaptor);
    }

    @OnClick(R.id.button_search)
    public void search(View v) {
        if (spinner_accessory.getSelectedItem().toString().equals("Fertilizers")) {
            Intent i = new Intent(this, Fertilizers.class);
            startActivity(i);
        }
    }

}
