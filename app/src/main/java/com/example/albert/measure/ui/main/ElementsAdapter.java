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
import com.example.albert.measure.elements.Element;
import com.example.albert.measure.elements.Point;
import com.example.albert.measure.elements.Vector;

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

    static class ListVectorRef {
        List<Vector> vectors;
        ListVectorRef(List<Vector> vectors) { this.vectors = vectors; }
        List<Vector> getVectors() { return vectors; }
    }

    Context context;
    List<Point> pointList;
    List<Angle> angleList;
    List<Vector> vectorList;

    private ElementsAdapter(Context context) {
        this.context = context;
    }

    ElementsAdapter(ListPointRef points, List<Angle> angles, List<Vector> vectors, Context context) {
        this(context);
        this.pointList = points.getPoints();
        this.angleList = angles;
        this.vectorList = vectors;
    }

    ElementsAdapter(List<Point> points, ListAngleRef angles, List<Vector> vectors, Context context) {
        this(context);
        this.pointList = points;
        this.angleList = angles.getAngles();
        this.vectorList = vectors;
    }

    ElementsAdapter(List<Point> points, List<Angle> angles, ListVectorRef vectors, Context context) {
        this(context);
        this.pointList = points;
        this.angleList = angles;
        this.vectorList = vectors.getVectors();
    }

    @NonNull
    @Override
    public abstract ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i);

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        onBindChildrenViewHolder(holder, position);
        holder.rename.setOnClickListener(new ModifyElement(position));
        holder.remove.setOnClickListener(new ModifyElement(position));
        holder.name.setText(getItemName(position));
    }

    abstract void onBindChildrenViewHolder(ViewHolder holder, int position);

    @Override
    public abstract int getItemCount();

    class ModifyElement implements View.OnClickListener {

        int position;

        ModifyElement(int position) {
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
                nameET.setText(getItemName(position));
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
                            String name = nameET.getText().toString().trim();
                            if(Element.validNameFromEditText(nameET, pointList, angleList, vectorList)
                                    == Element.VALID_NAME) {
                                renameItem(position, name);
                                dialog.dismiss();
                            }
                            }
                        });
                    }
                });
            }
            dialog.show();
        }
    }

    abstract void removeItem(int position);

    abstract String getItemName(int position);

    abstract void renameItem(int position, String name);

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, rename, remove;
        ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            rename = v.findViewById(R.id.rename);
            remove = v.findViewById(R.id.remove);
        }
    }
}
