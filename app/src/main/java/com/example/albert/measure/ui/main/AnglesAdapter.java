package com.example.albert.measure.ui.main;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.Angle;

import java.util.List;

public class AnglesAdapter extends RecyclerView.Adapter<AnglesAdapter.ViewHolder> {

    private List<Angle> angleList;

    public AnglesAdapter(List<Angle> angles) {
        this.angleList = angles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_angles, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = angleList.get(position).getName();
        holder.name.setText(name);
        double angle = angleList.get(position).getAngle();
        holder.textAngle.setText(String.format("%.0f", Math.toDegrees(angle)).concat("°"));
        double x = angleList.get(position).getAngleX();
        if(Double.isNaN(x))
            holder.textX.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0f));
        else
            holder.textX.setText("x\n".concat(String.format("%.0f", Math.toDegrees(x))).concat("°"));
        double y = angleList.get(position).getAngleY();
        if(Double.isNaN(y))
            holder.textY.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0f));
        else
            holder.textY.setText("y\n".concat(String.format("%.0f", Math.toDegrees(y))).concat("°"));
        double z = angleList.get(position).getAngleZ();
        if(Double.isNaN(z))
            holder.textZ.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0f));
        else
            holder.textZ.setText("z\n".concat(String.format("%.0f", z, Math.toDegrees(z))).concat("°"));
    }

    @Override
    public int getItemCount() {
        return angleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, textAngle, textX, textY, textZ;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.angleName);
            textAngle = v.findViewById(R.id.angle);
            textX = v.findViewById(R.id.angleX);
            textY = v.findViewById(R.id.angleY);
            textZ = v.findViewById(R.id.angleZ);
        }
    }
}
