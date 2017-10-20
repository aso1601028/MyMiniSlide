package com.example.wakasugiakira.myminislide;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    ImageSwitcher mImageSwitcher;
    int[] mImageResources = {R.drawable.slide00,R.drawable.slide01,R.drawable.slide02,R.drawable.slide03};
    int mPosition = 0;

    boolean mIsSlideshow = false;
    MediaPlayer mMediaPlayer;

    public class MainTimerTask extends TimerTask {
        @Override
        public void run() {
            if(mIsSlideshow) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        movePosition(1);
                    }
                });
            }
        }
    }

    Timer mTimer = new Timer();
    TimerTask mTimerTask = new MainTimerTask();
    Handler mHandler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                return imageView;
            }
        });
        mImageSwitcher.setImageResource(mImageResources[0]);
        mTimer .schedule(mTimerTask, 0, 3000);
        mMediaPlayer = MediaPlayer.create(this, R.raw.bgm);
        mMediaPlayer.setLooping(true);

    }

    private void movePosition(int move) {

        mPosition = mPosition + move;
        if (mPosition >= mImageResources.length) {
            mPosition = 0;
        } else if (mPosition < 0) {
            mPosition = mImageResources.length - 1;
        }
        mImageSwitcher.setImageResource(mImageResources[mPosition]);
    }

    public void onPrevButtonTapped(View view) {
        mImageSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mImageSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        movePosition(-1);
    }

    public void onNextButtonTapped(View view) {
        mImageSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
        mImageSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);
        movePosition(1);
    }
    public void onSlideshowButtonTapped(View view) {
        mImageSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mImageSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        mIsSlideshow = !mIsSlideshow;

        if(mIsSlideshow) {
            mMediaPlayer.start();
        }else {
            mMediaPlayer.pause();
            mMediaPlayer.seekTo(0);
        }
    }
    public void onStopButtonTapped(View view) {
        mIsSlideshow = !mIsSlideshow;

        if(!mIsSlideshow) {
            mMediaPlayer.pause();
            mMediaPlayer.seekTo(0);
        }
    }







}
