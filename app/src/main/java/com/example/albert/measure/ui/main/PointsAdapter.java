package com.example.albert.measure.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albert.measure.R;
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
        double x = pointList.get(position).getX();
        double y = pointList.get(position).getY();
        double z = pointList.get(position).getZ();
        this.holder.textX.setText("x\n".concat(String.format("%.1f", x)));
        this.holder.textY.setText("y\n".concat(String.format("%.1f", y)));
        this.holder.textZ.setText("z\n".concat(String.format("%.1f", z)));
    }

    @Override
    public int getItemCount() {
        return pointList.size();
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
