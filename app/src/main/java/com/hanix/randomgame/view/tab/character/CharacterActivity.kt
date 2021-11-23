package com.hanix.randomgame.view.tab.character

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hanix.randomgame.R
import com.hanix.randomgame.view.event.OnSingleClickListener

class CharacterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        init()
    }

    /**
     * findViewById 및 OnClickListener 사용
     */
    private fun init() {}

    /**
     * onClickListener -> 중복 클릭 방지를 위해 OnSingleClickListener
     */
    var charClick: OnSingleClickListener = object : OnSingleClickListener() {
        override fun onSingleClick(v: View) {}
    }
}