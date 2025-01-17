package com.example.movekos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class SecondOrderFragment extends Fragment {
    TextView next;
    TextView back;
    ViewPager viewPager;
    public SecondOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_order, container, false);
        viewPager = getActivity().findViewById(R.id.viewPager);
        next = view.findViewById(R.id.slideTwoNext);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                viewPager.setCurrentItem(2);
            }
        });

        back = view.findViewById(R.id.slideTwoBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                viewPager.setCurrentItem(0);
            }
        });

        return view;
    }
}