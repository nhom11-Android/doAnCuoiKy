package myHelper;

import android.content.Context;
import android.media.MediaPlayer;

public class MySound {
    public static float amThanh = 1;
    public static float nhacNen = (float)0.5;

    public static MediaPlayer mediaPlayerAmThanh;
    public static MediaPlayer mediaPlayerNhacNen;

    public static void startNhacNen( Context context, int resId){
        mediaPlayerNhacNen = MediaPlayer.create(context, resId);
        mediaPlayerNhacNen.setLooping(true);
        mediaPlayerNhacNen.setVolume(nhacNen, nhacNen);
        mediaPlayerNhacNen.start();
    }

    public static void stopNhacNen(){
        mediaPlayerNhacNen.reset();
        mediaPlayerNhacNen.release();
        mediaPlayerNhacNen = null;
    }

    public static void settingNhacNen(){
        mediaPlayerNhacNen.setVolume(nhacNen, nhacNen);
    }

    public static void amThanhGame( Context context, int resId){
        mediaPlayerAmThanh = MediaPlayer.create(context, resId);
        mediaPlayerAmThanh.setLooping(false);
        mediaPlayerAmThanh.setVolume(amThanh, amThanh);
        mediaPlayerAmThanh.start();
        mediaPlayerAmThanh.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayerAmThanh.reset();
                mediaPlayerAmThanh.release();
                mediaPlayerAmThanh = null;
            }
        });
    }

    public static void stopAmThanh(){
        try {
            if (mediaPlayerAmThanh.isPlaying()) {
                mediaPlayerAmThanh.reset();
                mediaPlayerAmThanh.release();
                mediaPlayerAmThanh = null;
            }
        }catch (Exception e){

        }
    }

    public static void setAmThanh(float amThanh) {
        MySound.amThanh = amThanh;
    }

    public static void setNhacNen(float nhacNen) {
        MySound.nhacNen = nhacNen;
    }

    public static boolean nhacNenIsPlaying(){
        try{
            return mediaPlayerNhacNen.isPlaying();
        }
        catch (Exception e){
            return false;
        }

    }
}
