package com.example.accountasleep.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private int snooze_limit_update;
    private boolean snooze_flag = false;

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

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}