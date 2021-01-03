package com.hanix.randomgame.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hanix.randomgame.R;
import com.hanix.randomgame.view.event.OnSingleClickListener;

public class MainActivity extends AppCompatActivity {

    TextView characterText, idiomText, sayingText, relayText, bodyText, objectText;
    ImageView characterImage, idiomImage, sayingImage, relayImage, bodyImage, objectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        characterText = findViewById(R.id.characterText);
        idiomText = findViewById(R.id.idiomText);
        sayingText = findViewById(R.id.sayingText);
        relayText = findViewById(R.id.relayText);
        bodyText = findViewById(R.id.bodyText);
        objectText = findViewById(R.id.objectText);
        characterImage = findViewById(R.id.characterImage);
        idiomImage = findViewById(R.id.idiomImage);
        sayingImage = findViewById(R.id.sayingImage);
        relayImage = findViewById(R.id.relayImage);
        bodyImage = findViewById(R.id.bodyImage);
        objectImage = findViewById(R.id.objectImage);

        characterText.setOnClickListener(mainClick);
        idiomText.setOnClickListener(mainClick);
        sayingText.setOnClickListener(mainClick);
        relayText.setOnClickListener(mainClick);
        bodyText.setOnClickListener(mainClick);
        objectText.setOnClickListener(mainClick);
        characterImage.setOnClickListener(mainClick);
        idiomImage.setOnClickListener(mainClick);
        sayingImage.setOnClickListener(mainClick);
        relayImage.setOnClickListener(mainClick);
        bodyImage.setOnClickListener(mainClick);
        objectImage.setOnClickListener(mainClick);
    }

    OnSingleClickListener mainClick = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            int id = v.getId();
            if (id == R.id.characterText || id == R.id.characterImage) {        // 인물퀴즈
            } else if (id == R.id.idiomText || id == R.id.idiomImage) {         // 사자성어
            } else if (id == R.id.sayingText || id == R.id.sayingImage) {       // 속담
            } else if (id == R.id.relayText || id == R.id.relayImage) {         // 이어말하기
            } else if (id == R.id.bodyText || id == R.id.bodyImage) {           // 몸으로 말해요
            } else if (id == R.id.objectText || id == R.id.objectImage) {       // 사물퀴즈
            }
        }
    };

    private void goActivity(Class<?> activityClass) {
        startActivity(new Intent(MainActivity.this, activityClass));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
