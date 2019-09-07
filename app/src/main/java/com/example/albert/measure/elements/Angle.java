package com.example.albert.measure.elements;

import android.os.Parcel;
import android.support.annotation.NonNull;

public class Angle extends Element {

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
    private final Vector v;
    private final Vector u;

    public Angle() {
        super();
        v = u = new Vector();
    }

    public Angle(Vector v, Vector u) {
        super();
        this.v = v;
        this.u = u;
    }

    // Angle is \angle BAC
    public Angle(Point b, Point c, Point a) {
        super();
        v = new Vector(a, b);
        u = new Vector(a, c);
    }

    public Angle(String name, Point b, Point c, Point a) {
        super(name);
        v = new Vector(a, b);
        u = new Vector(a, c);
    }

    private Angle(Parcel in) {
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

    Point getA() {
        return v.getA();
    }

    public Vector getV() {
        return v;
    }

    Vector getU() {
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

    @NonNull
    @Override
    public String toString() {
        return "Angle{" +
                "name='" + name + '\'' +
                ", v=" + v +
                ", u=" + u +
                ", angle=" + getAngle() +
                ", angleX=" + getAngleX() +
                ", angleY=" + getAngleY() +
                ", angleZ=" + getAngleZ() +
                '}';
    }
}
