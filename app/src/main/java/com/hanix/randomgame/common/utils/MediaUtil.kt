package com.hanix.randomgame.common.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.hanix.randomgame.common.app.GLog;

import java.util.Timer;
import java.util.TimerTask;

public class MediaUtil {

    float stopVolume = 1;
    float startVolume = 0;

    /**
     * 소리 점점 줄이면서 끄기
     * @param mediaPlayer
     */
    private void startFadeOut(MediaPlayer mediaPlayer) {
        final int FADE_DURATION = 3000;
        final int FADE_INTERVAL = 250;
        int numberOfSteps = FADE_DURATION / FADE_INTERVAL;
        final float deltaVolume = stopVolume / numberOfSteps;

        final Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                fadeOutStep(deltaVolume, mediaPlayer);
                if (stopVolume <= 0) {
                    timer.cancel();
                    timer.purge();
                    stopPlayer(mediaPlayer);
                    stopVolume = 1;
                }
            }
        };
        timer.schedule(timerTask, FADE_INTERVAL, FADE_INTERVAL);
    }

    /**
     * 음량 줄이기
     * @param deltaVolume
     * @param mediaPlayer
     */
    private void fadeOutStep(float deltaVolume, MediaPlayer mediaPlayer) {
        mediaPlayer.setVolume(stopVolume, stopVolume);
        stopVolume -= deltaVolume;
    }

    /**
     * 노래 끄기
     * @param mediaPlayer
     */
    private void stopPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }


    /**
     * 소리 점점 키우면서 노래 켜기
     * - 주의사항 : mediaPlayer.start(); 한 후 부르기
     * @param mediaPlayer
     */
    private void startFadeIn(MediaPlayer mediaPlayer) {
        final int FADE_DURATION = 3000;
        final int FADE_INTERVAL = 250;
        final int MAX_VOLUME = 1;
        int numberOfSteps = FADE_DURATION / FADE_INTERVAL;
        final float deltaVolume = MAX_VOLUME / (float) numberOfSteps;

        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                fadeInStep(deltaVolume, mediaPlayer);
                if(startVolume >= 1f) {
                    timer.cancel();
                    timer.purge();
                    startVolume = 0;
                }
            }
        };
        timer.schedule(timerTask, FADE_INTERVAL, FADE_INTERVAL);
    }

    /**
     * 소리 점점 키우기
     * @param deltaVolume
     * @param mediaPlayer
     */
    private void fadeInStep(float deltaVolume, MediaPlayer mediaPlayer) {
        mediaPlayer.setVolume(startVolume, startVolume);
        startVolume += deltaVolume;
    }

    /** 이미 음악이 실행 중일 경우 오디오 가져오기**/
    private void audioFocusSdkVersion(Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager != null) {
            //첫 번째 인자로 listener 를 줘야하지만, 음악 일시정지만 하고 싶은 경우엔 null 로 준다.
            int result = mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if(result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                GLog.d("not get audio focus");
            }
        }
    }
}
