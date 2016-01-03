package com.aun.tela.alphabets.application.util;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import com.aun.tela.alphabets.application.Application;

import java.io.IOException;
import java.util.Locale;

import io.meengle.util.Value;

/**
 * Created by Joseph Dalughut on 01/01/16 at 8:21 AM.
 * Project name : TELA.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class Speech {

    static TextToSpeech tts;
    static MediaPlayer player;

    public static void init(){
        tts = new TextToSpeech(Application.getInstance(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.UK);
            }
        });
    }

    public static interface VoiceCallback {
        public void onStart(String id);
        public void onDone(String id);
        public void onError(String id, Integer errorCode);

    }

    public static void play(int resid, String utteranceId, final VoiceCallback callback){
        try {
            if (!Value.NULL(player)) {
                try {
                    player.reset();
                } catch (Exception e) {
                    Log.d("Exception resetting "+e.getMessage());
                    player = new MediaPlayer();
                }
            } else {
                player = new MediaPlayer();
            }
            AssetFileDescriptor afd = Application.getInstance().getResources().openRawResourceFd(resid);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            //player.setVolume(0, 0);
            afd.close();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            callback.onDone(null);
                            mp.release();
                        }
                    });
                    mp.start();
                }
            });
            player.prepareAsync();
        }catch (IOException e){
            Log.d("Exception "+e.getMessage());
            callback.onDone("");
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private static UtteranceProgressListener toUPL(final VoiceCallback callback){
        return new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                callback.onStart(utteranceId);
            }

            @Override
            public void onDone(String utteranceId) {
                Log.d("Done speaking");
                callback.onDone(utteranceId);
            }

            @Override
            public void onError(String utteranceId) {
                callback.onError(utteranceId, null);
            }
        };
    }


    private static TextToSpeech.OnUtteranceCompletedListener toOUCL(final VoiceCallback callback){
        return new TextToSpeech.OnUtteranceCompletedListener() {
            @Override
            public void onUtteranceCompleted(String utteranceId) {
                callback.onDone(utteranceId);
            }
        };
    }

}
