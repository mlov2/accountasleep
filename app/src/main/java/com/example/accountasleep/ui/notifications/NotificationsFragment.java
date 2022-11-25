package com.example.accountasleep.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.accountasleep.RingActivity;
import com.example.accountasleep.R;
import com.example.accountasleep.databinding.FragmentNotificationsBinding;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private HashMap<String, String> initialContacts;

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
        final TextView contactNameText = binding.textContactName1;
        final TextView contactNumberText = binding.textContactNumber1;
        manageContactsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.linlayAccountPage.setVisibility(View.GONE);
                binding.btnStartQuiz.setVisibility(View.GONE);
                binding.rellayManageContactsPage.setVisibility(View.VISIBLE);
                if (notificationsViewModel.getContacts() != null) {
                    initialContacts = new LinkedHashMap<>();
                    for (String name: notificationsViewModel.getContacts().keySet()) {
                        initialContacts.put(name, notificationsViewModel.getNumber(name));
                    }
//                    initialContacts = notificationsViewModel.getContacts();
                } else {
                    initialContacts = new LinkedHashMap<>();
                }
//                loadInContacts(notificationsViewModel);
                updateContacts();
                Log.i("Map Notif Manage", notificationsViewModel.getContacts().toString());
                Log.i("Map Frag Manage", initialContacts.toString());


                final EditText enteredContactName = binding.edittextContactName;
                final EditText enteredPhoneNumber = binding.edittextPhoneNumber;
                enteredPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

                final ImageButton addContactButton = binding.imgbtnAddContact;
                addContactButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String contactName = enteredContactName.getText().toString();
                        String phoneNumber = enteredPhoneNumber.getText().toString().replaceAll("[()\\s-]+", "");
                        // Contact name invalid if it's empty (including only spaces)
                        if (contactName.replaceAll("\\s", "").equals("")) {
                            Toast.makeText(getActivity().getBaseContext(), "Invalid contact name cannot be added.", Toast.LENGTH_SHORT).show();
                        } else if (!PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
                            Toast.makeText(getActivity().getBaseContext(), "Invalid phone number cannot be added.", Toast.LENGTH_SHORT).show();
                        } else {
//                            notificationsViewModel.addContact(enteredContactName.getText().toString(), enteredPhoneNumber.getText().toString());
                            initialContacts.put(enteredContactName.getText().toString(), enteredPhoneNumber.getText().toString());

//                    contactNumberText.setText(enteredPhoneNumber.getText().toString());
//                    contactNameText.setText(enteredContactName.getText().toString());
//                            loadInContacts(notificationsViewModel);
                            updateContacts();
                            enteredContactName.setText("");
                            enteredPhoneNumber.setText("");
                        }
//                        enteredPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
                    }
                });

                final Button confirmContactsButton = binding.btnManageContactsConfirm;
                confirmContactsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        enteredPhoneNumber.setText("");
//                        notificationsViewModel.saveContacts();
//                        loadInContacts(notificationsViewModel);
                        notificationsViewModel.setContacts(initialContacts);
                        updateContacts();
                        Log.i("Map Notif Confirm", notificationsViewModel.getContacts().toString());
                        Log.i("Map Frag Confirm", initialContacts.toString());

                        binding.rellayManageContactsPage.setVisibility(View.GONE);
                        binding.linlayAccountPage.setVisibility(View.VISIBLE);
                        binding.btnStartQuiz.setVisibility(View.VISIBLE);
                    }
                });

                final Button cancelContactsButton = binding.btnManageContactsCancel;
                cancelContactsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        enteredPhoneNumber.setText("");
                        contactNumberText.setText("");
//                        notificationsViewModel.cancelContacts();
//                        loadInContacts(notificationsViewModel);
                        initialContacts.clear();
                        for (String name : notificationsViewModel.getContactNames()) {
                            initialContacts.put(name, notificationsViewModel.getNumber(name));
                        }
//                        initialContacts = notificationsViewModel.getContacts();
                        updateContacts();
                        Log.i("Map Notif Cancel", notificationsViewModel.getContacts().toString());
                        Log.i("Map Frag Cancel", initialContacts.toString());


                        binding.rellayManageContactsPage.setVisibility(View.GONE);
                        binding.linlayAccountPage.setVisibility(View.VISIBLE);
                        binding.btnStartQuiz.setVisibility(View.VISIBLE);
                    }
                });



            }
        });



        return binding.getRoot();
    }


    void loadInContacts(NotificationsViewModel notificationsViewModel) {
        final RelativeLayout contact1 = binding.rellayContact1;
        final RelativeLayout contact2 = binding.rellayContact2;
        final RelativeLayout contact3 = binding.rellayContact3;
        final RelativeLayout contact4 = binding.rellayContact4;
        final RelativeLayout contact5 = binding.rellayContact5;

        contact1.setVisibility(View.GONE);
        contact2.setVisibility(View.GONE);
        contact3.setVisibility(View.GONE);
        contact4.setVisibility(View.GONE);
        contact5.setVisibility(View.GONE);

        TextView contactName;
        TextView contactNumber;

        if (notificationsViewModel.getContacts() != null && notificationsViewModel.getNumberOfContacts() != 0) {
            int count = 1;
            for (String name : notificationsViewModel.getContactNames()) {
                if (count == 1) {
                    contact1.setVisibility(View.VISIBLE);
                    contactName = binding.textContactName1;
                    contactNumber = binding.textContactNumber1;
                } else if (count == 2) {
                    contact2.setVisibility(View.VISIBLE);
                    contactName = binding.textContactName2;
                    contactNumber = binding.textContactNumber2;
                } else if (count == 3) {
                    contact3.setVisibility(View.VISIBLE);
                    contactName = binding.textContactName3;
                    contactNumber = binding.textContactNumber3;
                } else if (count == 4) {
                    contact4.setVisibility(View.VISIBLE);
                    contactName = binding.textContactName4;
                    contactNumber = binding.textContactNumber4;
                } else {
                    contact5.setVisibility(View.VISIBLE);
                    contactName = binding.textContactName5;
                    contactNumber = binding.textContactNumber5;
                }
                contactName.setText(name);
                contactNumber.setText(notificationsViewModel.getNumber(name));
                count++;
            }
        }
    }

    void updateContacts() {
        final RelativeLayout contact1 = binding.rellayContact1;
        final RelativeLayout contact2 = binding.rellayContact2;
        final RelativeLayout contact3 = binding.rellayContact3;
        final RelativeLayout contact4 = binding.rellayContact4;
        final RelativeLayout contact5 = binding.rellayContact5;

        contact1.setVisibility(View.GONE);
        contact2.setVisibility(View.GONE);
        contact3.setVisibility(View.GONE);
        contact4.setVisibility(View.GONE);
        contact5.setVisibility(View.GONE);

        TextView contactName;
        TextView contactNumber;

        if (initialContacts != null && initialContacts.size() != 0) {
            int count = 1;
            for (String name : initialContacts.keySet()) {
                if (count == 1) {
                    contact1.setVisibility(View.VISIBLE);
                    contactName = binding.textContactName1;
                    contactNumber = binding.textContactNumber1;
                } else if (count == 2) {
                    contact2.setVisibility(View.VISIBLE);
                    contactName = binding.textContactName2;
                    contactNumber = binding.textContactNumber2;
                } else if (count == 3) {
                    contact3.setVisibility(View.VISIBLE);
                    contactName = binding.textContactName3;
                    contactNumber = binding.textContactNumber3;
                } else if (count == 4) {
                    contact4.setVisibility(View.VISIBLE);
                    contactName = binding.textContactName4;
                    contactNumber = binding.textContactNumber4;
                } else {
                    contact5.setVisibility(View.VISIBLE);
                    contactName = binding.textContactName5;
                    contactNumber = binding.textContactNumber5;
                }
                contactName.setText(name);
                contactNumber.setText(initialContacts.get(name));
                count++;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}