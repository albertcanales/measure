package com.example.albert.measure.ui.tutorial;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albert.measure.R;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int tabPosition;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabPosition = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        int fragmentId = R.layout.fragment_tutorial_introduction;
        if (tabPosition == 2)
            fragmentId = R.layout.fragment_tutorial_pointtypes;
        else if (tabPosition == 3)
            fragmentId = R.layout.fragment_tutorial_points;
        else if (tabPosition == 4)
            fragmentId = R.layout.fragment_tutorial_measure;
        else if (tabPosition == 5)
            fragmentId = R.layout.fragment_tutorial_video;
        else if (tabPosition == 6)
            fragmentId = R.layout.fragment_tutorial_height;
        return inflater.inflate(fragmentId, container, false);
    }
}