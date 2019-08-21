package com.example.albert.measure.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.example.albert.measure.activities.DistanceActivity;
import com.example.albert.measure.activities.DistanceParametersActivity;

import java.util.Objects;

public class ResultsDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DistanceActivity distance = new DistanceActivity();
        double result = Math.round(distance.getResult()*100.0)/100.0;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Result: " + result + " cm")
                .setPositiveButton("Home", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Objects.requireNonNull(getActivity()).finish();
                    }
                })
                .setNegativeButton("Recalculate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getActivity(), DistanceParametersActivity.class));
                        Objects.requireNonNull(getActivity()).finish();
                    }
                });

        return builder.create();
    }
}