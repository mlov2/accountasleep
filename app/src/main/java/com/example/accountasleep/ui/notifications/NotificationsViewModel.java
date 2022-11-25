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
    private String contact1Number;
    private HashMap<String, String> savedContacts = new LinkedHashMap<>();
    private HashMap<String, String> tempContacts = new LinkedHashMap<>();

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

//    public LiveData<String> getText() {
//        return mText;
//    }
//
//    public void setContact1Number(String contact1Number) {
//        this.contact1Number = contact1Number;
//    }

//    public String getContact1Number() {
//        return contact1Number;
//    }

    public void addContact(String contactName, String contactNumber) {
        tempContacts.put(contactName, contactNumber);
    }

    public Set<String> getContactNames() {
        return tempContacts.keySet();
    }

    public String getNumber(String contactName) {
        return tempContacts.get(contactName);
    }

    public Integer getNumberOfContacts() {
        return tempContacts.size();
    }

    public HashMap<String, String> getContacts() {
        return tempContacts;
    }

    public void cancelContacts() {
        tempContacts = savedContacts;
    }

    public void saveContacts() {
        savedContacts = tempContacts;
    }

//    private HashMap<String, String> copyMap(Map<String, String> original)
//    {
//        Map<String, String> second_Map = new HashMap<>();
//
//        for (Map.Entry<String, String> entry : original.entrySet()) {
//
//            second_Map.put(entry.getKey(),
//                    entry.getValue());
//        }
//        return (HashMap<String, String>) second_Map;
//    }
//
    public void setContacts(HashMap<String, String> newsavedContacts) {
        tempContacts = new LinkedHashMap<>();
        for (String name : newsavedContacts.keySet()) {
            tempContacts.put(name, newsavedContacts.get(name));
        }
    }

}