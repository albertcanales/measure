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
import com.example.albert.measure.elements.Point;

import java.util.List;

public class PointsAdapter extends ElementsAdapter {

    private ViewHolder holder;

    PointsAdapter(List<Point> points, Context context) {
        super(new ListPointRef(points), context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_points, parent, false);
        holder = new ViewHolder(v);
        return holder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    void onBindChildrenViewHolder(ElementsAdapter.ViewHolder holder, int position) {
        this.holder.textX.setText(String.format("x\n%.1f", pointList.get(position).getX()));
        this.holder.textY.setText(String.format("y\n%.1f", pointList.get(position).getY()));
        this.holder.textZ.setText(String.format("z\n%.1f", pointList.get(position).getZ()));
    }

    @Override
    public int getItemCount() {
        return pointList.size();
    }

    @Override
    void removeItem(int position) {
        pointList.remove(position);
        ((ResultsActivity) context).setPointList(pointList);
        ((ResultsActivity) context).refreshAdapter(0);
    }

    @Override
    String getItemName(int position) {
        return pointList.get(position).getName();
    }

    @Override
    void renameItem(int position, String name) {
        pointList.get(position).setName(name);
        ((ResultsActivity) context).setPointList(pointList);
        ((ResultsActivity) context).refreshAdapter(0);
    }

    static class ViewHolder extends ElementsAdapter.ViewHolder {
        TextView textX, textY, textZ;
        ViewHolder(View v) {
            super(v);
            textX = v.findViewById(R.id.angleX);
            textY = v.findViewById(R.id.angleY);
            textZ = v.findViewById(R.id.angleZ);
        }
    }
}
