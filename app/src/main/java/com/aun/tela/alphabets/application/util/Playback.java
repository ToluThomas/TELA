package com.aun.tela.alphabets.application.util;

import android.animation.Animator;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.aun.tela.alphabets.application.Application;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import io.meengle.util.Value;

/**
 * Created by Joseph Dalughut on 01/01/16 at 8:21 AM.
 * Project name : TELA.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class Playback {

    static MediaPlayer player;
    static Set<Animator> animators = new HashSet<>();
    static boolean paused = false;

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

    public static void release(){
        try {
            try {
                if (!Value.NULL(player)) {
                    player.stop();
                    player.release();
                    player.reset();
                }
            } catch (IllegalStateException e) {

            }
            stopAnimators();
            animators.clear();
        }catch (Exception e){

        }
    }

    public static void stopAnimators(){
        if(animators.isEmpty()) return;
        for(Animator animator : animators){
            animator.end();
        }
    }

    public static void pausePlayer(){
        Log.d("Player Paused");
        try {
            if (!Value.NULL(player) && player.isPlaying()) {
                player.pause();
            }
        }catch (IllegalStateException e){

        }
    }

    public static void playPlayer(){
        Log.d("Player played");
        try {
            if (!Value.NULL(player) && !player.isPlaying()) {
                player.start();
            }
        }catch (IllegalStateException e){

        }
    }

    public static void pauseAnimators(){
        Log.d("Animators Paused");
        if(Value.EMPTY(animators)) return;
        for(Animator animator : animators){
            animator.pause();
        }
    }

    public static void pause(){
        Log.d("Playback paused");
        if(paused) return;
        pauseAnimators();
        pausePlayer();
        paused = true;
    }

    public static void resume(){
        Log.d("Playback resumed");
        if(!paused) return;
        playAnimators();
        playPlayer();
        paused = false;
    }

    public static void play(Animator... animators){
        commit(animators);
        paused = false;
        for(Animator animator: animators) {
            if(!animator.isRunning())
            animator.start();
        }
    }

    public static void playEvenIfPaused(Animator... animators){
        commit(animators);
        paused = false;
        playAnimators();
        playPlayer();
    }


    public static void playAnimators(){
        if(Value.EMPTY(animators)) return;
        for(Animator animator : animators){
            if(animator.isPaused()) {
                animator.resume();
            }else{
                animator.start();
            }
        }
    }

    public static boolean toggle(){
        if(paused())
            resume();
        else
            pause();
        return paused;
    }

    public static boolean paused(){
        return paused;
    }

    private static void commit(final Animator... animators){
        for(Animator animator : animators) {
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    try{
                        uncommit(animation);
                    }catch (Exception e){

                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            Playback.animators.add(animator);
        }
    }

    public static void uncommit(Animator... animators){
        for(Animator animator : animators)
        try{
            Playback.animators.remove(animator);
        }catch (Exception e){

        }
    }

    public static class PausableAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
            Playback.commit(animation);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            Playback.uncommit(animation);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

}
