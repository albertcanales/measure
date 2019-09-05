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

public class VolumesAdapter extends ElementsAdapter {

    private ViewHolder holder;

    VolumesAdapter(List<Point> points, List<Angle> angles, List<Vector> vectors, List<Area> areas, List<Volume> volumes, Context context) {
        super(points, angles, vectors, areas, new ListVolumeRef(volumes), context);
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
        double volume = volumeList.get(position).getVolume();
        double base = volumeList.get(position).getBase().getArea();
        double height = volumeList.get(position).getHeight();
        this.holder.textVolume.setText(String.format("%.1f  cm³", volume));
        this.holder.base.setText(String.format("Base\n%.1f cm²", base));
        this.holder.height.setText(String.format("Height\n%.1f cm", height));
    }

    @Override
    public int getItemCount() {
        return volumeList.size();
    }

    @Override
    void removeItem(int position) {
        volumeList.remove(position);
        ((ResultsActivity) context).setVolumeList(volumeList);
        ((ResultsActivity) context).refreshAdapter(SectionsPagerAdapter.VOLUME_TAB);
    }

    @Override
    String getItemName(int position) {
        return volumeList.get(position).getName();
    }

    @Override
    void renameItem(int position, String name) {
        volumeList.get(position).setName(name);
        ((ResultsActivity) context).setVolumeList(volumeList);
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

