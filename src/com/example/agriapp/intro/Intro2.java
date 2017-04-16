package com.example.agriapp.intro;

import com.example.agriapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;


public class Intro2 extends Activity {

	 ImageView imageView2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro2);
		imageView2=(ImageView) findViewById(R.id.imageView2);
        imageView2.setOnTouchListener(new OnSwipeTouchListener(this) {
        	
        	 public void onSwipeRight() {
                 //Toast.makeText(Introduction2.this, "right", Toast.LENGTH_SHORT).show();
                 Intent target = new Intent(Intro2.this, Intro1.class);
                 startActivity(target);
                 finish();

             }
             public void onSwipeLeft() {
                 //Toast.makeText(Introduction2.this, "left", Toast.LENGTH_SHORT).show();
                 Intent target = new Intent(Intro2.this, Intro3.class);
                 startActivity(target);
                 finish();
             }
             /*public void onSwipeBottom() {
                 //Toast.makeText(Introduction1.this, "bottom", Toast.LENGTH_SHORT).show();
             }*/

         });
     }
}
