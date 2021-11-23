package com.hanix.randomgame.view.tab.character;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hanix.randomgame.R;
import com.hanix.randomgame.view.event.OnSingleClickListener;

public class CharacterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        init();
    }

    /**
     * findViewById 및 OnClickListener 사용
     */
    private void init() {

    }

    /**
     * onClickListener -> 중복 클릭 방지를 위해 OnSingleClickListener
     */
    OnSingleClickListener charClick = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {

        }
    };
}