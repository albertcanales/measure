package com.example.albert.measure.elements;

import android.os.Parcel;
import android.os.Parcelable;

public class Volume extends Element implements Parcelable {

    public static final int TYPE_PYRAMID = 0;
    public static final int TYPE_PRISM = 1;

    private Area base;
    private Point pointHeight;
    private int volumeType;

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

    // Empty constructors
    public Volume() {
        super("");
        base = new Area();
        pointHeight = new Point();
        volumeType = TYPE_PYRAMID;
    }

    public Volume(String name) {
        super(name);
        base = new Area();
        pointHeight = new Point();
        volumeType = TYPE_PYRAMID;
    }

    // Default constructors
    public Volume(Area base, Point pointHeight, int volumeType) {
        super("");
        this.base = base;
        this.pointHeight = pointHeight;
        this.volumeType = volumeType;
    }

    public Volume(String name, Area base, Point pointHeight, int volumeType) {
        super(name);
        this.base = base;
        this.pointHeight = pointHeight;
        this.volumeType = volumeType;
    }

    // Parcelable constructor
    private Volume(Parcel in) {
        name = in.readString();
        base = in.readParcelable(Area.class.getClassLoader());
        pointHeight = in.readParcelable(Point.class.getClassLoader());
        volumeType = in.readInt();
    }

    public double getHeight() {
        return Vector.getNorm((new Vector(pointHeight,base.getVertex())).
                multiplyByScalar(base.getV().dot(base.getU()))) / Math.abs(base.getV().dot(base.getU()));
    }

    public double getVolume() {
        if(volumeType == TYPE_PYRAMID)
            return base.getArea() * getHeight() / 3;
        return base.getArea() * getHeight();
    }

    public Area getBase() {
        return base;
    }

    public Point getPointHeight() {
        return pointHeight;
    }

    public int getVolumeType() {
        return volumeType;
    }

    @Override
    public String toString() {
        return "Volume{" +
                "name='" + name + '\'' +
                ", base=" + base +
                ", pointHeight=" + pointHeight +
                ", volumeType=" + volumeType +
                ", height=" + getHeight() +
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
        parcel.writeParcelable(base, i);
        parcel.writeParcelable(pointHeight, i);
        parcel.writeInt(volumeType);
    }
}
