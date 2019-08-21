package com.example.albert.measure.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.albert.measure.R;

public abstract class TwoOptionParameterFragment extends Fragment implements View.OnClickListener {

    private final String textFirst;
    private final String textSecond;
    protected OnDataPass dataPasser;

    protected TwoOptionParameterFragment(String textFirst, String textSecond) {
        this.textFirst = textFirst;
        this.textSecond = textSecond;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two_option_parameter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button first = view.findViewById(R.id.first);
        Button second = view.findViewById(R.id.second);

        first.setText(textFirst);
        second.setText(textSecond);

        first.setOnClickListener(this);
        second.setOnClickListener(this);
    }

    @Override
    public abstract void onClick(View view);

    public interface OnDataPass {
        void onDataPass(String data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }


}
