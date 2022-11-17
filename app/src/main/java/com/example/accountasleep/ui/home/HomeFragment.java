package com.example.accountasleep.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.accountasleep.R;
import com.example.accountasleep.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    // Array of strings...
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // references:
        // - to get listview to work: https://www.youtube.com/watch?v=7MRnL_slGrI
        // - to create a custom adapter: TODO (refer to one of the links below)
        ListView lv = (ListView) root.findViewById(R.id.alarm_list_view);
        ArrayAdapter adapter=new ArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mobileArray);
//        CustomAdapter adapter = new CustomAdapter(mobileArray, mobileArray, mobileArray, mobileArray, mobileArray);
        lv.setAdapter(adapter);



        // reference: https://www.tutorialspoint.com/android/android_list_view.htm
        // reference: https://www.tutorialspoint.com/how-to-use-findviewbyid-in-fragment
//        ViewGroup alarm_lst = (ViewGroup) inflater.inflate(R.layout.fragment_home, null);
////        LinearLayout alarm_list = binding.alarmListLayout;
//        ArrayAdapter adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.alarm_list, mobileArray);
//        ListView listView = (ListView) alarm_lst.findViewById(R.id.alarm_list_list);
//        listView.setAdapter(adapter);

//        MyListAdapter adapter=new MyListAdapter(this, mobileArray, mobileArray, mobileArray, mobileArray, mobileArray);
//        list=(ListView)findViewById(R.id.list);
//        list.setAdapter(adapter);

        // Inflate the layout for this fragment - ref: https://stackoverflow.com/questions/69164648/adding-a-listview-in-a-fragment
//        View contentView = inflater.inflate(R.layout.fragment_home, container, false);
//        ListView listView = contentView.findViewById(R.id.alarm_list_list);
//        CustomAdapter listAdapter = new CustomAdapter(mobileArray, mobileArray, mobileArray, mobileArray, mobileArray);
//        listView.setAdapter(listAdapter);

        // alarm list page variables
        LinearLayout alarm_page_header = binding.alarmPageHeaderLayout;
        Button add_alarm_button = binding.addAlarmButton;
        Button edit_alarm_button = binding.editAlarmButton;
        LinearLayout alarm_list = binding.alarmListLayout;
        LinearLayout alarm_setting_page = binding.alarmSettingPage;
        // alarm setting page variables
        LinearLayout alarm_header = binding.alarmHeaderLayout;
        LinearLayout alarm_setting = binding.alarmSettingLayout;
        LinearLayout alarm_repeat = binding.repeatSetterLayout;
        LinearLayout alarm_snooze = binding.snoozeSetterLayout;
        Button cancel_button = binding.cancelButton;
        Button done_button = binding.doneButton;
        Button repeat_button = binding.repeatButton;
        Button duration_button = binding.durationButton;
        Button limit_button = binding.snoozeLimitButton;
        Button snooze_ok_button = binding.okButtonSnooze;
        Button repeat_ok_button = binding.okButtonDay;
        Button delete_button = binding.deleteButton;
        Switch snooze_switch = binding.snoozeSwitch;
        CardView snooze_card = binding.snoozeCard;
        NumberPicker snooze_duration_number_picker = binding.snoozeDurationNumberPicker;
        NumberPicker snooze_limit_number_picker = binding.snoozeLimitNumberPicker;

        //Edit alarm
        edit_alarm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarm_setting_page.setVisibility(View.VISIBLE);
                alarm_page_header.setVisibility(View.GONE);
                alarm_list.setVisibility(View.GONE);

                // TODO: add in extra functionality
                // - change header name in alarm setting
                // - set fields in the alarm setting page to the
                // current settings of the alarm we are editing
            }
        });

        //Add alarm
        add_alarm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarm_setting_page.setVisibility(View.VISIBLE);
                alarm_page_header.setVisibility(View.GONE);
                alarm_list.setVisibility(View.GONE);

                // TODO: add the new alarm and its settings to the alarm list view
            }
        });

        //Cancel alarm setting
        cancel_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                alarm_setting_page.setVisibility(View.GONE);
                alarm_page_header.setVisibility(View.VISIBLE);
                alarm_list.setVisibility(View.VISIBLE);
            }
        });

        //Save alarm setting
        done_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                alarm_setting_page.setVisibility(View.GONE);
                alarm_page_header.setVisibility(View.VISIBLE);
                alarm_list.setVisibility(View.VISIBLE);
            }
        });

        //Delete alarm TODO: Delete memory and go back to alarm page
        delete_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                alarm_header.setVisibility(View.INVISIBLE);
                alarm_setting.setVisibility(View.INVISIBLE);
            }
        });

        //Show snooze setting if snooze switch is on
        snooze_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    snooze_card.setVisibility(buttonView.VISIBLE);
                }else{
                    snooze_card.setVisibility(buttonView.INVISIBLE);
                }

            }
        });
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
                if(repeat_output.length()==0){
                    repeat_button.setText("NEVER");
                }else{
                    repeat_button.setText(repeat_output);
                }
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