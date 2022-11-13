package com.example.accountasleep.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.accountasleep.R;
import com.example.accountasleep.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    ImageButton btn;
    GridView gridView;
    ImageView imgView;
    int SELECT_IMAGE_CODE = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn = root.findViewById(R.id.ImageButton02);
        // gridView = root.findViewById(R.id.photo_gallery);
        imgView = root.findViewById(R.id.photo_gallery);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(Intent.createChooser(intent, "Title"), SELECT_IMAGE_CODE);


            }
        });
//        Log.d("myTag", "This is my message");


//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //  Psuedocode:
            // Create empty photo array of uri data type and make sure it lasts when switching tabs
            // Add event listener for photo library button
                // Event listener should call Photo Picker
                    // Need to figure out how to import that component into library and where to integrate that code
                    // Figure out if saving works
                    // Picking photo should add to the array
            // Grid view should take all uris in photo array and establish it
            // Important: Make sure that emulator has photos

            // Extra:
                // Add event listener for camera button
                // Delete = extra feature
                // Use boolean to get rid of Note after it was displayed once
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Uri uri = data.getData();
//            Log.d("myTag", "This is my message2");
//            System.out.println("HERE");
//            System.out.println(uri);
            imgView.setImageURI(uri);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}