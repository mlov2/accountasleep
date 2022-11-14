package com.example.accountasleep.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.accountasleep.R;


public class CustomAdapter extends BaseAdapter {
    String[] alarmtime;
    String[] alarmdays;
    String[] alarmlabel;
    String[] alarmsnoozeinterval;
    String[] alarmsnoozefrequency;

    public CustomAdapter(String[] alarmtime, String[] alarmdays, String[] alarmlabel, String[] alarmsnoozeinterval, String[] alarmsnoozefrequency) {
        super();
        this.alarmtime = alarmtime;
        this.alarmdays = alarmdays;
        this.alarmlabel = alarmlabel;
        this.alarmsnoozeinterval = alarmsnoozeinterval;
        this.alarmsnoozefrequency = alarmsnoozefrequency;
    }

    @Override
    public int getCount() {
        return alarmtime.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView alarmTimeText = (TextView) view.findViewById(R.id.alarm_time);
        TextView alarmDaysText = (TextView) view.findViewById(R.id.alarm_days);
        TextView alarmLabelText = (TextView) view.findViewById(R.id.alarm_label);
        TextView alarmSnoozeIntervalText = (TextView) view.findViewById(R.id.alarm_snooze_interval);
        TextView alarmSnoozeFrequencyText = (TextView) view.findViewById(R.id.alarm_snooze_frequency);

        alarmTimeText.setText(alarmtime[i]);
        alarmDaysText.setText(alarmdays[i]);
        alarmLabelText.setText(alarmlabel[i]);
        alarmSnoozeIntervalText.setText(alarmsnoozeinterval[i]);
        alarmSnoozeFrequencyText.setText(alarmsnoozefrequency[i]);

        return view;
    }
}
