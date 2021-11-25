package com.hanix.randomgame.view.tab.saying

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hanix.randomgame.R
import com.hanix.randomgame.view.event.OnSingleClickListener

class SayingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saying)
        init()
    }

    /**
     * findViewById 및 OnClickListener
     */
    private fun init() {}

    /**
     * OnClickListener -> 중복 클릭 방지를 위해 OnSingleClickListener 를 사용한다.
     */
    var sayingClick: OnSingleClickListener = object : OnSingleClickListener() {
        override fun onSingleClick(v: View?) {}
    }
}