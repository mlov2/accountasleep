package com.example.accountasleep.ui.home;

public class Alarm {
    private String alarmTime;
    private String alarmDays;
    private String alarmLabel;
    private int alarmSnoozeInterval;
    private int alarmSnoozeFrequency;

    public Alarm(String alarmTime, String alarmDays, String alarmLabel, int alarmSnoozeInterval, int alarmSnoozeFrequency) {
        this.alarmTime = alarmTime;
        this.alarmDays = alarmDays;
        this.alarmLabel = alarmLabel;
        this.alarmSnoozeInterval = alarmSnoozeInterval;
        this.alarmSnoozeFrequency = alarmSnoozeFrequency;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public void setAlarmDays(String alarmDays) {
        this.alarmDays = alarmDays;
    }

    public void setAlarmLabel(String alarmLabel) {
        this.alarmLabel = alarmLabel;
    }

    public void setAlarmSnoozeInterval(int alarmSnoozeInterval) {
        this.alarmSnoozeInterval = alarmSnoozeInterval;
    }

    public void setAlarmSnoozeFrequency(int alarmSnoozeFrequency) {
        this.alarmSnoozeFrequency = alarmSnoozeFrequency;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public String getAlarmDays() {
        return alarmDays;
    }

    public String getAlarmLabel() {
        return alarmLabel;
    }

    public int getAlarmSnoozeInterval() {
        return alarmSnoozeInterval;
    }

    public int getAlarmSnoozeFrequency() {
        return alarmSnoozeFrequency;
    }
}
