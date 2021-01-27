package com.hanix.randomgame.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.hanix.randomgame.R;

public class AppIntro extends AppCompatActivity {

    private static final long SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_intro);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(AppIntro.this, MainActivity.class));
            overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity);
            finish();
        }, SPLASH_TIME);
    }


    @Override
    public void onBackPressed() {
        // 동작이 없어야 인트로 화면에서 백버튼 클릭 시 앱이 꺼지지 않음
    }
}
