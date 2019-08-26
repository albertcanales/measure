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
        double x = pointList.get(position).getX();
        double y = pointList.get(position).getY();
        double z = pointList.get(position).getZ();
        holder.name.setText(name);
        holder.textX.setText("x\n".concat(String.format("%.1f", x)));
        holder.textY.setText("y\n".concat(String.format("%.1f", y)));
        holder.textZ.setText("z\n".concat(String.format("%.1f", z)));
    }

    @Override
    public int getItemCount() {
        return pointList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, textX, textY, textZ;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.angleName);
            textX = v.findViewById(R.id.angleX);
            textY = v.findViewById(R.id.angleY);
            textZ = v.findViewById(R.id.angleZ);
        }
    }

}
