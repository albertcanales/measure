package com.example.albert.measure.ui.results;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albert.measure.R;
import com.example.albert.measure.activities.AddAngleActivity;
import com.example.albert.measure.activities.AddAreaActivity;
import com.example.albert.measure.activities.AddPointActivity;
import com.example.albert.measure.activities.AddVectorActivity;
import com.example.albert.measure.activities.AddVolumeActivity;
import com.example.albert.measure.activities.ResultsActivity;
import com.example.albert.measure.elements.ElementsLists;

import java.util.Objects;

import static com.example.albert.measure.ui.results.SectionsPagerAdapter.ANGLE_TAB;
import static com.example.albert.measure.ui.results.SectionsPagerAdapter.AREA_TAB;
import static com.example.albert.measure.ui.results.SectionsPagerAdapter.POINT_TAB;
import static com.example.albert.measure.ui.results.SectionsPagerAdapter.TAB_TITLES;
import static com.example.albert.measure.ui.results.SectionsPagerAdapter.VECTOR_TAB;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ResultsActivity myActivity;

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

        Button addElement = root.findViewById(R.id.add_element);
        TextView noElementTV = root.findViewById(R.id.noElementTV);

        RecyclerView recyclerViewElements = root.findViewById(R.id.elementsRecyclerView);
        recyclerViewElements.setHasFixedSize(true);
        recyclerViewElements.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewElements.setVisibility(View.INVISIBLE);

        myActivity = (ResultsActivity) Objects.requireNonNull(getActivity());
        noElementTV.setVisibility(View.GONE);

        ElementsLists elements = myActivity.getElements();

        RecyclerView.Adapter adapter;
        if (tabPosition == POINT_TAB) {
            if (elements.getPointList().isEmpty()) noElementTV.setVisibility(View.VISIBLE);
            else recyclerViewElements.setVisibility(View.VISIBLE);
            adapter = new PointsAdapter(elements, myActivity);
        } else if (tabPosition == ANGLE_TAB) {   // ANGLES
            if (elements.getAngleList().isEmpty()) noElementTV.setVisibility(View.VISIBLE);
            else recyclerViewElements.setVisibility(View.VISIBLE);
            adapter = new AnglesAdapter(elements, myActivity);
        } else if (tabPosition == VECTOR_TAB) {
            if (elements.getVectorList().isEmpty()) noElementTV.setVisibility(View.VISIBLE);
            else recyclerViewElements.setVisibility(View.VISIBLE);
            adapter = new VectorsAdapter(elements, myActivity);
        } else if (tabPosition == AREA_TAB) {
            if (elements.getAreaList().isEmpty()) noElementTV.setVisibility(View.VISIBLE);
            else recyclerViewElements.setVisibility(View.VISIBLE);
            adapter = new AreasAdapter(elements, myActivity);
        } else {
            if (elements.getVolumeList().isEmpty()) noElementTV.setVisibility(View.VISIBLE);
            else recyclerViewElements.setVisibility(View.VISIBLE);
            adapter = new VolumesAdapter(elements, myActivity);
        }
        recyclerViewElements.setAdapter(adapter);
        addElement.setOnClickListener(new AddElementButtonListener(tabPosition, myActivity));
        addElement.setText(String.format("Add new %s", getString(TAB_TITLES[tabPosition])).toUpperCase());
        noElementTV.setText(String.format("No %s calculated.\nClick below to add one!",
                getString(TAB_TITLES[tabPosition]).toLowerCase()));
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ElementsLists elements = Objects.requireNonNull(data.getExtras()).getParcelable("elements");
            assert elements != null;
            myActivity.setElements(elements);
        }
        myActivity.refreshAdapter(requestCode);
    }

    private class AddElementButtonListener implements View.OnClickListener {

        final int elementType;
        final Context context;

        AddElementButtonListener(int elementType, Context context) {
            this.elementType = elementType;
            this.context = context;
        }

        @Override
        public void onClick(View view) {    // Add element
            Intent intent = new Intent();
            if (elementType == POINT_TAB)
                intent = new Intent(context, AddPointActivity.class);
            else if (elementType == ANGLE_TAB) {
                if (myActivity.getElements().getPointList().size() < 3)
                    Toast.makeText(context, "There must be at least three points", Toast.LENGTH_SHORT).show();
                else
                    intent = new Intent(context, AddAngleActivity.class);
            } else if (elementType == VECTOR_TAB) {
                if (myActivity.getElements().getPointList().size() < 2)
                    Toast.makeText(context, "There must be at least two points", Toast.LENGTH_SHORT).show();
                else
                    intent = new Intent(context, AddVectorActivity.class);
            } else if (elementType == AREA_TAB) {
                if (myActivity.getElements().getPointList().size() < 3)
                    Toast.makeText(context, "There must be at least three points", Toast.LENGTH_SHORT).show();
                else
                    intent = new Intent(context, AddAreaActivity.class);
            } else {
                if (myActivity.getElements().getAreaList().size() < 1)
                    Toast.makeText(context, "There must be at least one area", Toast.LENGTH_SHORT).show();
                else
                    intent = new Intent(context, AddVolumeActivity.class);
            }
            if (!intent.filterEquals(new Intent())) {
                if (myActivity.isActivityActive()) {
                    myActivity.setActivityActive(false);
                    intent.putExtra("elements", myActivity.getElements());
                    startActivityForResult(intent, elementType);
                }
            }
        }
    }
}