package com.example.accountasleep.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.accountasleep.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}