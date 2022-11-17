package com.example.accountasleep.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.accountasleep.R;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends ArrayAdapter<Alarm> {
//    private Context alarmContext;
//    private List<Alarm> alarmList = new ArrayList<>();

    public AlarmAdapter(@NonNull Context context, ArrayList<Alarm> list) {
        super(context, 0 , list);
//        alarmContext = context;
//        alarmList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.alarm_list_item,parent,false);

        Alarm currentAlarm = getItem(position);
        assert currentAlarm != null;

        TextView time = (TextView) listItem.findViewById(R.id.alarm_time);
        time.setText(currentAlarm.getAlarmTime());

        TextView days = (TextView) listItem.findViewById(R.id.alarm_days);
        days.setText(currentAlarm.getAlarmDays());

        TextView label = (TextView) listItem.findViewById(R.id.alarm_label);
        label.setText(currentAlarm.getAlarmLabel());

//        TextView snooze_interval = (TextView) listItem.findViewById(R.id.alarm_snooze_interval);
//        snooze_interval.setText(currentAlarm.getAlarmSnoozeInterval());
//
//        TextView snooze_frequency = (TextView) listItem.findViewById(R.id.alarm_snooze_frequency);
//        snooze_frequency.setText(currentAlarm.getAlarmSnoozeFrequency());

        return listItem;
    }
}
