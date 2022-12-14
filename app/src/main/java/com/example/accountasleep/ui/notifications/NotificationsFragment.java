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

        //  ============ MANAGE CONTACTS PAGE LOGIC ============

        // Button logic for "Manage Contacts"
        final Button manageContactsButton = binding.btnSelectManageContacts;
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
                } else {
                    initialContacts = new LinkedHashMap<>();
                }
                updateContacts();
                Log.i("Map Notif Manage", notificationsViewModel.getContacts().toString());
                Log.i("Map Frag Manage", initialContacts.toString());
            }
        });

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
                if (initialContacts.size() == 5) {
                    Toast.makeText(getActivity().getBaseContext(), "Maximum contacts have been reached.", Toast.LENGTH_SHORT).show();
                } else if (contactName.replaceAll("\\s", "").equals("")) {
                    Toast.makeText(getActivity().getBaseContext(), "Invalid contact name cannot be added.", Toast.LENGTH_SHORT).show();
                } else if (!PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
                    Toast.makeText(getActivity().getBaseContext(), "Invalid phone number cannot be added.", Toast.LENGTH_SHORT).show();
                } else {
                    initialContacts.put(enteredContactName.getText().toString(), enteredPhoneNumber.getText().toString());
                    updateContacts();
                    enteredContactName.setText("");
                    enteredPhoneNumber.setText("");
                }
            }
        });

        // Delete Contact 1
        final ImageButton deleteContact1 = binding.imgbtnDeleteContact1;
        deleteContact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView contact1Name = binding.textContactName1;
                initialContacts.remove(contact1Name.getText());
                updateContacts();
            }
        });

        // Delete Contact 2
        final ImageButton deleteContact2 = binding.imgbtnDeleteContact2;
        deleteContact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialContacts.remove(binding.textContactName2.getText());
                updateContacts();
            }
        });

        // Delete Contact 3
        final ImageButton deleteContact3 = binding.imgbtnDeleteContact3;
        deleteContact3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialContacts.remove(binding.textContactName3.getText());
                updateContacts();
            }
        });

        // Delete Contact 4
        final ImageButton deleteContact4 = binding.imgbtnDeleteContact4;
        deleteContact4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialContacts.remove(binding.textContactName4.getText());
                updateContacts();
            }
        });

        // Delete Contact 5
        final ImageButton deleteContact5 = binding.imgbtnDeleteContact5;
        deleteContact5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialContacts.remove(binding.textContactName5.getText());
                updateContacts();
            }
        });

        final Button confirmContactsButton = binding.btnManageContactsConfirm;
        confirmContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredContactName.setText("");
                enteredPhoneNumber.setText("");
                notificationsViewModel.setContacts(initialContacts);
                updateContacts();

                binding.rellayManageContactsPage.setVisibility(View.GONE);
                binding.linlayAccountPage.setVisibility(View.VISIBLE);
//                binding.btnStartQuiz.setVisibility(View.VISIBLE);
            }
        });

        final Button cancelContactsButton = binding.btnManageContactsCancel;
        cancelContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredContactName.setText("");
                enteredPhoneNumber.setText("");
                initialContacts.clear();
                for (String name : notificationsViewModel.getContactNames()) {
                    initialContacts.put(name, notificationsViewModel.getNumber(name));
                }
                updateContacts();
                Log.i("Map Notif Cancel", notificationsViewModel.getContacts().toString());
                Log.i("Map Frag Cancel", initialContacts.toString());

                binding.rellayManageContactsPage.setVisibility(View.GONE);
                binding.linlayAccountPage.setVisibility(View.VISIBLE);
//                binding.btnStartQuiz.setVisibility(View.VISIBLE);
            }
        });

        //  ============ EDIT MESSAGE PAGE LOGIC ============
        Button editMessageButton = binding.btnEditMessage;
        editMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.linlayAccountPage.setVisibility(View.GONE);
                binding.btnStartQuiz.setVisibility(View.GONE);
                binding.rellayEditMessage.setVisibility(View.VISIBLE);

                // Load in the last saved message if it's not the default empty string
                if (!notificationsViewModel.getMessage().equals("")) {
                    binding.edittextMessage.setText(notificationsViewModel.getMessage());
                }
            }
        });

        Button useDefaultMsgButton = binding.btnDefaultMessage;
        useDefaultMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edittextMessage.setText(notificationsViewModel.getDefaultMessage());
            }
        });

        Button confirmMsgButton = binding.btnEditMessageConfirm;
        confirmMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the text box is empty or only spaces
                if (binding.edittextMessage.getText().toString().replaceAll("\\s", "").equals("")) {
                    Toast.makeText(getActivity().getBaseContext(), "Please enter a message or use the default.", Toast.LENGTH_SHORT).show();
                } else {
                    notificationsViewModel.setMessage(binding.edittextMessage.getText().toString());
                    binding.rellayEditMessage.setVisibility(View.GONE);
                    binding.linlayAccountPage.setVisibility(View.VISIBLE);
//                    binding.btnStartQuiz.setVisibility(View.VISIBLE);
                }
            }
        });

        Button cancelMsgButton = binding.btnEditMessageCancel;
        cancelMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.rellayEditMessage.setVisibility(View.GONE);
                binding.linlayAccountPage.setVisibility(View.VISIBLE);
//                binding.btnStartQuiz.setVisibility(View.VISIBLE);
            }
        });

        return binding.getRoot();
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