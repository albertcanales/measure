package com.example.albert.measure.elements;

import android.os.Parcel;
import android.support.annotation.NonNull;

public class Area extends Element {

    public static final int TYPE_TRIANGLE = 0;
    public static final Creator<Area> CREATOR = new Creator<Area>() {
        @Override
        public Area createFromParcel(Parcel in) {
            return new Area(in);
        }

        @Override
        public Area[] newArray(int size) {
            return new Area[size];
        }
    };
    private final int type;
    private final Angle angle;

    // Empty constructor
    public Area() {
        super("");
        angle = new Angle();
        this.type = 0;
    }

    public Area(String name, Point b, Point c, Point a, int type) {
        super(name);
        angle = new Angle(b, c, a);
        this.type = type;
    }

    // Constructor for parcelable
    private Area(Parcel in) {
        name = in.readString();
        angle = in.readParcelable(Angle.class.getClassLoader());
        type = in.readInt();
    }

    public double getArea() {
        double angleArea = (angle.getV().getDistance() * angle.getU().getDistance() * Math.sin(angle.getAngle())) / 2;
        if (type == TYPE_TRIANGLE)
            return angleArea;
        else
            return 2 * angleArea;
    }

    public Vector getV() {
        return angle.getV();
    }

    Vector getU() {
        return angle.getU();
    }

    Point getA() {
        return angle.getA();
    }

    int getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeParcelable(angle, i);
        parcel.writeInt(type);
    }

    @NonNull
    @Override
    public String toString() {
        return "Area{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", angle=" + angle +
                ", area=" + getArea() +
                '}';
    }
}
