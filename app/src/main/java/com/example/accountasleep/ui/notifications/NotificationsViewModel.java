package com.example.accountasleep.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private HashMap<String, String> contacts = new LinkedHashMap<>();

    private final String defaultMessage = "Uh oh! I slept past my alarm today! I wasn't able to stop this photo from sending to you.";
    private String message = "";

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public Set<String> getContactNames() {
        return contacts.keySet();
    }

    public String getNumber(String contactName) {
        return contacts.get(contactName);
    }
    
    public HashMap<String, String> getContacts() {
        return contacts;
    }
    
    public void setContacts(HashMap<String, String> newContacts) {
        contacts = new LinkedHashMap<>();
        for (String name : newContacts.keySet()) {
            contacts.put(name, newContacts.get(name));
        }
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setMessage(String newMessage) {
        message = newMessage;
    }

    public String getMessage() {
        return message;
    }

}