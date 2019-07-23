package com.minipoly.android.utils;

import android.os.CountDownTimer;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.minipoly.android.BR;

public class CountDown extends BaseObservable {
    private CountDownTimer timer;
    private int hours;
    private int minutes;
    private int seconds;
    private String time;

    @Bindable
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

    @Bindable
    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
        notifyPropertyChanged(BR.hours);
    }

    @Bindable
    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
        notifyPropertyChanged(BR.minutes);
    }

    @Bindable
    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
        notifyPropertyChanged(BR.seconds);
    }


    private String format(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

    public CountDown(long time) {
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long remaining = millisUntilFinished;
                int factor = 3600 * 1000;
                setHours((int) (remaining / factor));
                remaining = remaining % factor;
                factor = factor / 60;
                setMinutes((int) (remaining / factor));
                remaining = remaining % factor;
                factor = factor / 60;
                setSeconds((int) (remaining / factor));
                setTime(format(hours) + ":" + format(minutes) + ":" + format(seconds));
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }
}
