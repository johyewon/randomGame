package com.hanix.randomgame.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hanix.randomgame.R
import com.hanix.randomgame.view.tab.`object`.ObjectActivity
import com.hanix.randomgame.view.tab.body.BodyActivity
import com.hanix.randomgame.view.tab.character.CharacterActivity
import com.hanix.randomgame.view.tab.idiom.IdiomActivity
import com.hanix.randomgame.view.tab.relay.RelayActivity
import com.hanix.randomgame.view.tab.saying.SayingActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * OnClickListener
     */
    fun gameClick(v: View) {
        when (v.id) {
            R.id.characterText, R.id.characterImage -> {        // 인물퀴즈
                goActivity(CharacterActivity::class.java)
            }
            R.id.idiomText, R.id.idiomImage -> {         // 사자성어
                goActivity(IdiomActivity::class.java)
            }
            R.id.sayingText, R.id.sayingImage -> {       // 속담
                goActivity(SayingActivity::class.java)
            }
            R.id.relayText, R.id.relayImage -> {         // 이어말하기
                goActivity(RelayActivity::class.java)
            }
            R.id.bodyText, R.id.bodyImage -> {           // 몸으로 말해요
                goActivity(BodyActivity::class.java)
            }
            R.id.objectText, R.id.objectImage -> {       // 사물퀴즈
                goActivity(ObjectActivity::class.java)
            }
        }
    }

    /**
     * activity 이동
     *
     * @param activityClass
     */
    private fun goActivity(activityClass: Class<*>) {
        startActivity(Intent(this@MainActivity, activityClass))
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}