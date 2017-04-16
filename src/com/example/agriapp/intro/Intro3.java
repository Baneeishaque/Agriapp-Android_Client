package com.example.agriapp.intro;

import com.example.agriapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Intro3 extends Activity {

	
    ImageView imageView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro3);
		imageView1=(ImageView) findViewById(R.id.imageView1);
        imageView1.setOnTouchListener(new OnSwipeTouchListener(this) {
            /*public void onSwipeTop() {
                //Toast.makeText(Introduction1.this, "top", Toast.LENGTH_SHORT).show();
            }*/
            public void onSwipeRight() {
                //Toast.makeText(Introduction2.this, "right", Toast.LENGTH_SHORT).show();
                Intent target = new Intent(Intro3.this, Intro2.class);
                startActivity(target);
                finish();

            }
            public void onSwipeLeft() {
                //Toast.makeText(Introduction2.this, "left", Toast.LENGTH_SHORT).show();
                Intent target = new Intent(Intro3.this, Intro4.class);
                startActivity(target);
                finish();
            }
            /*public void onSwipeBottom() {
                //Toast.makeText(Introduction1.this, "bottom", Toast.LENGTH_SHORT).show();
            }*/

        });
    }

}
