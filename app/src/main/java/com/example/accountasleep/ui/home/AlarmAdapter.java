package com.example.accountasleep.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
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

        TextView time_of_day = (TextView) listItem.findViewById(R.id.alarm_am_pm);
        time_of_day.setText(currentAlarm.getAlarmAmPm());

        TextView days = (TextView) listItem.findViewById(R.id.alarm_days);
        days.setText(currentAlarm.getAlarmDays());

        TextView label = (TextView) listItem.findViewById(R.id.alarm_label);
        label.setText(currentAlarm.getAlarmLabel());

        ImageView sendMsg = (ImageView) listItem.findViewById(R.id.send_msg);
        if (currentAlarm.getAlarmSendMsg()) {
            sendMsg.setVisibility(View.VISIBLE);
        } else {
            sendMsg.setVisibility(View.GONE);
        }

        if (currentAlarm.getAlarmSnoozeInterval() != -1 && currentAlarm.getAlarmSnoozeFrequency() != -1) {
            LinearLayout snooze_linear_layout = (LinearLayout) listItem.findViewById(R.id.snooze_layout);
            snooze_linear_layout.setVisibility(View.VISIBLE);

            TextView snooze_interval = (TextView) listItem.findViewById(R.id.alarm_snooze_interval);
            snooze_interval.setText(Integer.toString(currentAlarm.getAlarmSnoozeInterval()));

            TextView snooze_frequency = (TextView) listItem.findViewById(R.id.alarm_snooze_frequency);
            snooze_frequency.setText(Integer.toString(currentAlarm.getAlarmSnoozeFrequency()));
        } else {
            LinearLayout snooze_linear_layout = (LinearLayout) listItem.findViewById(R.id.snooze_layout);
            snooze_linear_layout.setVisibility(View.GONE);
        }

        if (currentAlarm.getAlarmEnabled()) {
            Switch alarm_enable = (Switch) listItem.findViewById(R.id.alarm_enable);
            alarm_enable.setChecked(true);
        } else {
            Switch alarm_enable = (Switch) listItem.findViewById(R.id.alarm_enable);
            alarm_enable.setChecked(false);
        }

        return listItem;
    }
}
