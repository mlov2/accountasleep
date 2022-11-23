package com.example.accountasleep.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private String contact1Number;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setContact1Number(String contact1Number) {
        this.contact1Number = contact1Number;
    }

    public String getContact1Number() {
        return contact1Number;
    }
}