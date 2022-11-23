package com.example.accountasleep.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.accountasleep.RingActivity;
import com.example.accountasleep.R;
import com.example.accountasleep.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        // Inflate the Account page xml
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);

        // TEMPORARY button for starting the RingActivity
        // TODO: delete after this is linked to be set off by actual alarms
        final Button startQuizButton = binding.btnStartQuiz;
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RingActivity.class);
                startActivity(intent);
            }
        });

        // Button logic for "Manage Contacts"
        final Button manageContactsButton = binding.btnSelectManageContacts;
        final TextView contactText = binding.textContact1;
        manageContactsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.linlayAccountPage.setVisibility(View.GONE);
                binding.btnStartQuiz.setVisibility(View.GONE);
                binding.rellayManageContactsPage.setVisibility(View.VISIBLE);
                contactText.setText(notificationsViewModel.getContact1Number());
            }
        });

        final EditText enteredText = binding.edittextPhoneNumber;
        enteredText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        final ImageButton addNumberButton = binding.imgbtnAddPhoneNumber;
        addNumberButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String phoneNumber = enteredText.getText().toString().replaceAll("[()\\s-]+", "");
                if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
                    contactText.setText(enteredText.getText().toString());
                    enteredText.setText("");
                } else {
                    Toast.makeText(getActivity().getBaseContext(), "Invalid phone number cannot be added.", Toast.LENGTH_SHORT).show();
                }
                enteredText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
            }
        });

        final Button confirmContactsButton = binding.btnManageContactsConfirm;
        confirmContactsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                notificationsViewModel.setContact1Number((String) contactText.getText());
                binding.rellayManageContactsPage.setVisibility(View.GONE);
                binding.linlayAccountPage.setVisibility(View.VISIBLE);
                binding.btnStartQuiz.setVisibility(View.VISIBLE);
                enteredText.setText("");
            }
        });

        final Button cancelContactsButton = binding.btnManageContactsCancel;
        cancelContactsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.rellayManageContactsPage.setVisibility(View.GONE);
                binding.linlayAccountPage.setVisibility(View.VISIBLE);
                binding.btnStartQuiz.setVisibility(View.VISIBLE);
                enteredText.setText("");
                contactText.setText("");
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}