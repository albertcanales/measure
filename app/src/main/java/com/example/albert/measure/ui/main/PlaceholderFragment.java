package com.example.albert.measure.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
import com.example.albert.measure.elements.Angle;
import com.example.albert.measure.elements.Area;
import com.example.albert.measure.elements.Point;
import com.example.albert.measure.elements.Vector;
import com.example.albert.measure.elements.Volume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        myActivity = (ResultsActivity) Objects.requireNonNull(getActivity());
        String elementTypeStr = "";
        noElementTV.setVisibility(View.GONE);

        List<Point> pointList = myActivity.getPointList();
        List<Angle> angleList = myActivity.getAngleList();
        List<Vector> vectorList = myActivity.getVectorList();
        List<Area> areaList = myActivity.getAreaList();
        List<Volume> volumeList = myActivity.getVolumeList();

        RecyclerView.Adapter adapter = null;
        if (tabPosition == 0) {  // POINTS
            elementTypeStr = "point";
            if (pointList.isEmpty()) noElementTV.setVisibility(View.VISIBLE);
            adapter = new PointsAdapter(pointList, angleList, vectorList, areaList, volumeList, myActivity);
        } else if (tabPosition == 1) {   // ANGLES
            elementTypeStr = "angle";
            if (angleList.isEmpty()) noElementTV.setVisibility(View.VISIBLE);
            //angleList.add(new Angle("TestAngle", pointList.get(0), pointList.get(1), pointList.get(2)));
            //angleList.add(new Angle(new Point("First", 3.0, 4.0, 5.0),
            //        new Point("Second", 5.0, 7.0, 0.0), new Point("Vertex", 0.0, 0.0, 0.0)));
            adapter = new AnglesAdapter(pointList, angleList, vectorList, areaList, volumeList, myActivity);
        } else if (tabPosition == 2) {  // VECTORS
            elementTypeStr = "distance";
            if (vectorList.isEmpty()) noElementTV.setVisibility(View.VISIBLE);
            //vectorList.add(new Vector("TestDistance1", pointList.get(0), pointList.get(1)));
            //vectorList.add(new Vector("TestDistance2", pointList.get(1), pointList.get(2)));
            adapter = new VectorsAdapter(pointList, angleList, vectorList, areaList, volumeList, myActivity);
        } else if (tabPosition == 3) {  // AREAS
            elementTypeStr = "area";
            if (areaList.isEmpty()) noElementTV.setVisibility(View.VISIBLE);
            //areaList.add(new Area("TestArea", pointList.get(0), pointList.get(1), pointList.get(2),1));
            adapter = new AreasAdapter(pointList, angleList, vectorList, areaList, volumeList, myActivity);
        } else {
            elementTypeStr = "volume";
            if (volumeList.isEmpty()) noElementTV.setVisibility(View.VISIBLE);
            adapter = new VolumesAdapter(pointList, angleList, vectorList, areaList, volumeList, myActivity);
        }
        recyclerViewElements.setAdapter(adapter);
        addElement.setOnClickListener(new AddElementButtonListener(tabPosition, myActivity));
        addElement.setText("Add ".concat(elementTypeStr));
        noElementTV.setText(String.format("No %s calculated.\nClick below to add one!", elementTypeStr));
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                List<Point> points = data.getParcelableArrayListExtra("points");
                myActivity.setPointList(points);
            } else if (requestCode == 1) {
                List<Angle> angles = data.getParcelableArrayListExtra("angles");
                myActivity.setAngleList(angles);
            } else if (requestCode == 2) {  // Vector
                List<Vector> vectors = data.getParcelableArrayListExtra("vectors");
                myActivity.setVectorList(vectors);
            } else if (requestCode == 3) {
                List<Area> areas = data.getParcelableArrayListExtra("areas");
                myActivity.setAreaList(areas);
            } else if(requestCode == 4) {
                List<Volume> volumes = data.getParcelableArrayListExtra("volumes");
                myActivity.setVolumeList(volumes);
            }
        }
        myActivity.refreshAdapter(requestCode);
    }

    private class AddElementButtonListener implements View.OnClickListener {

        int elementType;
        Context context;

        AddElementButtonListener(int elementType, Context context) {
            this.elementType = elementType;
            this.context = context;
        }

        @Override
        public void onClick(View view) {    // Add element
            Intent intent = new Intent();
            if (elementType == 0)
                intent = new Intent(context, AddPointActivity.class);
            else if (elementType == 1) {
                if (myActivity.getPointList().size() < 3)
                    Toast.makeText(context, "There must be at least three points", Toast.LENGTH_SHORT).show();
                else
                    intent = new Intent(context, AddAngleActivity.class);
            } else if (elementType == 2) {
                if (myActivity.getPointList().size() < 2)
                    Toast.makeText(context, "There must be at least two points", Toast.LENGTH_SHORT).show();
                else
                    intent = new Intent(context, AddVectorActivity.class);
            } else if (elementType == 3) {
                if (myActivity.getPointList().size() < 3)
                    Toast.makeText(context, "There must be at least three points", Toast.LENGTH_SHORT).show();
                else
                    intent = new Intent(context, AddAreaActivity.class);
            } else if (elementType == 4) {
                if (myActivity.getAreaList().size() < 1)
                    Toast.makeText(context, "There must be at least one area", Toast.LENGTH_SHORT).show();
                else
                    intent = new Intent(context, AddVolumeActivity.class);
            }
            if (!intent.filterEquals(new Intent())) {

                intent.putParcelableArrayListExtra("points", new ArrayList<Parcelable>(myActivity.getPointList()));
                intent.putParcelableArrayListExtra("vectors", new ArrayList<Parcelable>(myActivity.getVectorList()));
                intent.putParcelableArrayListExtra("angles", new ArrayList<Parcelable>(myActivity.getAngleList()));
                intent.putParcelableArrayListExtra("areas", new ArrayList<Parcelable>(myActivity.getAreaList()));
                intent.putParcelableArrayListExtra("volumes", new ArrayList<Parcelable>(myActivity.getVolumeList()));

                startActivityForResult(intent, elementType);
            }
        }
    }
}