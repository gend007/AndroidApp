package com.example.gend.myslideshow;

import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    ImageSwitcher mImageSwitcher;
    int[] mImageResources = {R.drawable.hoto_1, R.drawable.hoto_2,
                                R.drawable.hoto_3, R.drawable.hoto_4,
                                R.drawable.hoto_5, R.drawable.hoto_6,
                                R.drawable.hotp_7, R.drawable.hoto_8,
                                R.drawable.hotp_9, R.drawable.hoto_10};
    int mPpsition = 0;
    boolean mIsSlideshow = false;
    MediaPlayer mMediaPlayer;

    public class MainTimerTask extends TimerTask {

        @Override
        public void run() {
            if (mIsSlideshow){
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
        mTimer.schedule(mTimerTask,0,5000);
        mMediaPlayer = MediaPlayer.create(this, R.raw.getdown);
        mMediaPlayer.setLooping(true);
    }

    public void onAnimationButtonTapped(View view){
        //ViewPropertyAnimator animator = view.animate();
        //animator.setDuration(3000).x(200.0f).y(200.0f);

        float y =view.getY() + 100;
        view.animate().setDuration(1000).setInterpolator(new BounceInterpolator()).y(y);
    }

    private void movePosition(int move){
        mPpsition = mPpsition + move;
        if (mPpsition >= mImageResources.length) {
            mPpsition = 0;
        }
        else  if (mPpsition < 0){
            mPpsition = mImageResources.length - 1;
        }
        mImageSwitcher.setImageResource((mImageResources[mPpsition]));
    }

    public void onPrevButtonTapped(View view){
        mImageSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mImageSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        movePosition(-1);
        findViewById(R.id.imageView).animate().setDuration(1000).alpha(0.0f);
    }

    public void onNextButtonTapped(View view){
        mImageSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
        mImageSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);
        movePosition(1);
        findViewById(R.id.imageView).animate().setDuration(1000).alpha(0.0f);
    }

    public void onSlideshowButtonTapped(View view){
        mIsSlideshow = !mIsSlideshow;

        if (mIsSlideshow) {
            mMediaPlayer.start();
        }
        else {
            mMediaPlayer.pause();
            mMediaPlayer.seekTo(0);
        }
    }
}
