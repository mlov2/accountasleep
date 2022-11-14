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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.accountasleep.R;
import com.example.accountasleep.databinding.FragmentDashboardBinding;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    ImageButton btn;
    GridView gridView;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set Gridview
        gridView = (GridView) root.findViewById(R.id.gridview);
        // Initial Images Adapter
        ImagesAdapter imagesAdapter = new ImagesAdapter(getActivity(), dashboardViewModel.imgPaths);
        gridView.setAdapter(imagesAdapter);

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            // Add Image Uri
                            dashboardViewModel.addImgPath(uri);
                            // Update Images Adapter
                            ImagesAdapter imagesAdapter = new ImagesAdapter(getActivity(), dashboardViewModel.imgPaths);
                            gridView.setAdapter(imagesAdapter);

                        }
                    }
                });

        btn = root.findViewById(R.id.ImageButton02);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                someActivityResultLauncher.launch(intent);

            }
        });

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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}