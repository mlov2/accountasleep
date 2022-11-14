package com.example.accountasleep.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
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
        Button limit_button = binding.snoozeLimitButton;
        Button snooze_ok_button = binding.okButtonSnooze;
        Button repeat_ok_button = binding.okButtonDay;
        NumberPicker snooze_duration_number_picker = binding.snoozeDurationNumberPicker;
        NumberPicker snooze_limit_number_picker = binding.snoozeLimitNumberPicker;

        //Initialize settings for snooze duration/limit number picker and retrieve input "snooze_duration_input"
        snooze_duration_number_picker.setMaxValue(50);
        snooze_duration_number_picker.setMinValue(0);
        final int[] snooze_duration_input = {0};
        snooze_duration_number_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                snooze_duration_input[0] = snooze_duration_number_picker.getValue();
            }
        });
        snooze_limit_number_picker.setMaxValue(20);
        snooze_limit_number_picker.setMinValue(0);
        final int[] snooze_limit_input = {0};
        snooze_limit_number_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                snooze_limit_input[0] = snooze_limit_number_picker.getValue();
            }
        });

        //Enable buttons to navigate between different pages
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
        //After clicking the ok button in snooze setting page, return snooze_limit_output and snooze_duration_output
        snooze_ok_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                alarm_header.setVisibility(View.VISIBLE);
                alarm_setting.setVisibility(View.VISIBLE);
                alarm_snooze.setVisibility(View.INVISIBLE);
                String snooze_duration_output = snooze_duration_input[0] + " mins";
                duration_button.setText(snooze_duration_output);
                String snooze_limit_output = snooze_limit_input[0] + " times";
                limit_button.setText(snooze_limit_output);
            }
        });
        //After clicking the ok button in repeat setting page, return repeat_output
        repeat_ok_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                alarm_header.setVisibility(View.VISIBLE);
                alarm_setting.setVisibility(View.VISIBLE);
                alarm_repeat.setVisibility(View.INVISIBLE);

                String repeat_output = "";
                if(binding.Monday.isChecked()){
                    repeat_output += "MON ";
                    repeat_button.setPadding(10,0,0,0);
                }
                if(binding.Tuesday.isChecked()){
                    repeat_output += "TUE ";
                    repeat_button.setPadding(10,0,0,0);
                }
                if(binding.Wednesday.isChecked()){
                    repeat_output += "WED ";
                    repeat_button.setPadding(10,0,0,0);
                }
                if(binding.Thursday.isChecked()){
                    repeat_output += "THU ";
                    repeat_button.setPadding(10,0,0,0);
                }
                if(binding.Friday.isChecked()){
                    repeat_output += "FRI ";
                    repeat_button.setPadding(10,0,0,0);
                }
                if(binding.Saturday.isChecked()){
                    repeat_output += "SAT ";
                    repeat_button.setPadding(10,0,0,0);
                }
                if(binding.Sunday.isChecked()){
                    repeat_output += "SUN ";
                    repeat_button.setPadding(10,0,0,0);
                }
                repeat_button.setText(repeat_output);
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