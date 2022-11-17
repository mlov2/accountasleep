package com.example.accountasleep.ui.home;

public class Alarm {
    private String alarmTime;
    private String alarmAmPm;
    private String alarmDays;
    private String alarmLabel;
    private boolean alarmSendMsg;
    private int alarmSnoozeInterval;
    private int alarmSnoozeFrequency;
    private boolean alarmEnabled;

    public Alarm(String alarmTime, String alarmAmPm, String alarmDays, String alarmLabel, boolean alarmSendMsg, int alarmSnoozeInterval, int alarmSnoozeFrequency, boolean alarmEnabled) {
        this.alarmTime = alarmTime;
        this.alarmAmPm = alarmAmPm;
        this.alarmDays = alarmDays;
        this.alarmLabel = alarmLabel;
        this.alarmSnoozeInterval = alarmSnoozeInterval;
        this.alarmSnoozeFrequency = alarmSnoozeFrequency;
        this.alarmEnabled = alarmEnabled;
        this.alarmSendMsg = alarmSendMsg;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public void setAlarmAmPm(String alarmAmPm) {
        this.alarmAmPm = alarmAmPm;
    }

    public void setAlarmDays(String alarmDays) {
        this.alarmDays = alarmDays;
    }

    public void setAlarmLabel(String alarmLabel) {
        this.alarmLabel = alarmLabel;
    }

    public void setAlarmSendMsg(boolean alarmSendMsg) {
        this.alarmSendMsg = alarmSendMsg;
    }

    public void setAlarmSnoozeInterval(int alarmSnoozeInterval) {
        this.alarmSnoozeInterval = alarmSnoozeInterval;
    }

    public void setAlarmSnoozeFrequency(int alarmSnoozeFrequency) {
        this.alarmSnoozeFrequency = alarmSnoozeFrequency;
    }

    public void setAlarmEnabled(boolean alarmEnabled) {
        this.alarmEnabled = alarmEnabled;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public String getAlarmAmPm() {
        return alarmAmPm;
    }

    public String getAlarmDays() {
        return alarmDays;
    }

    public String getAlarmLabel() {
        return alarmLabel;
    }

    public boolean getAlarmSendMsg() { return alarmSendMsg; }

    public int getAlarmSnoozeInterval() {
        return alarmSnoozeInterval;
    }

    public int getAlarmSnoozeFrequency() {
        return alarmSnoozeFrequency;
    }

    public boolean getAlarmEnabled() { return alarmEnabled; }
}
