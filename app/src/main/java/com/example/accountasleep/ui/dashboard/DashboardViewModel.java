package com.example.accountasleep.ui.dashboard;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.accountasleep.R;

import java.util.ArrayList;
import java.util.Arrays;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public Uri[] imgPaths = {
            Uri.parse("android.resource://com.example.accountasleep/" + R.drawable.sample_img_1),
            Uri.parse("android.resource://com.example.accountasleep/" + R.drawable.sample_img_2),
            Uri.parse("android.resource://com.example.accountasleep/" + R.drawable.sample_img_3),
            Uri.parse("android.resource://com.example.accountasleep/" + R.drawable.sample_img_4),
            Uri.parse("android.resource://com.example.accountasleep/" + R.drawable.sample_img_5),
    };

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public void addImgPath(Uri imgPath){
        ArrayList<Uri> listTmp = new ArrayList<>(Arrays.asList(imgPaths));
        listTmp.add(0, imgPath);
        imgPaths = listTmp.toArray(new Uri[listTmp.size()]);
    }

    public void removeImgPath(Uri imgPath){
        ArrayList<Uri> listTmp = new ArrayList<>(Arrays.asList(imgPaths));
        listTmp.remove(imgPath);
        imgPaths = listTmp.toArray(new Uri[listTmp.size()]);
    }


    public LiveData<String> getText() {
        return mText;
    }
}