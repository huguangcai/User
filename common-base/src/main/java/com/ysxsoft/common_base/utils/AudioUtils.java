package com.ysxsoft.common_base.utils;

import android.content.Context;
import android.media.AudioManager;

/**
 * create by Sincerly on 2019/1/11 0011
 **/
public class AudioUtils {
    public static void setAudioVolume(Context context,int progress){
        AudioManager audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);//tempVolume:音量绝对值
    }
    /**
     * 获取媒体音量最大值
     * @param context
     * @return
     */
    public static int getMaxAudioVolume(Context context){
        AudioManager audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        return maxVolume;
    }

    /**
     * 获取当前媒体音量
     * @param context
     * @return
     */
    public static int getCurrentAudioVolume(Context context){
        AudioManager audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int streamVolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        return streamVolume;
    }

    /**
     * 减少音量
     * @param context
     */
    public static void reduceVolume(Context context){
        AudioManager mAudioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FX_FOCUS_NAVIGATION_UP);
    }

    /**
     * 增加音量
     * @param context
     */
    public static void increaseVolume(Context context){
        AudioManager mAudioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.adjustStreamVolume (AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE,AudioManager.FX_FOCUS_NAVIGATION_UP);
    }
}
