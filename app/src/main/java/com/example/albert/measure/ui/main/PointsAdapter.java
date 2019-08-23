package com.example.albert.measure.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.Point;

import java.util.List;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder> {

    private List<Point> pointList;

    public PointsAdapter(List<Point> points) {
        this.pointList = points;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_points, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = pointList.get(position).getName();
        holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return pointList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.pointName);
        }
    }

}
