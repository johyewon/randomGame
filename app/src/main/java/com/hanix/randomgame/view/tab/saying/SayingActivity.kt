package com.hanix.randomgame.view.tab.saying;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hanix.randomgame.R;
import com.hanix.randomgame.view.event.OnSingleClickListener;

public class SayingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saying);

        init();
    }

    /**
     * findViewById 및 OnClickListener
     */
    private void init() {

    }


    /**
     * OnClickListener -> 중복 클릭 방지를 위해 OnSingleClickListener 를 사용한다.
     */
    OnSingleClickListener sayingClick = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {

        }
    };
}