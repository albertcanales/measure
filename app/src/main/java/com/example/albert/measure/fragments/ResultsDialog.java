package com.example.albert.measure.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.albert.measure.activities.DistanceActivity;
import com.example.albert.measure.activities.MainActivity;

public class ResultsDialog extends DialogFragment {

    DistanceActivity distance;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        distance = new DistanceActivity();
        double result = Math.round(distance.getResult()*100.0)/100.0;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Result: " + Double.toString(result) + " cm")
                .setPositiveButton("Home", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Recalculate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(), "Feature not developed, redirecting to HOME", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                });

        return builder.create();
    }
}