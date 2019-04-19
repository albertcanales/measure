package com.example.albert.measure.ParameterTypes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albert.measure.fragments.TwoOptionParameterFragment;

public class HeightMode extends TwoOptionParameterFragment {

    public HeightMode() {
        super("AUTOMATIC", "MANUAL");
    }

    @Override
    public void onClick(View view) {
        if (view == first)
            passData("AUTOMATIC");
        else
            passData("MANUAL");
    }

    public void passData(String data) {
        dataPasser.onDataPass(data);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
