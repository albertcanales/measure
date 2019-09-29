package com.example.albert.measure.ui.results;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albert.measure.R;
import com.example.albert.measure.activities.ResultsActivity;
import com.example.albert.measure.elements.ElementsLists;
import com.example.albert.measure.elements.Point;

public class PointsAdapter extends ElementsAdapter {

    private ViewHolder holder;

    PointsAdapter(ElementsLists elements, Context context) {
        super(elements, context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_points, parent, false);
        holder = new ViewHolder(v);
        return holder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    void onBindChildrenViewHolder(ElementsAdapter.ViewHolder holder, int position) {
        Point p = elements.getPointList().get(position);
        this.holder.textX.setText(String.format("x\n%.1f", adjustToUnit(p.getX(),1)));
        this.holder.textY.setText(String.format("y\n%.1f", adjustToUnit(p.getY(),1)));
        this.holder.textZ.setText(String.format("z\n%.1f", adjustToUnit(p.getZ(),1)));
    }

    @Override
    public int getItemCount() {
        return elements.getPointList().size();
    }

    @Override
    void removeItem(int position) {
        elements.getPointList().remove(position);
        ((ResultsActivity) context).setElements(elements);
        ((ResultsActivity) context).refreshAdapter(SectionsPagerAdapter.POINT_TAB);
    }

    @Override
    String getItemName(int position) {
        return elements.getPointList().get(position).getName();
    }

    @Override
    void renameItem(int position, String name) {
        elements.getPointList().get(position).setName(name);
        ((ResultsActivity) context).setElements(elements);
        ((ResultsActivity) context).refreshAdapter(SectionsPagerAdapter.POINT_TAB);
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
