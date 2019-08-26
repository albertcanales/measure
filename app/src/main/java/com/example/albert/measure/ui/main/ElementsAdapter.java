package com.example.albert.measure.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
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
import com.example.albert.measure.elements.Angle;
import com.example.albert.measure.elements.Point;

import java.util.List;

public abstract class ElementsAdapter extends RecyclerView.Adapter<ElementsAdapter.ViewHolder> {

    // To avoid same erasure error, could be done with Suppliers, but API increases to 24 (7.0)
    static class ListPointRef {
        List<Point> points;
        ListPointRef(List<Point> points) { this.points = points; }
        List<Point> getPoints() { return points; }
    }

    static class ListAngleRef {
        List<Angle> angles;
        ListAngleRef(List<Angle> angles) { this.angles = angles; }
        List<Angle> getAngles() { return angles; }
    }

    Context context;
    List<Point> pointList;
    List<Angle> angleList;

    int type; // 0 Point, 1, Angle, 2 Distance, 3 Area, 4 Volume

    private ElementsAdapter(Context context) {
        this.context = context;
    }

    ElementsAdapter(ListPointRef points, Context context) {
        this(context);
        this.pointList = points.getPoints();
        this.type = 0;
    }

    ElementsAdapter(ListAngleRef angles, Context context) {
        this(context);
        this.angleList = angles.getAngles();
        this.type = 1;
    }

    @NonNull
    @Override
    public abstract ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i);

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rename.setOnClickListener(new onClickListeners(position));
        holder.remove.setOnClickListener(new onClickListeners(position));

        String name = pointList.get(position).getName();
        holder.name.setText(name);
        onBindChildrenViewHolder(holder, position);
    }

    abstract void onBindChildrenViewHolder(ViewHolder holder, int position);

    @Override
    public abstract int getItemCount();

    class onClickListeners implements View.OnClickListener {

        int position;

        onClickListeners(int position) {
            this.position = position;
        }

        @Override
        public void onClick(final View view) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            AlertDialog dialog;
            if(view.getId() == R.id.remove) {
                builder.setMessage("Delete it permanently?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // TODO Remove only if not used
                                removeItem(position);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dialog = builder.create();
            } else {
                final LayoutInflater inflater = ((ResultsActivity)context).getLayoutInflater();
                final View myView = inflater.inflate(R.layout.dialog_rename, null);
                final EditText nameET = myView.findViewById(R.id.rename_edit_text);
                nameET.setText(getDefaultText());
                builder.setView(myView);
                builder.setTitle("Rename to...")
                        .setPositiveButton("OK", null)
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dialog = builder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String newName = nameET.getText().toString().trim();
                                if (newName.isEmpty()) nameET.setError("Cannot be empty");
                                else if(newName.matches("[A-Za-z0-9]+")) {
                                    // TODO Check if it already exists
                                    renameItem(position, newName);
                                    dialog.dismiss();
                                } else {
                                    nameET.setError("Can only contain alphanumeric characters");
                                }
                            }
                        });
                    }
                });
            }
            dialog.show();
        }

        private void removeItem(int position) {
            if(type == 0) {
                pointList.remove(position);
                ((ResultsActivity) context).setPointList(pointList);
            } else if (type == 1) {

            }
            ((ResultsActivity) context).refreshAdapter();
        }

        private void renameItem(int position, String name) {
            if(type == 0) {
                pointList.get(position).setName(name);
                ((ResultsActivity) context).setPointList(pointList);
            }
            ((ResultsActivity) context).refreshAdapter();
        }

        private String getDefaultText() {
            if(type == 0)
                return pointList.get(position).getName();
            return "";
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, rename, remove;
        ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.angleName);
            rename = v.findViewById(R.id.rename);
            remove = v.findViewById(R.id.remove);
        }
    }
}
