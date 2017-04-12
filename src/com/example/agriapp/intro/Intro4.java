package com.example.agriapp.intro;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.agriapp.R;
import com.example.agriapp.homes.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Intro4 extends Activity {

	
		ImageView imageView1;
		FileOutputStream fos;
		int s = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro4);
		imageView1=(ImageView) findViewById(R.id.imageView1);
        imageView1.setOnTouchListener(new OnSwipeTouchListener(this) {
            /*public void onSwipeTop() {
                //Toast.makeText(Introduction1.this, "top", Toast.LENGTH_SHORT).show();
            }*/
            public void onSwipeRight() {
                //Toast.makeText(Introduction2.this, "right", Toast.LENGTH_SHORT).show();
                Intent target = new Intent(Intro4.this, Intro3.class);
                startActivity(target);
                finish();

            }
            public void onSwipeLeft() {
            	String FILE = "ini";

        		try {

        			fos = openFileOutput(FILE, Context.MODE_PRIVATE);
        			try {
        				fos.write(s);
        				fos.close();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        		} catch (FileNotFoundException e) {
        		}
        		Intent target = new Intent(Intro4.this, Login.class);
        		startActivity(target);
                finish();
            }
            /*public void onSwipeBottom() {
                //Toast.makeText(Introduction1.this, "bottom", Toast.LENGTH_SHORT).show();
            }*/

        });
    }

}