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
import com.example.albert.measure.elements.Volume;

import java.util.Locale;

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

    @Override
    void onBindChildrenViewHolder(int position) {
        Volume v = elements.getVolumeList().get(position);
        final String unit = ((ResultsActivity) context).getActualUnit();
        double volume = v.getVolume();
        double base = v.getBase().getArea();
        this.holder.textVolume.setText(String.format(Locale.getDefault(), "%.1f %s³", adjustToUnit(volume, 3), unit));
        this.holder.base.setText(String.format(Locale.getDefault(), "Base\n%.1f %s²", adjustToUnit(base, 2), unit));
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
        final TextView textVolume, base;

        ViewHolder(View v) {
            super(v);
            textVolume = v.findViewById(R.id.volume);
            base = v.findViewById(R.id.base);
        }
    }
}

