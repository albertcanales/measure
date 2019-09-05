package com.example.albert.measure.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albert.measure.R;
import com.example.albert.measure.activities.ResultsActivity;
import com.example.albert.measure.elements.Angle;
import com.example.albert.measure.elements.Area;
import com.example.albert.measure.elements.Point;
import com.example.albert.measure.elements.Vector;
import com.example.albert.measure.elements.Volume;

import java.util.List;

public class AreasAdapter extends ElementsAdapter {

    private ViewHolder holder;

    AreasAdapter(List<Point> points, List<Angle> angles, List<Vector> vectors, List<Area> areas, List<Volume> volumes, Context context) {
        super(points, angles, vectors, new ListAreaRef(areas), volumes, context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_areas, parent, false);
        holder = new ViewHolder(v);
        return holder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    void onBindChildrenViewHolder(ElementsAdapter.ViewHolder holder, int position) {
        double area = areaList.get(position).getArea();
        this.holder.textArea.setText(String.format("%.2f", area).concat(" cm²"));
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }

    @Override
    void removeItem(int position) {
        areaList.remove(position);
        ((ResultsActivity) context).setAreaList(areaList);
        ((ResultsActivity) context).refreshAdapter(SectionsPagerAdapter.AREA_TAB);
    }

    @Override
    String getItemName(int position) {
        return areaList.get(position).getName();
    }

    @Override
    void renameItem(int position, String name) {
        areaList.get(position).setName(name);
        ((ResultsActivity) context).setAreaList(areaList);
        ((ResultsActivity) context).refreshAdapter(SectionsPagerAdapter.AREA_TAB);
    }


    public static class ViewHolder extends ElementsAdapter.ViewHolder {
        TextView textArea;

        ViewHolder(View v) {
            super(v);
            textArea = v.findViewById(R.id.area);
        }
    }
}
