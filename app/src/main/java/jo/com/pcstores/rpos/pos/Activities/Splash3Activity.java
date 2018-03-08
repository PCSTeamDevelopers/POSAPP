package jo.com.pcstores.rpos.pos.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import jo.com.pcstores.rpos.R;

public class Splash3Activity extends AppCompatActivity {
    //DECLARE
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash3);

        //HIDE ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //INITIALIZE
        logo = findViewById(R.id.imgLogo);

        //SET ANIMATION FOR IMAGE
        Animation a= AnimationUtils.loadAnimation(Splash3Activity.this,R.anim.fadein);
        logo.startAnimation(a);

        new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Splash3Activity.this, LoginActivity.class);
                        startActivity(i);
                    }
                }, 3000);
            }
    }
