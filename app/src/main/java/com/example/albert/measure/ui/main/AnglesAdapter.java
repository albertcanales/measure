package com.example.albert.measure.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.Angle;

import java.util.List;

public class AnglesAdapter extends ElementsAdapter {

    private ViewHolder holder;

    AnglesAdapter(List<Angle> angles, Context context) {
        super(new ListAngleRef(angles), context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_angles, parent, false);
        holder = new ViewHolder(v);
        return holder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    void onBindChildrenViewHolder(ElementsAdapter.ViewHolder holder, int position) {
        double angle = angleList.get(position).getAngle();
        this.holder.textAngle.setText(String.format("%.0f", Math.toDegrees(angle)).concat("°"));
        double x = angleList.get(position).getAngleX();
        if(Double.isNaN(x))
            this.holder.textX.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0f));
        else
            this.holder.textX.setText("x\n".concat(String.format("%.0f", Math.toDegrees(x))).concat("°"));
        double y = angleList.get(position).getAngleY();
        if(Double.isNaN(y))
            this.holder.textY.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0f));
        else
            this.holder.textY.setText("y\n".concat(String.format("%.0f", Math.toDegrees(y))).concat("°"));
        double z = angleList.get(position).getAngleZ();
        if(Double.isNaN(z))
            this.holder.textZ.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0f));
        else
            this.holder.textZ.setText("z\n".concat(String.format("%.0f", z, Math.toDegrees(z))).concat("°"));
    }

    @Override
    public int getItemCount() {
        return angleList.size();
    }
    public static class ViewHolder extends ElementsAdapter.ViewHolder {
        TextView textAngle, textX, textY, textZ;
        ViewHolder(View v) {
            super(v);
            textAngle = v.findViewById(R.id.angle);
            textX = v.findViewById(R.id.angleX);
            textY = v.findViewById(R.id.angleY);
            textZ = v.findViewById(R.id.angleZ);
        }
    }
}
