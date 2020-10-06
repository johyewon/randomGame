package com.hanix.randomgame.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hanix.randomgame.R;

import org.jetbrains.annotations.NotNull;

public class AppIntro extends AppCompatActivity {

    private static final long SPLASH_TIME = 2000;
    private static final int STOP_SPLASH = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_intro);

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NotNull Message msg) {
            super.handleMessage(msg);

            Intent intent;

            if (msg.what == STOP_SPLASH) {// 2020-05-20 튜토리얼 매번 나오게 수정
                intent = new Intent(AppIntro.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_activity, R.anim.hold_activity);
                finish();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Message msg = new Message();
        msg.what = STOP_SPLASH;
        handler.sendMessageDelayed(msg, SPLASH_TIME);
    }

}
