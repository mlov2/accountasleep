package com.example.accountasleep;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// This Activity pulls up all the pages and logic that should appear after the alarm goes off.
public class RingActivity extends Activity {

    public RingActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pulls up the page of the "Stop" and "Snooze" buttons
        setContentView(R.layout.activity_ring);

        final Button stopAlarmButton = findViewById(R.id.btn_stop_alarm);

        // Pulls up the page for the quiz screen after "Stop" is pressed
        stopAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.rellay_ring_page).setVisibility(View.GONE);
                findViewById(R.id.linlay_quiz_screen).setVisibility(View.VISIBLE);

                TextView timerText = findViewById(R.id.text_quiz_time);
                new CountDownTimer(90000, 1000) { // 30000, 1000 is 30 seconds
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