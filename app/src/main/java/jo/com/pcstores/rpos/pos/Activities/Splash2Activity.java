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
    TextView O;
    TextView I;
    TextView N;
    TextView T;
    TextView ofSale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

    //HIDE ACTIONBAR
    ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    //INITIALIZE
    logo = findViewById(R.id.imgLogo);
    O = findViewById(R.id.txtO);
    I = findViewById(R.id.txtI);
    N = findViewById(R.id.txtN);
    T = findViewById(R.id.txtT);
    ofSale = findViewById(R.id.txtOfSale);

    //MAKE TEXT INVISIBLE
        O.setVisibility(View.INVISIBLE);
        I.setVisibility(View.INVISIBLE);
        N.setVisibility(View.INVISIBLE);
        T.setVisibility(View.INVISIBLE);
        ofSale.setVisibility(View.INVISIBLE);

    //SET ANIMATION FOR IMAGE
    Animation a= AnimationUtils.loadAnimation(Splash2Activity.this,R.anim.fadein);
        logo.startAnimation(a);

    //SHOW TEXT
        new android.os.Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            O.setVisibility(View.VISIBLE);
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    I.setVisibility(View.VISIBLE);
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            N.setVisibility(View.VISIBLE);
                            new android.os.Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    T.setVisibility(View.VISIBLE);

                                            new android.os.Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ofSale.setVisibility(View.VISIBLE);
                                                    new android.os.Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Intent i = new Intent(Splash2Activity.this,LoginActivity.class);
                                                            startActivity(i);
                                                        }
                                                    },500);
                                                }
                                            },200);
                                        }
                                    },200);
                        }
                    },200);
                }
            },200);
        }
    },1000);
}
}


