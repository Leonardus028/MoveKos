package com.example.movekos;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class IntroAdapter extends FragmentPagerAdapter {


    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FirstOrderFragment();
            case 1:
                return new SecondOrderFragment();
            case 2:
                return new ThirdOrderFragment();
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
