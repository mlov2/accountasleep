package com.example.accountasleep.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.accountasleep.R;
import com.example.accountasleep.RingActivity;
import com.example.accountasleep.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    // Array of strings...
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    private FragmentHomeBinding binding;

    private AlarmAdapter adapter;
    //private ArrayList<Alarm> alarmlist;
    private ListView lv;

    private String alarm_setting_header_value = "";
    private int snooze_duration_input = -1;
    private int snooze_limit_input = -1;

    // variables for saving an alarm
    //ArrayList<ArrayList<Object>> alarms_raw = new ArrayList<>();
    private String save_alarm_label = "";
    private String save_alarm_time = "";
    private String save_alarm_time_of_day = "AM";
    private Boolean[] save_repeat = {false, false, false, false, false, false, false};
    private boolean save_send_a_message = true;
    private boolean save_snooze = false;
    private int save_snooze_duration = -1;
    private int save_snooze_limit = -1;
    private int edit_position;


    private void updateListView() {
        // reference to refresh list view: https://stackoverflow.com/questions/37460133/how-to-refresh-listview-in-a-custom-adapter
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lv.invalidateViews();
        lv.refreshDrawableState();
    }

    private void resetToDefaultSettings(FragmentHomeBinding binding) {
        EditText alarm_label = binding.alarmLabel;
        Button repeat_button = binding.repeatButton;
        Switch send_msg_switch = binding.sendMessageSwitch;
        Switch snooze_switch = binding.snoozeSwitch;

        // reset label
        alarm_label.setText("Alarm");
        // reset repeat settings
        binding.Sunday.setChecked(false);
        binding.Monday.setChecked(false);
        binding.Tuesday.setChecked(false);
        binding.Wednesday.setChecked(false);
        binding.Thursday.setChecked(false);
        binding.Friday.setChecked(false);
        binding.Saturday.setChecked(false);

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
        // reset send a message settings
        send_msg_switch.setChecked(true);
        snooze_switch.setChecked(false);
        // reset snooze settings
        snooze_duration_input = -1;
        snooze_limit_input = -1;
    }

    private void populateHardcodedAlarms(String alarm_label, String alarm_time, String alarm_time_of_day,
                                         Boolean[] alarm_repeat, boolean send_a_msg, boolean snooze,
                                         int snooze_duration, int snooze_limit, HomeViewModel homeViewModel) {
        ArrayList<Object> alarm_raw = new ArrayList<>();
        alarm_raw.add(alarm_label);
        alarm_raw.add(alarm_time);
        alarm_raw.add(alarm_time_of_day);
        alarm_raw.add(alarm_repeat);
        alarm_raw.add(send_a_msg);
        alarm_raw.add(snooze);
        alarm_raw.add(snooze_duration);
        alarm_raw.add(snooze_limit);
        //alarms_raw.add(alarm_raw);
        ArrayList<ArrayList<Object>> curr_alarms_raw = homeViewModel.getAlarms_raw();
        curr_alarms_raw.add(alarm_raw);
        homeViewModel.setAlarms_raw(curr_alarms_raw);
    }

    private void editAlarm(FragmentHomeBinding binding, HomeViewModel homeViewModel) {
        TextView alarm_setting_header_label = binding.alarmSettingHeaderLabel;
        EditText alarm_label = binding.alarmLabel;
        TimePicker alarm_time = binding.fragmentCreatealarmTimePicker;
        Button repeat_button = binding.repeatButton;
        Switch send_msg_switch = binding.sendMessageSwitch;
        Switch snooze_switch = binding.snoozeSwitch;
        Button duration_button = binding.durationButton;
        Button limit_button = binding.snoozeLimitButton;
        NumberPicker snooze_duration_number_picker = binding.snoozeDurationNumberPicker;
        NumberPicker snooze_limit_number_picker = binding.snoozeLimitNumberPicker;

        // ensure alarm setting page header is set correctly
        alarm_setting_header_value = "Edit Alarm";
        alarm_setting_header_label.setText(alarm_setting_header_value);

        // get all the settings for the alarm that was clicked
        //ArrayList<Object> curr_alarm = alarms_raw.get(edit_position);
        ArrayList<Object> curr_alarm = homeViewModel.getAlarms_raw().get(edit_position);
        String alarm_label_setting = (String) curr_alarm.get(0);
        String alarm_time_setting = (String) curr_alarm.get(1);
        String alarm_time_of_day_setting = (String) curr_alarm.get(2);
        Boolean[] alarm_repeat_setting = (Boolean[]) curr_alarm.get(3);
        boolean send_msg_setting = (boolean) curr_alarm.get(4);
        boolean snooze_setting = (boolean) curr_alarm.get(5);
        int snooze_duration_setting = (int) curr_alarm.get(6);
        int snooze_limit_setting = (int) curr_alarm.get(7);

        // display the existing setting in the alarm setting page (backend and frontend)
        alarm_label.setText(alarm_label_setting);

        // set time setting
        int hour = Integer.valueOf(alarm_time_setting.split(":")[0]);
        int minute = Integer.valueOf(alarm_time_setting.split(":")[1]);
        if (alarm_time_of_day_setting == "PM") {
            hour = hour + 12;
        } else {
            if (hour == 12) {
                hour = 0;
            }
        }
        alarm_time.setCurrentHour(hour);
        alarm_time.setCurrentMinute(minute);

        // set repeat setting
        binding.Sunday.setChecked(alarm_repeat_setting[0]);
        binding.Monday.setChecked(alarm_repeat_setting[1]);
        binding.Tuesday.setChecked(alarm_repeat_setting[2]);
        binding.Wednesday.setChecked(alarm_repeat_setting[3]);
        binding.Thursday.setChecked(alarm_repeat_setting[4]);
        binding.Friday.setChecked(alarm_repeat_setting[5]);
        binding.Saturday.setChecked(alarm_repeat_setting[6]);

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

        // set custom settings
        send_msg_switch.setChecked(send_msg_setting);
        snooze_switch.setChecked(snooze_setting);
        snooze_duration_input = snooze_duration_setting;
        snooze_limit_input = snooze_limit_setting;
        snooze_duration_number_picker.setValue(snooze_duration_input);
        snooze_limit_number_picker.setValue(snooze_limit_input);

        String snooze_duration_output = snooze_duration_input + " mins";
        duration_button.setText(snooze_duration_output);
        String snooze_limit_output = snooze_limit_input + " times";
        limit_button.setText(snooze_limit_output);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // alarm list page variables
        LinearLayout alarm_page_header = binding.alarmPageHeaderLayout;
        FloatingActionButton add_alarm_button = binding.addAlarmButton;
        LinearLayout alarm_list = binding.alarmListLayout;

        // alarm setting page variables
        LinearLayout alarm_setting_page = binding.alarmSettingPage;
        Button cancel_button = binding.cancelButton;
        TextView alarm_setting_header_label = binding.alarmSettingHeaderLabel;
        Button done_button = binding.doneButton;
        LinearLayout alarm_setting = binding.alarmSettingLayout;
        EditText alarm_label = binding.alarmLabel;
        TimePicker alarm_time = binding.fragmentCreatealarmTimePicker;
        Button repeat_button = binding.repeatButton;
        Switch send_msg_switch = binding.sendMessageSwitch;
        Switch snooze_switch = binding.snoozeSwitch;
        CardView snooze_card = binding.snoozeCard;
        Button duration_button = binding.durationButton;
        Button limit_button = binding.snoozeLimitButton;
        Button delete_button = binding.deleteButton;
        LinearLayout alarm_repeat = binding.repeatSetterLayout;
        Button repeat_ok_button = binding.okButtonDay;
        LinearLayout alarm_snooze = binding.snoozeSetterLayout;
        NumberPicker snooze_duration_number_picker = binding.snoozeDurationNumberPicker;
        NumberPicker snooze_limit_number_picker = binding.snoozeLimitNumberPicker;
        Button snooze_ok_button = binding.okButtonSnooze;
        //Test
        Button edit_button = binding.editAlarmButton;


        // references:
        // - to get listview to work: https://www.youtube.com/watch?v=7MRnL_slGrI
        // - to create a custom adapter: https://www.geeksforgeeks.org/custom-arrayadapter-with-listview-in-android/
        lv = (ListView) root.findViewById(R.id.alarm_list_view);

        //alarmlist = new ArrayList<>();
        // hardcoded alarms
        //populateHardcodedAlarms("CS 465 lecture", "8:00", "AM", new Boolean[]{false, true, false, true, false, false, false}, true, true, 10, 3);
        //populateHardcodedAlarms("CS 465 studio", "8:00", "AM", new Boolean[]{false, false, false, false, false, true, false}, true, true, 8, 5);
        //populateHardcodedAlarms("Go to bed", "12:00", "AM", new Boolean[]{true, true, true, true, true, true, true}, false, false, -1, -1);
        //alarmlist.add(new Alarm("8:00", "AM", "MW", "CS 465 lecture", true, 10, 3, true));
        //alarmlist.add(new Alarm("8:00", "AM", "F", "CS 465 studio", true, 8, 5, false));
        //alarmlist.add(new Alarm("12:00", "AM", "SMTWThFS", "Go to bed", false, -1, -1, true));

        ArrayList<Alarm> curr_alarmlist = homeViewModel.getAlarmlist();
        if (curr_alarmlist.size() == 0) {
            // hardcoded alarms
            populateHardcodedAlarms("CS 465 lecture", "8:00", "AM", new Boolean[]{false, true, false, true, false, false, false}, true, true, 10, 3, homeViewModel);
            populateHardcodedAlarms("CS 465 studio", "8:00", "AM", new Boolean[]{false, false, false, false, false, true, false}, true, true, 8, 5, homeViewModel);
            populateHardcodedAlarms("Go to bed", "12:00", "AM", new Boolean[]{true, true, true, true, true, true, true}, false, false, -1, -1, homeViewModel);
            curr_alarmlist.add(new Alarm("8:00", "AM", "M W", "CS 465 lecture", true, 10, 3, true));
            curr_alarmlist.add(new Alarm("8:00", "AM", "Every Friday", "CS 465 studio", true, 8, 5, false));
            curr_alarmlist.add(new Alarm("12:00", "AM", "Every day", "Go to bed", false, -1, -1, true));
        }

        //        ArrayAdapter adapter=new ArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mobileArray);
//        CustomAdapter adapter = new CustomAdapter(mobileArray, mobileArray, mobileArray, mobileArray, mobileArray);
        //adapter = new AlarmAdapter(this.getActivity(), alarmlist);
        adapter = new AlarmAdapter(this.getActivity(), curr_alarmlist);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edit_position = position;

                // change the page from the alarm list to alarm setting
                // hide alarm list page
                alarm_page_header.setVisibility(View.GONE);
                alarm_list.setVisibility(View.GONE);

                // show alarm setting page
                alarm_setting_page.setVisibility(View.VISIBLE);

                editAlarm(binding, homeViewModel);
            }
        });

        // references for adapter:
        // - https://www.geeksforgeeks.org/custom-arrayadapter-with-listview-in-android/
        // - https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd



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


//        //Edit alarm
//        edit_alarm_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // hide alarm list page
//                alarm_page_header.setVisibility(View.GONE);
//                alarm_list.setVisibility(View.GONE);
//
//                // show alarm setting page
//                alarm_setting_page.setVisibility(View.VISIBLE);
//
//                // ensure alarm setting page header is set correctly
//                alarm_setting_header_value = "Edit Alarm";
//                alarm_setting_header_label.setText(alarm_setting_header_value);
//
//                // TODO: set fields in the alarm setting page to the
//                //  current settings of the alarm we are editing
//            }
//        });
        //Test, borrow edit alarm button TODO:Think of other solution
        edit_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DateFormat df = new SimpleDateFormat("h:mm a");
                String date = df.format(Calendar.getInstance().getTime());
                HomeViewModel myObj = new HomeViewModel();
//                if (myObj.getSnooze_flag()==true){
//                    int snooze_limit_update = myObj.getSnooze_limit_update();
//                    System.out.println("!!!!!!Here!!!!!");
//                    System.out.println(snooze_limit_update);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//                            // Actions to do after snooze_duration_int
//                            setContentView(R.layout.activity_ring);
//                            r.play();
//                        }
//                    }, snooze_duration_int*1000);
//                }else{
                    ArrayList<Alarm> alarmlist = homeViewModel.getAlarmlist();
                    for(int i = 0; i < alarmlist.size(); i++){

                        String curr = "";
                        curr += alarmlist.get(i).getAlarmTime();
                        curr += " ";
                        curr += alarmlist.get(i).getAlarmAmPm();
                        if (date.equals(curr)){
                            Intent intent1 = new Intent(getActivity(), RingActivity.class);
                            intent1.putExtra("snooze_limit",String.valueOf(alarmlist.get(i).getAlarmSnoozeFrequency()));
                            intent1.putExtra("snooze_duration",String.valueOf(alarmlist.get(i).getAlarmSnoozeInterval()));
                            startActivity(intent1);
                        }
                    }
                }
//            }
        });
        //Add alarm
        add_alarm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hide alarm list page
                alarm_page_header.setVisibility(View.GONE);
                alarm_list.setVisibility(View.GONE);

                // show alarm setting page
                alarm_setting_page.setVisibility(View.VISIBLE);

                // ensure alarm setting page header is set correctly
                alarm_setting_header_value = "Add Alarm";
                alarm_setting_header_label.setText(alarm_setting_header_value);
            }
        });

        //Cancel alarm setting
        cancel_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // hide alarm setting page
                alarm_setting_page.setVisibility(View.GONE);

                // show alarm list page
                alarm_page_header.setVisibility(View.VISIBLE);
                alarm_list.setVisibility(View.VISIBLE);

                resetToDefaultSettings(binding);
            }
        });

        //Save alarm setting
        done_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // hide alarm setting page
                alarm_setting_page.setVisibility(View.GONE);

                // show alarm list page
                alarm_page_header.setVisibility(View.VISIBLE);
                alarm_list.setVisibility(View.VISIBLE);

                // save the new alarm and its settings to the alarm list view
                save_alarm_label = alarm_label.getText().toString();
                // alarm time
                int hour = alarm_time.getCurrentHour();
                if (hour == 0) {
                    hour = 12;
                    save_alarm_time_of_day = "AM";
                } else if (hour > 12) {
                    hour = hour - 12;
                    save_alarm_time_of_day = "PM";
                } else {
                    save_alarm_time_of_day = "AM";
                }
                int minute = alarm_time.getCurrentMinute();
                save_alarm_time = "";
                save_alarm_time += Integer.toString(hour);
                save_alarm_time += ":";
                if (minute < 10) {
                    save_alarm_time += "0" + Integer.toString(minute);
                } else {
                    save_alarm_time += Integer.toString(minute);
                }
                // repeat
                save_repeat[0] = binding.Sunday.isChecked();
                save_repeat[1] = binding.Monday.isChecked();
                save_repeat[2] = binding.Tuesday.isChecked();
                save_repeat[3] = binding.Wednesday.isChecked();
                save_repeat[4] = binding.Thursday.isChecked();
                save_repeat[5] = binding.Friday.isChecked();
                save_repeat[6] = binding.Saturday.isChecked();
                // custom
                save_send_a_message = send_msg_switch.isChecked();
                save_snooze = snooze_switch.isChecked();
                save_snooze_duration = snooze_duration_input;
                save_snooze_limit = snooze_limit_input;

                // save alarm settings
                ArrayList<Object> alarm_raw = new ArrayList<>();
                alarm_raw.add(save_alarm_label);
                alarm_raw.add(save_alarm_time);
                alarm_raw.add(save_alarm_time_of_day);
                alarm_raw.add(save_repeat);
                alarm_raw.add(save_send_a_message);
                alarm_raw.add(save_snooze);
                alarm_raw.add(save_snooze_duration);
                alarm_raw.add(save_snooze_limit);

                if (alarm_setting_header_value == "Add Alarm") {
                    // add new alarm
                    //alarms_raw.add(alarm_raw);
                    ArrayList<ArrayList<Object>> curr_alarms_raw = homeViewModel.getAlarms_raw();
                    curr_alarms_raw.add(alarm_raw);
                    homeViewModel.setAlarms_raw(curr_alarms_raw);
                } else {
                    //alarms_raw.set(edit_position, alarm_raw);
                    ArrayList<ArrayList<Object>> curr_alarms_raw = homeViewModel.getAlarms_raw();
                    curr_alarms_raw.set(edit_position, alarm_raw);
                    homeViewModel.setAlarms_raw(curr_alarms_raw);
                }

                // update alarm list page to include the new/edited alarm
                ArrayList<Object> current_alarm = alarm_raw;
                String alarm_label = (String) current_alarm.get(0);
                String alarm_time = (String) current_alarm.get(1);
                String alarm_time_of_day = (String) current_alarm.get(2);
                Boolean[] alarm_repeat = (Boolean[]) current_alarm.get(3);
                String alarm_repeat_str = "";
                if (alarm_repeat[0] && alarm_repeat[1] && alarm_repeat[2] && alarm_repeat[3]
                        && alarm_repeat[4] && alarm_repeat[5] && alarm_repeat[6]) {
                    alarm_repeat_str = "Every day";
                } else if (!alarm_repeat[0] && alarm_repeat[1] && alarm_repeat[2] && alarm_repeat[3] && alarm_repeat[4] && alarm_repeat[5] && !alarm_repeat[6]) {
                    alarm_repeat_str = "Every weekday";
                } else if (alarm_repeat[0] && !alarm_repeat[1] && !alarm_repeat[2] && !alarm_repeat[3] && !alarm_repeat[4] && !alarm_repeat[5] && alarm_repeat[6]) {
                    alarm_repeat_str = "Every weekend";
                } else if (alarm_repeat[0] && !alarm_repeat[1] && !alarm_repeat[2] && !alarm_repeat[3] && !alarm_repeat[4] && !alarm_repeat[5] && !alarm_repeat[6]) {
                    alarm_repeat_str = "Every Sunday";
                } else if (!alarm_repeat[0] && alarm_repeat[1] && !alarm_repeat[2] && !alarm_repeat[3] && !alarm_repeat[4] && !alarm_repeat[5] && !alarm_repeat[6]) {
                    alarm_repeat_str = "Every Monday";
                } else if (!alarm_repeat[0] && !alarm_repeat[1] && alarm_repeat[2] && !alarm_repeat[3] && !alarm_repeat[4] && !alarm_repeat[5] && !alarm_repeat[6]) {
                    alarm_repeat_str = "Every Tuesday";
                } else if (!alarm_repeat[0] && !alarm_repeat[1] && !alarm_repeat[2] && alarm_repeat[3] && !alarm_repeat[4] && !alarm_repeat[5] && !alarm_repeat[6]) {
                    alarm_repeat_str = "Every Wednesday";
                } else if (!alarm_repeat[0] && !alarm_repeat[1] && !alarm_repeat[2] && !alarm_repeat[3] && alarm_repeat[4] && !alarm_repeat[5] && !alarm_repeat[6]) {
                    alarm_repeat_str = "Every Thursday";
                } else if (!alarm_repeat[0] && !alarm_repeat[1] && !alarm_repeat[2] && !alarm_repeat[3] && !alarm_repeat[4] && alarm_repeat[5] && !alarm_repeat[6]) {
                    alarm_repeat_str = "Every Friday";
                } else if (!alarm_repeat[0] && !alarm_repeat[1] && !alarm_repeat[2] && !alarm_repeat[3] && !alarm_repeat[4] && !alarm_repeat[5] && alarm_repeat[6]) {
                    alarm_repeat_str = "Every Saturday";
                } else {
                    if (alarm_repeat[0]) alarm_repeat_str += "S ";
                    if (alarm_repeat[1]) alarm_repeat_str += "M ";
                    if (alarm_repeat[2]) alarm_repeat_str += "T ";
                    if (alarm_repeat[3]) alarm_repeat_str += "W ";
                    if (alarm_repeat[4]) alarm_repeat_str += "Th ";
                    if (alarm_repeat[5]) alarm_repeat_str += "F ";
                    if (alarm_repeat[6]) alarm_repeat_str += "S";
                }
                boolean send_msg = (boolean) current_alarm.get(4);
                int snooze_duration = (int) current_alarm.get(6);
                int snooze_limit = (int) current_alarm.get(7);

                if (alarm_setting_header_value == "Add Alarm") {
                    // add new alarm
                    //alarmlist.add(new Alarm(alarm_time, alarm_time_of_day, alarm_repeat_str, alarm_label, send_msg, snooze_duration, snooze_limit, true));
                    ArrayList<Alarm> curr_alarmlist = homeViewModel.getAlarmlist();
                    curr_alarmlist.add(new Alarm(alarm_time, alarm_time_of_day, alarm_repeat_str, alarm_label, send_msg, snooze_duration, snooze_limit, true));
                    homeViewModel.setAlarmlist(curr_alarmlist);
                } else {
                    // replace old alarm with edited alarm settings
                    //alarmlist.set(edit_position, new Alarm(alarm_time, alarm_time_of_day, alarm_repeat_str, alarm_label, send_msg, snooze_duration, snooze_limit, true));
                    ArrayList<Alarm> curr_alarmlist = homeViewModel.getAlarmlist();
                    curr_alarmlist.set(edit_position, new Alarm(alarm_time, alarm_time_of_day, alarm_repeat_str, alarm_label, send_msg, snooze_duration, snooze_limit, true));
                    homeViewModel.setAlarmlist(curr_alarmlist);
                }
                updateListView();

                if (alarm_setting_header_value == "Add Alarm") {
                    Toast.makeText(getContext(), "Alarm Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Alarm Edited", Toast.LENGTH_SHORT).show();
                }
                resetToDefaultSettings(binding);
            }
        });

        //Delete alarm
        delete_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // hide alarm snooze setting page
                alarm_setting_page.setVisibility(View.GONE);

                // display alarm list page
                alarm_page_header.setVisibility(View.VISIBLE);
                alarm_list.setVisibility(View.VISIBLE);

                // remove alarm from alarm list if editing an alarm
                if (alarm_setting_header_value == "Edit Alarm") {
                    //alarmlist.remove(edit_position);
                    ArrayList<Alarm> curr_alarmlist = homeViewModel.getAlarmlist();
                    curr_alarmlist.remove(edit_position);
                    homeViewModel.setAlarmlist(curr_alarmlist);

                    //alarms_raw.remove(edit_position);
                    ArrayList<ArrayList<Object>> curr_alarms_raw = homeViewModel.getAlarms_raw();
                    curr_alarms_raw.remove(edit_position);
                    homeViewModel.setAlarms_raw(curr_alarms_raw);

                    updateListView();
                }

                // reset values to default values for an alarm
                resetToDefaultSettings(binding);
            }
        });

        //Show snooze setting if snooze switch is on
        snooze_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    snooze_card.setVisibility(buttonView.VISIBLE);
                    snooze_duration_input = 5;
                    snooze_limit_input = 1;

                    // display in alarm setting page
                    String snooze_duration_output = snooze_duration_input + " mins";
                    duration_button.setText(snooze_duration_output);
                    String snooze_limit_output = snooze_limit_input + " times";
                    limit_button.setText(snooze_limit_output);
                } else {
                    snooze_card.setVisibility(buttonView.INVISIBLE);
                    snooze_duration_input = -1;
                    snooze_limit_input = -1;

                    // display in alarm setting page
                    String snooze_duration_output = snooze_duration_input + " mins";
                    duration_button.setText(snooze_duration_output);
                    String snooze_limit_output = snooze_limit_input + " times";
                    limit_button.setText(snooze_limit_output);
                }
            }
        });

        //Initialize settings for snooze duration/limit number picker and retrieve input "snooze_duration_input"
        snooze_duration_number_picker.setMaxValue(10);
        snooze_duration_number_picker.setMinValue(5);
        snooze_duration_number_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                snooze_duration_input = snooze_duration_number_picker.getValue();
            }
        });
        snooze_limit_number_picker.setMaxValue(3);
        snooze_limit_number_picker.setMinValue(1);
        snooze_limit_number_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                snooze_limit_input = snooze_limit_number_picker.getValue();
            }
        });

        //Enable buttons to navigate between different pages
        repeat_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // hide alarm setting page
                alarm_setting.setVisibility(View.GONE);

                // change alarm setting header
                cancel_button.setVisibility(View.GONE);
                done_button.setVisibility(View.GONE);
                alarm_setting_header_label.setText("Repeat");

                // show alarm repeat setting page
                alarm_repeat.setVisibility(View.VISIBLE);
            }
        });

        //After clicking the ok button in repeat setting page, return repeat_output
        repeat_ok_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // hide alarm repeat setting page
                alarm_repeat.setVisibility(View.GONE);

                // change alarm setting header
                cancel_button.setVisibility(View.VISIBLE);
                done_button.setVisibility(View.VISIBLE);
                alarm_setting_header_label.setText(alarm_setting_header_value);

                // show alarm setting page
                alarm_setting.setVisibility(View.VISIBLE);

                // display in alarm setting page
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

        duration_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // hide alarm setting page
                alarm_setting.setVisibility(View.GONE);

                // change alarm setting header
                cancel_button.setVisibility(View.GONE);
                done_button.setVisibility(View.GONE);
                alarm_setting_header_label.setText("Snooze");

                // show alarm snooze setting page
                alarm_snooze.setVisibility(View.VISIBLE);

                // set the values for the pickers
                snooze_duration_number_picker.setValue(snooze_duration_input);
                snooze_limit_number_picker.setValue(snooze_limit_input);
            }
        });

        //After clicking the ok button in snooze setting page, return snooze_limit_output and snooze_duration_output
        snooze_ok_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // hide alarm snooze setting page
                alarm_snooze.setVisibility(View.GONE);

                // change alarm setting header
                cancel_button.setVisibility(View.VISIBLE);
                done_button.setVisibility(View.VISIBLE);
                alarm_setting_header_label.setText(alarm_setting_header_value);

                // show alarm setting page
                alarm_setting.setVisibility(View.VISIBLE);

                // display in alarm setting page
                String snooze_duration_output = snooze_duration_input + " mins";
                duration_button.setText(snooze_duration_output);
                String snooze_limit_output = snooze_limit_input + " times";
                limit_button.setText(snooze_limit_output);
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