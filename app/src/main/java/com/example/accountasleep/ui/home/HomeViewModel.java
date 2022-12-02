package com.example.accountasleep.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private int snooze_limit_update;
    private boolean snooze_flag = false;

    private ArrayList<ArrayList<Object>> alarms_raw = new ArrayList<>();
    private ArrayList<Alarm> alarmlist = new ArrayList<>();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public boolean getSnooze_flag() {
        return snooze_flag;
    }

    public void setSnooze_flag(boolean snooze_flag) {
        this.snooze_flag = snooze_flag;
    }

    public int getSnooze_limit_update() {
        return snooze_limit_update;
    }

    public void setSnooze_limit_update(int snooze_limit_update) {
        this.snooze_limit_update = snooze_limit_update;
    }

    public ArrayList<ArrayList<Object>> getAlarms_raw() {
        return alarms_raw;
    }

    public void setAlarms_raw(ArrayList<ArrayList<Object>> alarms_raw) {
        this.alarms_raw = alarms_raw;
    }

    public ArrayList<Alarm> getAlarmlist(){
        return alarmlist;
    }

    public void setAlarmlist(ArrayList<Alarm> alarmlist) {
        this.alarmlist = alarmlist;
    }
}