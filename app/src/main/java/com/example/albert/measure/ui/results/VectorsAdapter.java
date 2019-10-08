package com.example.albert.measure.ui.results;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albert.measure.R;
import com.example.albert.measure.activities.ResultsActivity;
import com.example.albert.measure.elements.ElementsLists;
import com.example.albert.measure.elements.Vector;

import java.util.Locale;

public class VectorsAdapter extends ElementsAdapter {

    private ViewHolder holder;

    VectorsAdapter(ElementsLists elements, Context context) {
        super(elements, context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_vectors, parent, false);
        holder = new ViewHolder(v);
        return holder;
    }

    @Override
    void onBindChildrenViewHolder(int position) {
        Vector v = elements.getVectorList().get(position);
        this.holder.textDistance.setText(String.format(Locale.getDefault(), "%.1f", adjustToUnit(v.getDistance(), 1)));
        this.holder.textX.setText(String.format(Locale.getDefault(), "x\n%.1f", adjustToUnit(v.getDistanceX(), 1)));
        this.holder.textY.setText(String.format(Locale.getDefault(), "y\n%.1f", adjustToUnit(v.getDistanceY(), 1)));
        this.holder.textZ.setText(String.format(Locale.getDefault(), "z\n%.1f", adjustToUnit(v.getDistanceZ(), 1)));
    }

    @Override
    public int getItemCount() {
        return elements.getVectorList().size();
    }

    @Override
    void removeItem(int position) {
        elements.getVectorList().remove(position);
        ((ResultsActivity) context).setElements(elements);
        ((ResultsActivity) context).refreshAdapter(SectionsPagerAdapter.VECTOR_TAB);
    }

    @Override
    String getItemName(int position) {
        return elements.getVectorList().get(position).getName();
    }

    @Override
    void renameItem(int position, String name) {
        elements.getVectorList().get(position).setName(name);
        ((ResultsActivity) context).setElements(elements);
        ((ResultsActivity) context).refreshAdapter(SectionsPagerAdapter.VECTOR_TAB);
    }


    public static class ViewHolder extends ElementsAdapter.ViewHolder {
        final TextView textDistance, textX, textY, textZ;

        ViewHolder(View v) {
            super(v);
            textDistance = v.findViewById(R.id.distance);
            textX = v.findViewById(R.id.distanceX);
            textY = v.findViewById(R.id.distanceY);
            textZ = v.findViewById(R.id.distanceZ);
        }
    }
}

