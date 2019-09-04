package com.example.albert.measure.elements;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import com.example.albert.measure.Triplet;

import javax.xml.transform.dom.DOMLocator;

public class Vector extends Element implements Parcelable {

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
    private Point first;
    private Point second;

    public Vector() {
        super("");
        first = new Point();
        second = new Point();
    }

    public Vector(Point first, Point second) {
        super("");
        this.first = first;
        this.second = second;
    }

    public Vector(String name, Point first, Point second) {
        super(name);
        this.first = first;
        this.second = second;
    }

    // Parcelable constructor
    public Vector(Parcel in) {
        name = in.readString();
        first = in.readParcelable(getClass().getClassLoader());
        second = in.readParcelable(getClass().getClassLoader());
    }

    public Vector projectionX() {
        return new Vector(name, new Point(first.getName(), 0, first.getY(), first.getZ()),
                new Point(second.getName(), 0, second.getY(), second.getZ()));
    }

    public Vector projectionY() {
        return new Vector(name, new Point(first.getName(), first.getX(), 0, first.getZ()),
                new Point(second.getName(), second.getX(), 0, second.getZ()));
    }

    public Vector projectionZ() {
        return new Vector(name, new Point(first.getName(), first.getX(), first.getY(), 0),
                new Point(second.getName(), second.getX(), second.getY(), 0));
    }

    public double dot(Vector v) {
        return v.signedNormX() * signedNormX() +
                v.signedNormY() * signedNormY() + v.signedNormZ() * signedNormZ();
    }

    // Needed for dot product, before called getVector
    private double signedNormX() {
        return getSecond().getX() - getFirst().getX();
    }

    private double signedNormY() {
        return getSecond().getY() - getFirst().getY();
    }

    private double signedNormZ() {
        return getSecond().getZ() - getFirst().getZ();
    }

    public double getDistanceX() {
        return Math.abs(signedNormX());
    }

    public double getDistanceY() {
        return Math.abs(signedNormY());
    }

    public double getDistanceZ() {
        return Math.abs(signedNormZ());
    }

    public double getDistance() {
        return Math.sqrt(Math.pow(getDistanceX(), 2) +
                Math.pow(getDistanceY(), 2) + Math.pow(getDistanceZ(), 2));
    }

    public Point getFirst() {
        return first;
    }

    public void setFirst(Point first) {
        this.first = first;
    }

    public Point getSecond() {
        return second;
    }

    public void setSecond(Point second) {
        this.second = second;
    }

    // Pure vector methods, used in calculations on Volume Class
    public Triplet<Double, Double, Double> getVector() {
        return new Triplet<>(second.getX() - first.getX(), second.getY() - second.getY(), second.getZ() - first.getZ());
    }

    public Triplet<Double, Double, Double> multiplyByScalar(double scalar) {
        Triplet<Double, Double, Double> myVector = getVector();
        return new Triplet<>(myVector.getFirst()*scalar, myVector.getSecond()*scalar,
                myVector.getThird()*scalar);
    }

    public static double getNorm(Triplet<Double,Double,Double> triplet) {
        return (new Vector(new Point(), new Point(triplet.getFirst(), triplet.getSecond(), triplet.getThird()))).getDistance();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeParcelable(first, i);
        parcel.writeParcelable(second, i);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "name='" + name + '\'' +
                ", first=" + first +
                ", second=" + second +
                ", distance=" + getDistance() +
                ", distanceX=" + getDistanceX() +
                ", distanceY=" + getDistanceY() +
                ", distanceZ=" + getDistanceZ() +
                '}';
    }
}
