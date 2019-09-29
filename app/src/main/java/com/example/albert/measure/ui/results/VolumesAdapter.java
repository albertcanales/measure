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
import com.example.albert.measure.elements.Volume;

public class VolumesAdapter extends ElementsAdapter {

    private ViewHolder holder;

    VolumesAdapter(ElementsLists elements, Context context) {
        super(elements, context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_volumes, parent, false);
        holder = new ViewHolder(v);
        return holder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    void onBindChildrenViewHolder(ElementsAdapter.ViewHolder holder, int position) {
        Volume v = elements.getVolumeList().get(position);
        final String unit = ((ResultsActivity) context).getActualUnit();
        double volume = v.getVolume();
        double base = v.getBase().getArea();
        double height = v.getHeight();
        this.holder.textVolume.setText(String.format("%.1f %s³", adjustToUnit(volume, 3), unit));
        this.holder.base.setText(String.format("Base\n%.1f %s²", adjustToUnit(base, 2), unit));
        this.holder.height.setText(String.format("Height\n%.1f %s", adjustToUnit(height, 1), unit));
    }

    @Override
    public int getItemCount() {
        return elements.getVolumeList().size();
    }

    @Override
    void removeItem(int position) {
        elements.getVolumeList().remove(position);
        ((ResultsActivity) context).setElements(elements);
        ((ResultsActivity) context).refreshAdapter(SectionsPagerAdapter.VOLUME_TAB);
    }

    @Override
    String getItemName(int position) {
        return elements.getVolumeList().get(position).getName();
    }

    @Override
    void renameItem(int position, String name) {
        elements.getVolumeList().get(position).setName(name);
        ((ResultsActivity) context).setElements(elements);
        ((ResultsActivity) context).refreshAdapter(SectionsPagerAdapter.VOLUME_TAB);
    }


    public static class ViewHolder extends ElementsAdapter.ViewHolder {
        TextView textVolume, base, height;

        ViewHolder(View v) {
            super(v);
            textVolume = v.findViewById(R.id.volume);
            base = v.findViewById(R.id.base);
            height = v.findViewById(R.id.height);
        }
    }
}

