package com.example.albert.measure.elements;

import android.os.Parcelable;

public abstract class Element implements Parcelable {

    String name;

    Element() {
        name = "";
    }

    Element(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
