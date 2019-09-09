package com.example.albert.measure.elements;

import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Point extends Element {

    public static final Creator<Point> CREATOR = new Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel in) {
            return new Point(in);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };

    private final double x, y, z;

    // Origin Constructor
    public Point() {
        super("Origin");
        x = y = z = 0;
    }

    public Point(String name, double x, double y, double z) {
        super(name);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Constructor for base points
    public Point(String name, double h, double[] angles) {
        super(name);
        this.y = Math.tan(angles[0]) * h;
        this.x = this.y * Math.tan(angles[1]);
        z = 0;
    }

    // TODO Control possible points as both azimuths must be equal or at least similar
    // Constructor for non-base points given its base point
    public Point(String name, Point p, double h, double[] angles) {
        super(name);
        this.y = p.getY();
        this.x = p.getX();
        this.z = PVS(h, Math.atan(y / h), angles[0]);  // TODO Avoid h == 0
    }

    // Parcelable constructor
    private Point(Parcel in) {
        name = in.readString();
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
    }

    private double PVS(double h, double A, double B) {
        if (B < Math.PI / 2)
            return h * (1 - Math.tan(A) / Math.tan(B));
        return h * (Math.tan(B - Math.PI / 2) * Math.tan(A) + 1);
    }


    private List<Point> CloseBasePoints(List<Point> points, double distance) {
        List<Point> closePoints = new ArrayList<>();
        for (Point q : points) {
            if ((new Vector(this, q)).getDistance() <= distance && q.isBased())
                closePoints.add(q);
        }
        return closePoints;
    }

    // TODO Implement a more flexible method
    public List<Point> CloseBasePoints(List<Point> points) {
        double DEFAULT_PROXIMITY_DISTANCE = 20;
        return CloseBasePoints(points, DEFAULT_PROXIMITY_DISTANCE);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    private boolean isBased() {
        return z == 0;
    }

    public boolean isDefault() {
        return getX() == 0 && getY() == 0 && getZ() == 0;
    }

    public boolean equals(Point q) {
        return getX() == q.getX() && getY() == q.getY() && getZ() == q.getZ();
    }

    @NonNull
    @Override
    public String toString() {
        return "Point{" +
                "name=" + name +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(x);
        parcel.writeDouble(y);
        parcel.writeDouble(z);
    }
}
