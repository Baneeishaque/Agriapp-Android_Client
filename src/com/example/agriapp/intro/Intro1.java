package com.example.agriapp.intro;

import com.example.agriapp.R;
import com.example.agriapp.R.id;
import com.example.agriapp.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class Intro1 extends Activity {
		
	ImageView imageView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro1);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView1.setOnTouchListener(new OnSwipeTouchListener(this) {
        	
        	 public void onSwipeLeft() {
                 //Toast.makeText(Introduction1.this, "left", Toast.LENGTH_SHORT).show();
                  Intent target = new Intent(Intro1.this, Intro2.class);
                 startActivity(target);
                 finish();
             }
        });
	}
}


	
