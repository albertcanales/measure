package com.example.albert.measure.elements;

import android.os.Parcel;
import android.support.annotation.NonNull;

public class Vector extends Element {

    public static final Creator<Vector> CREATOR = new Creator<Vector>() {
        @Override
        public Vector createFromParcel(Parcel in) {
            return new Vector(in);
        }

        @Override
        public Vector[] newArray(int size) {
            return new Vector[size];
        }
    };

    private final Point A;
    private final Point B;

    public Vector() {
        super();
        A = B = new Point();
    }

    public Vector(Point a, Point b) {
        super();
        this.A = a;
        this.B = b;
    }

    public Vector(String name, Point a, Point b) {
        super(name);
        this.A = a;
        this.B = b;
    }

    // Parcelable constructor
    private Vector(Parcel in) {
        name = in.readString();
        A = in.readParcelable(getClass().getClassLoader());
        B = in.readParcelable(getClass().getClassLoader());
    }

    // Mathematical vector methods, used in calculations on Volume Class
    static double getVectorNorm(double[] vector) {
        return (new Vector(new Point(), new Point(vector[0], vector[1], vector[2]))).getDistance();
    }

    Vector projectionX() {
        return new Vector(name, new Point(A.getName(), 0, A.getY(), A.getZ()),
                new Point(B.getName(), 0, B.getY(), B.getZ()));
    }

    Vector projectionY() {
        return new Vector(name, new Point(A.getName(), A.getX(), 0, A.getZ()),
                new Point(B.getName(), B.getX(), 0, B.getZ()));
    }

    Vector projectionZ() {
        return new Vector(name, new Point(A.getName(), A.getX(), A.getY(), 0),
                new Point(B.getName(), B.getX(), B.getY(), 0));
    }

    double dot(Vector v) {
        return v.vectorX() * vectorX() +
                v.vectorY() * vectorY() + v.vectorZ() * vectorZ();
    }

    // Needed for dot product, before called getVector
    private double vectorX() {
        return B.getX() - A.getX();
    }

    private double vectorY() {
        return B.getY() - A.getY();
    }

    private double vectorZ() {
        return B.getZ() - A.getZ();
    }

    public double getDistanceX() {
        return Math.abs(vectorX());
    }

    public double getDistanceY() {
        return Math.abs(vectorY());
    }

    public double getDistanceZ() {
        return Math.abs(vectorZ());
    }

    public double getDistance() {
        return Math.sqrt(Math.pow(getDistanceX(), 2) +
                Math.pow(getDistanceY(), 2) + Math.pow(getDistanceZ(), 2));
    }

    double[] VectorByScalar(double scalar) {
        double[] myVector = new double[]{vectorX(), vectorY(), vectorZ()};
        for (int i = 0; i < myVector.length; i++) myVector[i] *= scalar;
        return myVector;
    }

    public Point getA() {
        return A;
    }

    @NonNull
    @Override
    public String toString() {
        return "Vector{" +
                "name='" + name + '\'' +
                ", A=" + A +
                ", B=" + B +
                ", distance=" + getDistance() +
                ", distanceX=" + getDistanceX() +
                ", distanceY=" + getDistanceY() +
                ", distanceZ=" + getDistanceZ() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeParcelable(A, i);
        parcel.writeParcelable(B, i);
    }
}
