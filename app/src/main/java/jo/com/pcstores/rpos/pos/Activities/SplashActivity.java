package jo.com.pcstores.rpos.pos.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Classes.GlobalVar;

public class SplashActivity extends AppCompatActivity {
    ImageView logo;
    ImageView pos;
    //SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_splash);

       //Control orientation depending on size
        int screenSize = getResources().getConfiguration().screenLayout &Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize==Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else{
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

       //HIDE ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    //INITIALIZE
    logo = findViewById(R.id.imgLogo);
    pos = findViewById(R.id.imgpos);

    //MAKE TEXT INVISIBLE
        pos.setVisibility(View.INVISIBLE);


    //SHOW TEXT
            Animation a= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.fadein);
            pos.startAnimation(a);
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation a= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.blink);
                    logo.startAnimation(a);
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                            boolean keepMeLogged = sharedPreferences.getBoolean("keep me logged",false);
                            //Read Cashier Name fro shared preferences
                            String cashierName = sharedPreferences.getString("cashier name","");
                            GlobalVar.CashierName = cashierName;
                            if (keepMeLogged){
                                Intent i = new Intent(SplashActivity.this,NavMainActivity.class);
                                startActivity(i);
                                finish();
                            }else {
                                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    },2000);
                }
            },1000);


        }

}



