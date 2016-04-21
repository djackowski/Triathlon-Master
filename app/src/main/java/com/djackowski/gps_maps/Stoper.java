package com.djackowski.gps_maps;


import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.djackowski.R;
import com.djackowski.trenings.Listener;

public class Stoper extends Activity implements Runnable {

    TextView text;
    Handler handler = new Handler();
    Button start, stop;
    private long clock;
    private long start2;
    private long millis;
    private float speed;
    private float averageSpeed;
    private float calories;
    private float distance;
    private long minutes;
    private boolean isStartClicked = false;
    private boolean isStopClicked = false;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    private long startTime;
    private long distanceTime;
    private Listener listener;
    private TextView isDone;


    public Stoper(TextView text, Button start, Button stop, float speed, float averageSpeed, float calories, float distance, long distanceTime) {
        this.text = text;
        this.speed = speed;
        this.averageSpeed = averageSpeed;
        this.calories = calories;
        this.distance = distance;
        this.start = start;
        this.stop = stop;
        this.distanceTime = distanceTime;
        // this.isDone = isDone;
    }

    public Stoper(TextView text, Button start, Button stop, float speed, float averageSpeed, float calories, float distance) {
        this.text = text;
        this.speed = speed;
        this.averageSpeed = averageSpeed;
        this.calories = calories;
        this.distance = distance;
        this.start = start;
        this.stop = stop;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public long getDistanceTime() {
        return distanceTime;
    }

    public void setDistanceTime(long distanceTime) {
        this.distanceTime = distanceTime;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public boolean isStartClicked() {
        return isStartClicked;
    }

    public boolean isStopClicked() {
        return isStopClicked;
    }

    public boolean isDoneTreningYet(long time) {
        if (minutes >= time)
            return true;
        else
            return false;
    }


    public void calculate() {

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isStartClicked || isStopClicked) {

                    startTime = SystemClock.uptimeMillis();
                    handler.postDelayed(Stoper.this, 10);
                    // start.setText("Stop");
                    start.setBackgroundResource(R.drawable.pause);
                    isStartClicked = true;
                    isStopClicked = false;
                } else {
                    // start.setText("Resume");
                    start.setBackgroundResource(R.drawable.play);
                    isStartClicked = false;
                    timeSwapBuff += millis;
                    handler.removeCallbacks(Stoper.this);
                    isStopClicked = false;
                    // isDoneYet(distanceTime, isDone);

                }


            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float dst = GPSMap.getDistance() / 1000;
                listener.onListen(dst);
                handler.removeCallbacks(Stoper.this);
                timeSwapBuff = 0;
                text.setText("00:00:00");
                isStopClicked = true;
                start.setBackgroundResource(R.drawable.play);
                //start.setText("Start");
                speed = 0;
                averageSpeed = 0;
                calories = 0;
                distance = 0;


            }
        });

        start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //start.setBackgroundResource(R.drawable.buttonshapestartafterclick);
                        break;
                    case MotionEvent.ACTION_UP:
                        // start.setBackgroundResource(R.drawable.buttonshapestart);
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
        stop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // stop.setBackgroundResource(R.drawable.buttonshapestopafterclick);
                        break;
                    case MotionEvent.ACTION_UP:
                        //stop.setBackgroundResource(R.drawable.buttonshapestop);
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

    }

    public void isDoneYet(long distanceTime, TextView isDone) {
        if (isDoneTreningYet(distanceTime))
            isDone.setText("Trening ukonczono");
        else
            isDone.setText("Trening nie został jeszcze ukończony");

    }

    @Override
    public void run() {
        millis = SystemClock.uptimeMillis() - startTime;
        updatedTime = timeSwapBuff + millis;

        long secs = updatedTime / 1000;
        long mins = secs / 60;
        long hours = mins / 60;
        long milliseconds = updatedTime % 100;
        // millis = millis % 100;
        mins = mins % 60;
        secs = secs % 60;

        minutes = mins;

        text.setText(String.format("%02d:%02d:%02d", hours, mins, secs));
        //text.setText(String.format("%03d:%03d",millis, SystemClock.uptimeMillis()));
        handler.postDelayed(this, 0);//zapetla siebie
    }
}
