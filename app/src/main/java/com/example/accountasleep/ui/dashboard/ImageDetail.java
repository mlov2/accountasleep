package com.example.accountasleep.ui.dashboard;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProvider;

import com.example.accountasleep.R;

public class ImageDetail extends Activity {
    ImageView selectedImage;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail);

        selectedImage = (ImageView) findViewById(R.id.selectedImage); // init a ImageView
        intent = getIntent(); // get Intent which we set from Previous Activity
        selectedImage.setImageURI(Uri.parse(intent.getStringExtra("image")));

        final Button button = (Button) findViewById(R.id.imageDelete);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                setResult(RESULT_OK, new Intent().putExtra("ret", intent.getStringExtra("image")));
                finish();
            }
        });
    }
}
