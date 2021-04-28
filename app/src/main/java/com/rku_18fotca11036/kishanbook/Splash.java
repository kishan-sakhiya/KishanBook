package com.rku_18fotca11036.kishanbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.rku_18fotca11036.kishanbook.Fragment.HomeFragment;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    SharedPreferences preferences;
    ImageView imgSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        imgSplash = findViewById(R.id.imgSplash);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        imgSplash.startAnimation(animation);

        preferences = getSharedPreferences("activity",MODE_PRIVATE);
        String userPreference = preferences.getString("use", "");


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                if(userPreference.equals(""))
                {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(Splash.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,2300);
    }
}