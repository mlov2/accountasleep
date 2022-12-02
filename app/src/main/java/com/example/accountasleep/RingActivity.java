package com.example.accountasleep;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.accountasleep.ui.home.Alarm;
import com.example.accountasleep.ui.home.HomeViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


// This Activity pulls up all the pages and logic that should appear after the alarm goes off.
public class RingActivity extends Activity {

    public RingActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        r.play();
        // Pulls up the page of the "Stop" and "Snooze" buttons
        setContentView(R.layout.activity_ring);

        final Button stopAlarmButton = findViewById(R.id.btn_stop_alarm);
        final Button snoozeAlarmButton = findViewById(R.id.btn_snooze_alarm);

        //Get snooze_limit from HomeFragment.java, snooze_limit=-1 when it's not set
        Intent intent = getIntent();
        String snooze_limit = intent.getStringExtra("snooze_limit");
        String snooze_duration = intent.getStringExtra("snooze_duration");
        final int[] snooze_limit_int = {Integer.parseInt(snooze_limit)};
        int snooze_duration_int = Integer.parseInt(snooze_duration);
        String input = "Snooze\n" + snooze_limit + " Remaining";
        if (snooze_limit_int[0] == -1){
            input = "Snooze\n0 Remaining";
        }

        snoozeAlarmButton.setText(input);
        HomeViewModel myObj = new HomeViewModel();
        // snoozeAlarmButton is only pressable when snooze_limit is set to be >=1
        if (snooze_limit_int[0] != -1){
            snoozeAlarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snooze_limit_int[0] -= 1;
                    r.stop();
                    findViewById(R.id.rellay_ring_page).setVisibility(View.GONE);
                    TextView timerText = findViewById(R.id.text_quiz_time);
                    myObj.setSnooze_limit_update(snooze_limit_int[0]);
                    myObj.setSnooze_flag(true);
                    finish();
                }
            });
        }

        // Pulls up the page for the quiz screen after "Stop" is pressed
        stopAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r.stop();
                findViewById(R.id.rellay_ring_page).setVisibility(View.GONE);
                findViewById(R.id.linlay_quiz_screen).setVisibility(View.VISIBLE);
                TextView timerText = findViewById(R.id.text_quiz_time);
                // new CountDownTimer(90000, 1000) { // 30000, 1000 is 30 seconds
                new CountDownTimer(10000, 1000) { // 30000, 1000 is 30 seconds
                    public void onTick(long millisUntilFinished) {
                        long minutes = (millisUntilFinished / 1000) / 60;
                        long seconds = (millisUntilFinished / 1000) % 60;
                        String secondsStr = Long.toString(seconds);
                        String formattedSeconds;
                        if (secondsStr.length() >= 2) {
                            formattedSeconds = secondsStr.substring(0, 2);
                        } else {
                            formattedSeconds = "0" + secondsStr;
                        }

                        timerText.setText("Time remaining: " + minutes + ":" + formattedSeconds);
                    }

                    // After the timer ends, show the "failure" screen if the user has not entered the correct text yet
                    public void onFinish() {
                        timerText.setText("done!");
                        if (findViewById(R.id.linlay_quiz_prompt).getVisibility() == View.VISIBLE) {
                            findViewById(R.id.linlay_quiz_prompt).setVisibility(View.GONE);
                            // Send SMS message
                            SmsManager smgr = SmsManager.getDefault();
                            smgr.sendTextMessage("+12175551212",null, "Uh oh! I slept past my alarm today! I wasn't able to stop this photo from sending to you.",null,null);
                            smgr.sendTextMessage("+12175551212",null, "[image.jpg]",null,null);
                            findViewById(R.id.linlay_quiz_fail).setVisibility(View.VISIBLE);
                        }
                    }


                }.start();

            }
        });

        // SUBMIT BUTTON functionality
        final Button submitButton = findViewById(R.id.btn_submit_quiz);
        final EditText quizTextSubmission = (EditText)findViewById(R.id.edittext_quiz);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("EditText", quizTextSubmission.getText().toString());
                TextView passageText = findViewById(R.id.text_quiz_passage);
                if (quizTextSubmission.getText().toString().equals(passageText.getText().toString())) {
                    findViewById(R.id.linlay_quiz_prompt).setVisibility(View.GONE);
                    findViewById(R.id.linlay_quiz_success).setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getBaseContext(), "Submitted input does not match the prompt. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // SUCCESS SCREEN - OK BUTTON functionality
        final Button successOkButton = findViewById(R.id.btn_confirm_quiz_success);
        successOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // FAIL SCREEN - OK BUTTON functionality
        final Button failOkButton = findViewById(R.id.btn_confirm_quiz_fail);
        failOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}