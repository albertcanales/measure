package com.example.albert.measure.elements;

import android.os.Parcel;
import android.os.Parcelable;

public class Area extends Element implements Parcelable {

    public static final int TRIANGLE = 0;
    public static final int PARALLELOGRAM = 1;
    public static final int GENERAL_QUADRILATERAL = 2;
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
    private int areaType;
    private Angle angle1;
    private Angle angle2;

    // Empty constructor
    public Area() {
        super("");
        angle1 = new Angle();
        angle2 = new Angle();
        this.areaType = 0;
    }

    // Manual constructors
    public Area(Vector v, Vector u, int areaType) {
        super("");
        angle1 = new Angle(v, u);
        angle2 = new Angle();
        this.areaType = areaType;
    }

    public Area(String name, Vector v, Vector u, int areaType) {
        super(name);
        angle1 = new Angle(v, u);
        angle2 = new Angle();
        this.areaType = areaType;

    }

    // Constructors for point-defined triangles and parallelograms
    public Area(Point first, Point second, Point vertex, int areaType) {
        super("");
        angle1 = new Angle(first, second, vertex);
        angle2 = new Angle();
        this.areaType = areaType;
    }

    public Area(String name, Point first, Point second, Point vertex, int areaType) {
        super(name);
        angle1 = new Angle(first, second, vertex);
        angle2 = new Angle();
        this.areaType = areaType;
    }

    // Constructors for general-quadrilateral
    public Area(Point first, Point second, Point vertex, Point secondVertex) {
        super("");
        angle1 = new Angle(first, second, vertex);
        angle2 = new Angle(second, first, secondVertex);
        areaType = GENERAL_QUADRILATERAL;
    }

    public Area(String name, Point first, Point second, Point vertex, Point secondVertex) {
        super(name);
        angle1 = new Angle(first, second, vertex);
        angle2 = new Angle(second, first, secondVertex);
        areaType = GENERAL_QUADRILATERAL;
    }

    // Constructor for parcelable
    Area(Parcel in) {
        name = in.readString();
        angle1 = in.readParcelable(Angle.class.getClassLoader());
        angle2 = in.readParcelable(Angle.class.getClassLoader());
        areaType = in.readInt();
    }

    public double getArea() {
        if (areaType == TRIANGLE)
            return getAreaFromAngle(angle1);
        else if (areaType == PARALLELOGRAM)
            return 2 * getAreaFromAngle(angle1);
        else
            return getAreaFromAngle(angle1) + getAreaFromAngle(angle2);
    }

    private double getAreaFromAngle(Angle angle) {
        return (angle.getV().getDistance() * angle.getU().getDistance() * angle.getAngle()) / 2;
    }

    public Vector getV() {
        return angle1.getV();
    }

    public Vector getU() {
        return angle1.getU();
    }

    public Point getVertex() {
        return angle1.getVertex();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeParcelable(angle1, i);
        parcel.writeParcelable(angle2, i);
        parcel.writeInt(areaType);
    }

    @Override
    public String toString() {
        return "Area{" +
                "name='" + name + '\'' +
                ", areaType=" + areaType +
                ", angle1=" + angle1 +
                ", angle2=" + angle2 +
                ", area=" + getArea() +
                '}';
    }
}
