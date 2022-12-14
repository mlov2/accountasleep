package com.example.accountasleep.ui.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.accountasleep.R;
import com.example.accountasleep.databinding.FragmentDashboardBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    FloatingActionButton cameraBtn;
    FloatingActionButton albumBtn;
    GridView gridView;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    ActivityResultLauncher<Intent> cameraActivityResultLauncher;
    DashboardViewModel dashboardViewModel;


    // Activity: Delete Image
    ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                // Remove Image Uri
                dashboardViewModel.removeImgPath(Uri.parse(result.getData().getStringExtra("ret")));
                // Update Images Adapter
                ImagesAdapter imagesAdapter = new ImagesAdapter(getActivity(), dashboardViewModel.imgPaths);
                gridView.setAdapter(imagesAdapter);
            } else {
                System.out.println("Delete Null!");
            }
        }
    });

    @SuppressLint("IntentReset")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Floating Button

        FloatingActionButton fab = root.findViewById(R.id.floatingActionButton);
        albumBtn = root.findViewById(R.id.ActionButtonImg);
        cameraBtn = root.findViewById(R.id.ActionButtonCam);
        fab.setOnClickListener(new View.OnClickListener() {

            boolean isFABOpen = false;

            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }

            private void showFABMenu(){
                isFABOpen=true;
                albumBtn.animate().translationY(-getResources().getDimension(R.dimen.standard_70));
                cameraBtn.animate().translationY(-getResources().getDimension(R.dimen.standard_140));
            }

            private void closeFABMenu(){
                isFABOpen=false;
                albumBtn.animate().translationY(0);
                cameraBtn.animate().translationY(0);
            }
        });

        // Set Gridview
        gridView = (GridView) root.findViewById(R.id.gridview);
        // Initial Images Adapter
        ImagesAdapter imagesAdapter = new ImagesAdapter(getActivity(), dashboardViewModel.imgPaths);
        gridView.setAdapter(imagesAdapter);

        // Show Image Detail when click the item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ImageDetail.class);
                intent.putExtra("image", String.valueOf(dashboardViewModel.imgPaths[position])); // put image data in Intent
                intentActivityResultLauncher.launch(intent);
            }
        });

        // Use System Photo Gallery
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

//        btn = root.findViewById(R.id.ImageButton02);
        albumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                someActivityResultLauncher.launch(intent);

                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.SEND_SMS)) {
                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.SEND_SMS},
                                0);
                    }
                }
//
//                try{
//                    SmsManager smgr = SmsManager.getDefault();
//                    // smgr.sendMultimediaMessage(getContext(), Uri.parse("android.resource://com.example.accountasleep/" + R.drawable.sample_img_2), null, null, null);
//                    smgr.sendTextMessage("+11234567891",null,"Test: Accountasleep!",null,null);
//                    Toast.makeText(getActivity(), "SMS Sent Successfully!", Toast.LENGTH_SHORT).show();
//                }
//                catch (Exception e){
//                    Toast.makeText(getActivity(), "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
//                }

            }
        });

        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Bitmap photo = (Bitmap) data.getExtras().get("data");

                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                            String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), photo, "Title", null);
                            Uri uri = Uri.parse(path);



                            //Uri uri = data.getData();
                            // Add Image Uri
                            dashboardViewModel.addImgPath(uri);
                            // Update Images Adapter
                            ImagesAdapter imagesAdapter = new ImagesAdapter(getActivity(), dashboardViewModel.imgPaths);
                            gridView.setAdapter(imagesAdapter);

                        }
                    }
                });

//        cameraBtn = root.findViewById(R.id.openCamera);
        this.cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraActivityResultLauncher.launch(open_camera);
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