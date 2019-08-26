package com.example.albert.measure.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.albert.measure.R;
import com.example.albert.measure.activities.ResultsActivity;
import com.example.albert.measure.elements.Point;

import java.util.List;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder> {

    private List<Point> pointList;
    private Context context;

    public PointsAdapter(List<Point> points, Context context) {
        this.pointList = points;
        this.context = context;
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

        holder.rename.setOnClickListener(new onClickListeners(position));
        holder.remove.setOnClickListener(new onClickListeners(position));
    }

    @Override
    public int getItemCount() {
        return pointList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, textX, textY, textZ;
        private TextView rename, remove;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.angleName);
            textX = v.findViewById(R.id.angleX);
            textY = v.findViewById(R.id.angleY);
            textZ = v.findViewById(R.id.angleZ);
            rename = v.findViewById(R.id.rename);
            remove = v.findViewById(R.id.remove);
        }
    }

    private class onClickListeners implements View.OnClickListener {
        int position;

        private onClickListeners(int position) {
            this.position = position;
        }


        @Override
        public void onClick(final View view) {
            if(view.getId() == R.id.remove) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Delete the point permanently?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // TODO Remove only if not used
                                pointList.remove(position);
                                ((ResultsActivity) context).setPointList(pointList);
                                ((ResultsActivity) context).refreshAdapter();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final LayoutInflater inflater = ((ResultsActivity)context).getLayoutInflater();
                final View inflator = inflater.inflate(R.layout.dialog_rename, null);
                ((EditText) inflator.findViewById(R.id.rename_edittext)).setText(pointList.get(position).getName());
                builder.setView(inflator);
                builder.setTitle("Rename to...")
                        .setPositiveButton("OK", null)
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EditText nameET = inflator.findViewById(R.id.rename_edittext);
                                String newName = nameET.getText().toString().trim();
                                if (newName.isEmpty()) nameET.setError("Cannot be empty");
                                else if(newName.matches("[A-Za-z0-9]+")) {
                                    // TODO Check if it already exists
                                    pointList.get(position).setName(newName);
                                    ((ResultsActivity) context).setPointList(pointList);
                                    ((ResultsActivity) context).refreshAdapter();
                                    dialog.dismiss();
                                } else {
                                    nameET.setError("Can only contain alphanumeric values");
                                }
                            }
                        });
                    }
                });
                dialog.show();
            }
        }
    }

}
