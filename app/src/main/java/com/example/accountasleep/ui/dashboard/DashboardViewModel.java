package com.example.accountasleep.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.accountasleep.R;

import java.util.ArrayList;
import java.util.Arrays;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public Integer[] imgPaths = {
            R.drawable.sample_img_1,
            R.drawable.sample_img_2,
            R.drawable.sample_img_3,
            R.drawable.sample_img_4,
            R.drawable.sample_img_5,
    };

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public void addImgPath(Integer imgPath){
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(imgPaths));
        list2.add(imgPath);
        imgPaths = list2.toArray(new Integer[list2.size()]);
    }


    public LiveData<String> getText() {
        return mText;
    }
}