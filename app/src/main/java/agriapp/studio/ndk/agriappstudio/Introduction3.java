package agriapp.studio.ndk.agriappstudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Introduction3 extends AppCompatActivity {

    @BindView(R.id.intro_imageView)
    ImageView intro_imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction);

        ButterKnife.bind(this);

        Toast.makeText(Introduction3.this, "Introduction 3", Toast.LENGTH_SHORT).show();
        intro_imageView.setImageResource(R.mipmap.ic_launcher);
        intro_imageView.setOnTouchListener(new OnSwipeTouchListener(this) {
            /*public void onSwipeTop() {
                //Toast.makeText(Introduction1.this, "top", Toast.LENGTH_SHORT).show();
            }*/
            public void onSwipeRight() {
                //Toast.makeText(Introduction2.this, "right", Toast.LENGTH_SHORT).show();
                Intent target = new Intent(Introduction3.this, Introduction2.class);
                startActivity(target);
                finish();

            }

            public void onSwipeLeft() {
                //Toast.makeText(Introduction2.this, "left", Toast.LENGTH_SHORT).show();
                Intent target = new Intent(Introduction3.this, Introduction4.class);
                startActivity(target);
                finish();
            }
            /*public void onSwipeBottom() {
                //Toast.makeText(Introduction1.this, "bottom", Toast.LENGTH_SHORT).show();
            }*/

        });
    }

}
