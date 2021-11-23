package com.hanix.randomgame.view.tab.idiom

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hanix.randomgame.R
import com.hanix.randomgame.view.event.OnSingleClickListener

class IdiomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idiom)
        init()
    }

    /**
     * findViewById 및 OnClickListener 설정
     */
    private fun init() {}

    /**
     * onClickListener -> 중복 클릭 방지를 위해 OnSingleClickListener 사용
     */
    var idiomClick: OnSingleClickListener = object : OnSingleClickListener() {
        override fun onSingleClick(v: View) {}
    }
}