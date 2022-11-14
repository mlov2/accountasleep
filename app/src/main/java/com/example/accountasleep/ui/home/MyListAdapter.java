package com.example.accountasleep.ui.home;

import android.app.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.accountasleep.R;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] alarmtime;
    private final String[] alarmdays;
    private final String[] alarmlabel;
    private final String[] alarmsnoozeinterval;
    private final String[] alarmsnoozefrequency;

    public MyListAdapter(@NonNull Context context, int resource, Activity context1, String[] alarmtime, String[] alarmdays, String[] alarmlabel, String[] alarmsnoozeinterval, String[] alarmsnoozefrequency) {
        super(context, resource);
        this.context = context1;
        this.alarmtime = alarmtime;
        this.alarmdays = alarmdays;
        this.alarmlabel = alarmlabel;
        this.alarmsnoozeinterval = alarmsnoozeinterval;
        this.alarmsnoozefrequency = alarmsnoozefrequency;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.alarm_list, null, true);

        TextView alarmTimeText = (TextView) rowView.findViewById(R.id.alarm_time);
        TextView alarmDaysText = (TextView) rowView.findViewById(R.id.alarm_days);
        TextView alarmLabelText = (TextView) rowView.findViewById(R.id.alarm_label);
        TextView alarmSnoozeIntervalText = (TextView) rowView.findViewById(R.id.alarm_snooze_interval);
        TextView alarmSnoozeFrequencyText = (TextView) rowView.findViewById(R.id.alarm_snooze_frequency);

        alarmTimeText.setText(alarmtime[position]);
        alarmDaysText.setText(alarmdays[position]);
        alarmLabelText.setText(alarmlabel[position]);
        alarmSnoozeIntervalText.setText(alarmsnoozeinterval[position]);
        alarmSnoozeFrequencyText.setText(alarmsnoozefrequency[position]);

        return rowView;
    };
};
