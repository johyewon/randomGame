package com.hanix.randomgame.view.tab.object;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hanix.randomgame.R;
import com.hanix.randomgame.view.event.OnSingleClickListener;

public class ObjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);

        init();
    }

    /**
     * findViewById 및 OnClickListener
     */
    private void init() {

    }

    /**
     * onClickListener -> 중복 클릭 방지를 위해 OnSingleClickListener 사용
     */
    OnSingleClickListener objectClick = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {

        }
    };
}