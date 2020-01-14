package com.example.albert.measure.ui.tutorial;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albert.measure.R;
import com.example.albert.measure.activities.HeightActivity;
import com.example.albert.measure.activities.TutorialActivity;

import java.util.Objects;

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
            @NonNull LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
        View myView;
        if (tabPosition == 1) {
            myView = inflater.inflate(R.layout.fragment_tutorial_introduction, container, false);
            myView.findViewById(R.id.introduction_video).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "https://www.youtube.com/watch?v=hK7HbC1EGWM";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        } else if (tabPosition == 2) {
            myView = inflater.inflate(R.layout.fragment_tutorial_height, container, false);
            int textId = ((TutorialActivity) Objects.requireNonNull(getActivity())).getHeightMode() == HeightActivity.MODE_MANUAL ?
                    R.string.tutorial_manual_height : R.string.tutorial_automatic_height;
            ((TextView) myView.findViewById(R.id.height_type_tv)).setText(textId);
        } else if (tabPosition == 3) {
            myView = inflater.inflate(R.layout.fragment_tutorial_pointtypes, container, false);
        } else if (tabPosition == 4) {
            myView = inflater.inflate(R.layout.fragment_tutorial_points, container, false);
        } else {
            myView = inflater.inflate(R.layout.fragment_tutorial_measure, container, false);
            myView.findViewById(R.id.guide_video).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "https://www.youtube.com/watch?v=Xj892oAFnD0";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        }
        return myView;
    }
}