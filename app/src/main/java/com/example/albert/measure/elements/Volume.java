package com.example.albert.measure.elements;

import android.os.Parcel;
import android.support.annotation.NonNull;

public class Volume extends Element {

    public static final int TYPE_PYRAMID = 0;
    public static final Creator<Volume> CREATOR = new Creator<Volume>() {
        @Override
        public Volume createFromParcel(Parcel in) {
            return new Volume(in);
        }

        @Override
        public Volume[] newArray(int size) {
            return new Volume[size];
        }
    };
    private final int type;
    private final Area base;
    private final Vector w;

    public Volume(String name, Area base, Point p, int type) {
        super(name);
        this.type = type;
        this.base = base;
        w = new Vector(base.getA(), p);
    }

    // Parcelable constructor
    private Volume(Parcel in) {
        name = in.readString();
        type = in.readInt();
        base = in.readParcelable(Area.class.getClassLoader());
        w = in.readParcelable(Vector.class.getClassLoader());
    }

    public Area getBase() {
        return base;
    }

    public double getVolume() {
        double volume = getMatrixDeterminant();
        if (type == TYPE_PYRAMID)
            volume /= 3;
        if (base.getType() == Area.TYPE_TRIANGLE)
            volume /= 2;
        return Math.abs(volume);
    }

    private double getMatrixDeterminant() {
        Vector v = base.getV();
        Vector u = base.getU();
        return v.getX() * u.getY() * w.getZ() + v.getY() * u.getZ() * w.getX() + v.getZ() * u.getX() * w.getY()
                - v.getZ() * u.getY() * w.getX() - v.getX() * u.getZ() * w.getY() - v.getY() * u.getX() * w.getZ();
    }

    @NonNull
    @Override
    public String toString() {
        return "Volume{" +
                "name='" + name + '\'' +
                ", base=" + base +
                ", w=" + w +
                ", type=" + type +
                ", volume=" + getVolume() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(type);
        parcel.writeParcelable(base, i);
        parcel.writeParcelable(w, i);
    }
}
