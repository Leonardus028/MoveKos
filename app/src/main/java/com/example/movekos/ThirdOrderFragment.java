package com.example.movekos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.movekos.ui.notifications.NotificationsFragment;

public class ThirdOrderFragment extends Fragment {

    TextView done;
    TextView back;
    ViewPager viewPager;
    public ThirdOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third_order, container, false);
        viewPager = getActivity().findViewById(R.id.viewPager);
        done = view.findViewById(R.id.slideThreeDone);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(getActivity(), "Done", Toast.LENGTH_LONG).show();

            }
        });

        back = view.findViewById(R.id.slideThreeBack);
        done = view.findViewById(R.id.slideThreeDone);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                viewPager.setCurrentItem(1);
            }
        });

        return view;
    }
}