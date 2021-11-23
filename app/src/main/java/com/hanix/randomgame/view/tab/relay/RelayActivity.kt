package com.hanix.randomgame.view.tab.relay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hanix.randomgame.R;
import com.hanix.randomgame.view.event.OnSingleClickListener;

public class RelayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relay);


        init();
    }

    /**
     * findViewById 및 OnClickListener 설정
     */
    private void init() {

    }

    /**
     * OnClickListener -> 중복 클릭 방지를 위해 OnSingleClickListener 사용
     */
    OnSingleClickListener relayClick = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {

        }
    };
}