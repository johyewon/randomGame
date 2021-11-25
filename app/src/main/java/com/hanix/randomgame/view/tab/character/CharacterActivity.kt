package com.hanix.randomgame.view.tab.character

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.hanix.randomgame.R
import com.hanix.randomgame.common.utils.KeyboardUtil
import com.hanix.randomgame.common.utils.ToastUtil

class CharacterActivity : AppCompatActivity() {

    private val submitButton: Button by lazy {
        findViewById(R.id.submitButton)
    }

    private val charTurnCount: EditText by lazy {
        findViewById(R.id.charTurnCount)
    }

    private val charPeopleCount: EditText by lazy {
        findViewById(R.id.charPeopleCount)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        init()
    }

    /**
     * findViewById 및 OnClickListener 사용
     */
    private fun init() {
        submitButton.setOnClickListener {
            if (nullCheck()) {
                val intent = Intent(this@CharacterActivity, CharGameActivity::class.java)
                intent.putExtra("headCount", charPeopleCount.text.toString().toInt())
                intent.putExtra("turnCount", charTurnCount.text.toString().toInt())

                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_left, R.anim.hold_activity)
            }
        }
    }

    /**
     * 입력해야 할 사항을 전부 확인하고 예외처리를 한다.
     */
    private fun nullCheck(): Boolean {

        // 인원수 입력 안 되어 있을 때
        if (charPeopleCount.text.isEmpty() || charPeopleCount.text == null) {
            ToastUtil.showToastS(this@CharacterActivity, "인원수를 입력해 주세요")
            charPeopleCount.requestFocus()
            KeyboardUtil.showKeyboard(this, charPeopleCount)
            return false
        }

        // 인원수가 1 명 미만일 때
        if (charPeopleCount.text.toString().toInt() <= 0) {
            ToastUtil.showToastS(this@CharacterActivity, "최소 1인 이상이어야 합니다.")
            KeyboardUtil.showKeyboard(this, charPeopleCount)
            return false
        }

        // 턴수가 입력이 안 되어 있을 때
        if (charTurnCount.text.isEmpty() || charTurnCount.text == null) {
            ToastUtil.showToastS(this@CharacterActivity, "턴수를 입력해 주새요")
            charTurnCount.requestFocus()
            KeyboardUtil.showKeyboard(this, charTurnCount)
            return false
        }

        // 턴수가 1 턴 미만일 때
        if (charTurnCount.text.toString().toInt() <= 0) {
            ToastUtil.showToastS(this@CharacterActivity, "최소 턴수는 1 이상이어야 합니다")
            charTurnCount.requestFocus()
            KeyboardUtil.showKeyboard(this, charTurnCount)
            return false
        }

        return true
    }


}