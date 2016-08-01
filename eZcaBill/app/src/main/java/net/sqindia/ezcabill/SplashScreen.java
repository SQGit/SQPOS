package net.sqindia.ezcabill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Cr
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {


                Intent i = new Intent(SplashScreen.this, DashBoard.class);
                startActivity(i);


                finish();
            }
        }, 2000);

    }
}