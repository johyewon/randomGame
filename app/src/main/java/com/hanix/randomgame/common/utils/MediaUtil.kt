package com.hanix.randomgame.common.utils

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import com.hanix.randomgame.common.app.GLog.d
import java.util.*

class MediaUtil {
    var stopVolume = 1f
    var startVolume = 0f

    /**
     * 소리 점점 줄이면서 끄기
     * @param mediaPlayer
     */
    private fun startFadeOut(mediaPlayer: MediaPlayer) {
        val fadeDuration = 3000
        val fadeInterval = 250

        val numberOfSteps = fadeDuration / fadeInterval
        val deltaVolume = stopVolume / numberOfSteps

        val timer = Timer(true)
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                fadeOutStep(deltaVolume, mediaPlayer)
                if (stopVolume <= 0) {
                    timer.cancel()
                    timer.purge()
                    stopPlayer(mediaPlayer)
                    stopVolume = 1f
                }
            }
        }
        timer.schedule(timerTask, fadeInterval.toLong(), fadeInterval.toLong())
    }

    /**
     * 음량 줄이기
     * @param deltaVolume
     * @param mediaPlayer
     */
    private fun fadeOutStep(deltaVolume: Float, mediaPlayer: MediaPlayer) {
        mediaPlayer.setVolume(stopVolume, stopVolume)
        stopVolume -= deltaVolume
    }

    /**
     * 노래 끄기
     * @param mediaPlayer
     */
    private fun stopPlayer(mediaPlayer: MediaPlayer?) {
        if (mediaPlayer != null && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    /**
     * 소리 점점 키우면서 노래 켜기
     * - 주의사항 : mediaPlayer.start(); 한 후 부르기
     * @param mediaPlayer
     */
    private fun startFadeIn(mediaPlayer: MediaPlayer) {
        val fadeDuration = 3000
        val fadeInterval = 250
        val maxVolume = 1
        val numberOfSteps = fadeDuration / fadeInterval
        val deltaVolume = maxVolume / numberOfSteps.toFloat()

        val timer = Timer()
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                fadeInStep(deltaVolume, mediaPlayer)
                if (startVolume >= 1f) {
                    timer.cancel()
                    timer.purge()
                    startVolume = 0f
                }
            }
        }
        timer.schedule(timerTask, fadeInterval.toLong(), fadeInterval.toLong())
    }

    /**
     * 소리 점점 키우기
     * @param deltaVolume
     * @param mediaPlayer
     */
    private fun fadeInStep(deltaVolume: Float, mediaPlayer: MediaPlayer) {
        mediaPlayer.setVolume(startVolume, startVolume)
        startVolume += deltaVolume
    }

    /** 이미 음악이 실행 중일 경우 오디오 포커스 내 앱으로 변경 */
    private fun audioFocusSdkVersion(context: Context) {
        val mAudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        //첫 번째 인자로 listener 를 줘야하지만, 음악 일시정지만 하고 싶은 경우엔 null 로 준다.
        val result = mAudioManager.requestAudioFocus(
            null,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN
        )
        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            d("not get audio focus")
        }
    }
}