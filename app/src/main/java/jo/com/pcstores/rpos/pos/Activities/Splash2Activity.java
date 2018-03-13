package jo.com.pcstores.rpos.pos.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import jo.com.pcstores.rpos.R;

public class Splash2Activity extends AppCompatActivity {
    ImageView logo;
    ImageView pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

    //HIDE ACTIONBAR
    ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    //INITIALIZE
    logo = findViewById(R.id.imgLogo);
    pos = findViewById(R.id.imgpos);

    //MAKE TEXT INVISIBLE
        pos.setVisibility(View.INVISIBLE);

    //SET ANIMATION FOR IMAGE
//    Animation a= AnimationUtils.loadAnimation(Splash2Activity.this,R.anim.fadein);
//        logo.startAnimation(a);

    //SHOW TEXT
            Animation a= AnimationUtils.loadAnimation(Splash2Activity.this,R.anim.fadein);
            pos.startAnimation(a);
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation a= AnimationUtils.loadAnimation(Splash2Activity.this,R.anim.blink);
                    logo.startAnimation(a);
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                          Intent i = new Intent(Splash2Activity.this,LoginActivity.class);
                          startActivity(i);
                          finish();
                        }
                    },2000);
                }
            },1000);


        }

}



