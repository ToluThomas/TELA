package com.aun.tela.alphabets.application.util;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.aun.tela.alphabets.application.Application;

import java.io.IOException;

import io.meengle.util.Value;

public class Speech {

    static MediaPlayer player;

    /**
     * An interface for playback callback
     */
    public static interface PlaybackListener {
        public void onStart(String id);
        public void onDone(String id);
        public void onError(String id, Integer errorCode);

    }

    /**
     * Play a sound by it's resource id. Note that only one {@link MediaPlayer} is used, as such consequent
     * sounds would stop current playback
     * @param resid the resource id of the sound to be played
     * @param utteranceId a unique id for this playback
     * @param callback an instance of {@link PlaybackListener} to
     *                  receive play callbacks
     */
    public static void play(int resid, String utteranceId, final PlaybackListener callback){
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

}
