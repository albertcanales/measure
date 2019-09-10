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
import com.example.albert.measure.elements.Vector;

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

    @SuppressLint("DefaultLocale")
    @Override
    void onBindChildrenViewHolder(ElementsAdapter.ViewHolder holder, int position) {
        Vector v = elements.getVectorList().get(position);
        this.holder.textDistance.setText(String.format("%.1f", v.getDistance()));
        this.holder.textX.setText(String.format("x\n%.1f", v.getDistanceX()));
        this.holder.textY.setText(String.format("y\n%.1f", v.getDistanceY()));
        this.holder.textZ.setText(String.format("z\n%.1f", v.getDistanceZ()));
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

