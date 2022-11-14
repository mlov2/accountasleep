package com.example.accountasleep.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.accountasleep.R;
import com.example.accountasleep.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    // Array of strings...
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // references:
        // - to get listview to work: https://www.youtube.com/watch?v=7MRnL_slGrI
        // - to create a custom adapter: TODO (refer to one of the links below)
        ListView lv = (ListView) root.findViewById(R.id.alarm_list_view);
        ArrayAdapter adapter=new ArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mobileArray);
//        CustomAdapter adapter = new CustomAdapter(mobileArray, mobileArray, mobileArray, mobileArray, mobileArray);
        lv.setAdapter(adapter);



        // reference: https://www.tutorialspoint.com/android/android_list_view.htm
        // reference: https://www.tutorialspoint.com/how-to-use-findviewbyid-in-fragment
//        ViewGroup alarm_lst = (ViewGroup) inflater.inflate(R.layout.fragment_home, null);
////        LinearLayout alarm_list = binding.alarmListLayout;
//        ArrayAdapter adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.alarm_list, mobileArray);
//        ListView listView = (ListView) alarm_lst.findViewById(R.id.alarm_list_list);
//        listView.setAdapter(adapter);

//        MyListAdapter adapter=new MyListAdapter(this, mobileArray, mobileArray, mobileArray, mobileArray, mobileArray);
//        list=(ListView)findViewById(R.id.list);
//        list.setAdapter(adapter);

        // Inflate the layout for this fragment - ref: https://stackoverflow.com/questions/69164648/adding-a-listview-in-a-fragment
//        View contentView = inflater.inflate(R.layout.fragment_home, container, false);
//        ListView listView = contentView.findViewById(R.id.alarm_list_list);
//        CustomAdapter listAdapter = new CustomAdapter(mobileArray, mobileArray, mobileArray, mobileArray, mobileArray);
//        listView.setAdapter(listAdapter);

        LinearLayout alarm_header = binding.alarmHeaderLayout;
        LinearLayout alarm_setting = binding.alarmSettingLayout;
        LinearLayout alarm_repeat = binding.repeatSetterLayout;
        LinearLayout alarm_snooze = binding.snoozeSetterLayout;
        Button repeat_button = binding.repeatButton;
        Button duration_button = binding.durationButton;
        repeat_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                alarm_header.setVisibility(View.INVISIBLE);
                alarm_setting.setVisibility(View.INVISIBLE);
                alarm_repeat.setVisibility(View.VISIBLE);
            }
        });
        duration_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                alarm_header.setVisibility(View.INVISIBLE);
                alarm_setting.setVisibility(View.INVISIBLE);
                alarm_snooze.setVisibility(View.VISIBLE);
            }
        });

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}