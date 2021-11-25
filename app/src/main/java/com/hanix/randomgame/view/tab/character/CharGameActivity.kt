package com.hanix.randomgame.view.tab.character

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hanix.randomgame.R
import com.hanix.randomgame.common.app.GLog

class CharGameActivity : AppCompatActivity() {

    private var headCount = 0
    private var turnCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_char_game)

        getCount()
    }

    private fun getCount() {
        headCount = intent.getIntExtra("headCount", 0)
        turnCount = intent.getIntExtra("turnCount", 0)

        GLog.d("headCount : $headCount , turnCount : $turnCount")
    }

}
