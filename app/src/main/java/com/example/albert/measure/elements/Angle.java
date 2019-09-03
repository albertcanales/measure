package com.example.albert.measure.elements;

import android.os.Parcel;
import android.os.Parcelable;

public class Angle extends Element implements Parcelable {

    public static final Creator<Angle> CREATOR = new Creator<Angle>() {
        @Override
        public Angle createFromParcel(Parcel in) {
            return new Angle(in);
        }

        @Override
        public Angle[] newArray(int size) {
            return new Angle[size];
        }
    };
    // Point-based definition included though vectors to avoid data repetition
    private Vector v;
    private Vector u;

    public Angle() {
        super("");
        v = new Vector();
        u = new Vector();
    }

    public Angle(Vector v, Vector u) {
        super("");
        this.v = v;
        this.u = u;
    }

    public Angle(String name, Vector v, Vector u) {
        super(name);
        this.v = v;
        this.u = u;
    }

    public Angle(Point first, Point second, Point vertex) {
        super("");
        v = new Vector(vertex, first);
        u = new Vector(vertex, second);
    }

    public Angle(String name, Point first, Point second, Point vertex) {
        super(name);
        v = new Vector(vertex, first);
        u = new Vector(vertex, second);
    }

    // TODO Finish calculations

    Angle(Parcel in) {
        name = in.readString();
        v = in.readParcelable(Vector.class.getClassLoader());
        u = in.readParcelable(Vector.class.getClassLoader());
    }
    // Considering angle of an axis the angle resulting of the projection to that axis

    public double getAngle() {
        return Math.acos(v.dot(u) / (v.getDistance() * u.getDistance()));
    }

    public double getAngleX() {
        return new Angle(v.projectionX(), u.projectionX()).getAngle();
    }

    public double getAngleY() {
        return new Angle(v.projectionY(), u.projectionY()).getAngle();
    }

    public double getAngleZ() {
        return new Angle(v.projectionZ(), u.projectionZ()).getAngle();
    }

    public Point getFirst() {
        return v.getSecond();
    }

    public void setFirst(Point first) {
        v.setSecond(first);
    }

    public Point getSecond() {
        return u.getSecond();
    }

    public void setSecond(Point second) {
        u.setSecond(second);
    }

    public Point getVertex() {
        return v.getFirst();
    }

    public void setVertex(Point vertex) {
        v.setFirst(vertex);
        u.setFirst(vertex);
    }

    public Vector getV() {
        return v;
    }

    public Vector getU() {
        return u;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeParcelable(v, i);
        parcel.writeParcelable(u, i);
    }

    @Override
    public String toString() {
        return "Angle{" +
                "name='" + name + '\'' +
                ", v=" + v +
                ", u=" + u +
                ", u=" + u +
                ", angle=" + getAngle() +
                ", angleX=" + getAngleX() +
                ", angleY=" + getAngleY() +
                ", angleZ=" + getAngleZ() +
                '}';
    }
}
