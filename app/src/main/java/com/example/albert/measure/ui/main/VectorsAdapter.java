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

import java.util.List;

public class VectorsAdapter extends ElementsAdapter {

    private ViewHolder holder;

    VectorsAdapter(List<Point> points, List<Angle> angles, List<Vector> vectors, List<Area> areas, Context context) {
        super(points, angles, new ListVectorRef(vectors), areas, context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_vectors, parent, false);
        holder = new ViewHolder(v);
        return holder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    void onBindChildrenViewHolder(ElementsAdapter.ViewHolder holder, int position) {
        this.holder.textDistance.setText(String.format("%.1f", vectorList.get(position).getDistance()));
        this.holder.textX.setText(String.format("x\n%.1f", vectorList.get(position).getDistanceX()));
        this.holder.textY.setText(String.format("y\n%.1f", vectorList.get(position).getDistanceY()));
        this.holder.textZ.setText(String.format("z\n%.1f", vectorList.get(position).getDistanceZ()));
    }

    @Override
    public int getItemCount() {
        return vectorList.size();
    }

    @Override
    void removeItem(int position) {
        vectorList.remove(position);
        ((ResultsActivity) context).setVectorList(vectorList);
        ((ResultsActivity) context).refreshAdapter(2);
    }

    @Override
    String getItemName(int position) {
        return vectorList.get(position).getName();
    }

    @Override
    void renameItem(int position, String name) {
        vectorList.get(position).setName(name);
        ((ResultsActivity) context).setVectorList(vectorList);
        ((ResultsActivity) context).refreshAdapter(2);
    }


    public static class ViewHolder extends ElementsAdapter.ViewHolder {
        TextView textDistance, textX, textY, textZ;

        ViewHolder(View v) {
            super(v);
            textDistance = v.findViewById(R.id.distance);
            textX = v.findViewById(R.id.distanceX);
            textY = v.findViewById(R.id.distanceY);
            textZ = v.findViewById(R.id.distanceZ);
        }
    }
}

