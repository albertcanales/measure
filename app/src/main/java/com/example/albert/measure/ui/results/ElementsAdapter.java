package com.example.albert.measure.ui.results;

import android.annotation.SuppressLint;
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
import com.example.albert.measure.elements.ElementsLists;

public abstract class ElementsAdapter extends RecyclerView.Adapter<ElementsAdapter.ViewHolder> {

    final Context context;
    final ElementsLists elements;

    ElementsAdapter(ElementsLists elements, Context context) {
        this.elements = elements;
        this.context = context;
    }

    @NonNull
    @Override
    public abstract ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i);

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        onBindChildrenViewHolder(position);
        holder.rename.setOnClickListener(new ModifyElement(position));
        holder.remove.setOnClickListener(new ModifyElement(position));
        holder.name.setText(getItemName(position));
    }

    abstract void onBindChildrenViewHolder(int position);

    @Override
    public abstract int getItemCount();

    abstract void removeItem(int position);

    abstract String getItemName(int position);

    abstract void renameItem(int position, String name);

    double adjustToUnit(double value, int multiplier) {
        return value * 1 / Math.pow(10,
                (ResultsActivity.UNITS.indexOf(((ResultsActivity) context).getActualUnit()) - 1) * multiplier);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name, rename, remove;

        ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            rename = v.findViewById(R.id.rename);
            remove = v.findViewById(R.id.remove);
        }
    }

    class ModifyElement implements View.OnClickListener {

        final int position;

        ModifyElement(int position) {
            this.position = position;
        }

        @Override
        public void onClick(final View view) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            AlertDialog dialog;
            if (view.getId() == R.id.remove) {
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
                final LayoutInflater inflater = ((ResultsActivity) context).getLayoutInflater();
                @SuppressLint("InflateParams") final View myView = inflater.inflate(R.layout.dialog_rename, null);
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
                                if (getItemName(position).equals(name)) dialog.dismiss();
                                if (elements.validEditText(nameET) == ElementsLists.VALID) {
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
}
