package com.example.albert.measure.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.Point;

import java.util.List;

public class PointsAdapter extends ElementsAdapter {

    PointsAdapter(List<Point> points, Context context) {
        super(new ListPointRef(points), context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_points, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("DefaultLocale")
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        double x = pointList.get(position).getX();
        double y = pointList.get(position).getY();
        double z = pointList.get(position).getZ();
        holder.textX.setText("x\n".concat(String.format("%.1f", x)));
        holder.textY.setText("y\n".concat(String.format("%.1f", y)));
        holder.textZ.setText("z\n".concat(String.format("%.1f", z)));
    }

    @Override
    public int getItemCount() {
        return pointList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, rename, remove;
        private TextView textX, textY, textZ;
        ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.angleName);
            textX = v.findViewById(R.id.angleX);
            textY = v.findViewById(R.id.angleY);
            textZ = v.findViewById(R.id.angleZ);
            rename = v.findViewById(R.id.rename);
            remove = v.findViewById(R.id.remove);
        }
    }
}
