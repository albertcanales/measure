package com.example.albert.measure.ui.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.albert.measure.R;
import com.example.albert.measure.activities.ResultsActivity;
import com.example.albert.measure.elements.Angle;
import com.example.albert.measure.elements.Point;

import java.util.List;
import java.util.Objects;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

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
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        int tabPosition = Objects.requireNonNull(getArguments()).getInt(ARG_SECTION_NUMBER);
        Log.d("TAB", String.valueOf(tabPosition));
        View root = inflater.inflate(R.layout.fragment_results, container, false);

        RecyclerView recyclerViewElements = root.findViewById(R.id.elementsRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewElements.setHasFixedSize(true);

        // use a linear layout manager
        recyclerViewElements.setLayoutManager(new LinearLayoutManager(getContext()));

        ResultsActivity myActivity = ((ResultsActivity) Objects.requireNonNull(getActivity()));
        if(tabPosition == 0) {  // POINTS
            List<Point> pointList = myActivity.getPointList();
            PointsAdapter pointsAdapter = new PointsAdapter(pointList, myActivity);
            recyclerViewElements.setAdapter(pointsAdapter);
        } else if(tabPosition == 1) {
            List<Angle> angleList = myActivity.getAngleList();
            //List<Point> pointList = myActivity.getPointList();
            //angleList.add(new Angle("Test Angle", pointList.get(0), pointList.get(1), pointList.get(2)));
            //angleList.add(new Angle(new Point("First", 3.0, 4.0, 5.0),
            //        new Point("Second", 5.0, 7.0, 0.0), new Point("Vertex", 0.0, 0.0, 0.0)));
            AnglesAdapter anglesAdapter = new AnglesAdapter(angleList);
            recyclerViewElements.setAdapter(anglesAdapter);
        }

        return root;
    }
}