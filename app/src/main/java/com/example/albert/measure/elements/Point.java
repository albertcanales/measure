package com.example.albert.measure.elements;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.example.albert.measure.DistanceUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Point extends Element implements Parcelable {

    public static final double DEFAULT_PROXIMITY_DISTANCE = 20; // TODO Implement a more flexible method
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
    private double pitch, azimuth;

    // Origin Constructor
    public Point() {
        super("Origin");
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    // Device Constructor
    public Point(double h) {
        super("Device");
        this.x = 0;
        this.y = 0;
        this.z = h;
    }

    // Manual Constructor
    public Point(String name, double x, double y, double z) {
        super(name);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Manual constructor for base points
    public Point(String name, double x, double y) {
        super(name);
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    // Constructor for base points
    public Point(String name, double h, Pair<Double, Double> angles) {
        super(name);
        z = 0;
        this.pitch = angles.first;
        this.azimuth = angles.second;
        this.y = Math.tan(pitch) * h;
        this.x = this.y * Math.tan(azimuth);
    }

    // Manual constructor for non-base points given its base point
    public Point(String name, Point p, double h) {
        super(name);
        z = h;
        this.y = p.getY();
        this.x = p.getX();
    }

    // TODO Control possible points as both azimuths must be equal or at least similar
    // Constructor for non-base points given its base point
    public Point(String name, Point p, double h, Pair<Double, Double> angles) {
        super(name);
        this.pitch = angles.first;
        this.azimuth = angles.second;
        this.y = p.getY();
        this.x = p.getX();
        this.z = (new DistanceUtils()).PVS(h, p.pitch, this.pitch);
    }

    // Parcelable constructor
    public Point(Parcel in) {
        name = in.readString();
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
        pitch = in.readDouble();
        azimuth = in.readDouble();
    }

    public List<Point> CloseBasePoints(List<Point> points, double distance) {
        List<Point> closePoints = new ArrayList<>();
        for (Point q : points) {
            if ((new Vector(this, q)).getDistance() <= distance && q.isBased())
                closePoints.add(q);
        }
        return closePoints;
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

    // TODO Calculate it if not given
    // Angle Origin -- Device -- this
    public double getPitch() {
        return pitch;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public boolean isBased() {
        return z == 0;
    }

    public boolean isDefault() {
        return getX() == 0 && getY() == 0 && getZ() == 0;
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
        parcel.writeDouble(pitch);
        parcel.writeDouble(azimuth);
    }
}
