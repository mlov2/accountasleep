package com.example.accountasleep.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.accountasleep.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayout alarm_header = binding.alarmHeaderLayout;
        LinearLayout alarm_setting = binding.alarmSettingLayout;
        LinearLayout alarm_repeat = binding.repeatSetterLayout;
        LinearLayout alarm_snooze = binding.snoozeSetterLayout;
        Button repeat_button = binding.repeatButton;
        Button duration_button = binding.durationButton;
        repeat_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                alarm_header.setVisibility(View.INVISIBLE);
                alarm_setting.setVisibility(View.INVISIBLE);
                alarm_repeat.setVisibility(View.VISIBLE);
            }
        });
        duration_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                alarm_header.setVisibility(View.INVISIBLE);
                alarm_setting.setVisibility(View.INVISIBLE);
                alarm_snooze.setVisibility(View.VISIBLE);
            }
        });

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}