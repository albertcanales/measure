package com.example.albert.measure.elements;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ElementsLists implements Parcelable {

    public static final int VALID = 0;
    public static final Creator<ElementsLists> CREATOR = new Creator<ElementsLists>() {
        @Override
        public ElementsLists createFromParcel(Parcel in) {
            return new ElementsLists(in);
        }

        @Override
        public ElementsLists[] newArray(int size) {
            return new ElementsLists[size];
        }
    };
    private static final int EMPTY = 1;
    private static final int NOT_ALPHANUMERICAL = 2;
    private static final int ALREADY_EXISTS = 3;

    private final List<Point> pointList;
    private final List<Angle> angleList;
    private final List<Vector> vectorList;
    private final List<Area> areaList;
    private final List<Volume> volumeList;

    public ElementsLists(List<Point> pointList) {
        this.pointList = pointList;
        angleList = new ArrayList<>();
        vectorList = new ArrayList<>();
        areaList = new ArrayList<>();
        volumeList = new ArrayList<>();
    }

    private ElementsLists(Parcel in) {
        pointList = in.createTypedArrayList(Point.CREATOR);
        angleList = in.createTypedArrayList(Angle.CREATOR);
        vectorList = in.createTypedArrayList(Vector.CREATOR);
        areaList = in.createTypedArrayList(Area.CREATOR);
        volumeList = in.createTypedArrayList(Volume.CREATOR);
    }

    private boolean existingName(String name) {
        for (String m : getPointNames()) if (name.equals(m)) return true;
        for (String m : getAngleNames()) if (name.equals(m)) return true;
        for (String m : getVectorNames()) if (name.equals(m)) return true;
        for (String m : getAreaNames()) if (name.equals(m)) return true;
        for (String m : getVolumeNames()) if (name.equals(m)) return true;
        return false;
    }

    private int validName(String name) {
        if (name.isEmpty()) return EMPTY;
        else if (!name.matches("[A-Za-z0-9]+")) return NOT_ALPHANUMERICAL;
        else if (existingName(name)) return ALREADY_EXISTS;
        else return VALID;
    }

    public int validEditText(EditText editText) {
        String name = editText.getText().toString().trim();
        int exitCode = validName(name);
        if (exitCode == EMPTY)
            editText.setError("Cannot be empty");
        else if (exitCode == NOT_ALPHANUMERICAL)
            editText.setError("Can only contain alphanumeric characters");
        else if (exitCode == ALREADY_EXISTS)
            editText.setError("Must be unique");
        return exitCode;
    }

    public List<String> getPointNames() {
        List<String> names = new ArrayList<>();
        for (Point p : pointList) names.add(p.getName());
        return names;
    }

    private List<String> getAngleNames() {
        List<String> names = new ArrayList<>();
        for (Angle a : angleList) names.add(a.getName());
        return names;
    }

    private List<String> getVectorNames() {
        List<String> names = new ArrayList<>();
        for (Vector v : vectorList) names.add(v.getName());
        return names;
    }

    public List<String> getAreaNames() {
        List<String> names = new ArrayList<>();
        for (Area a : areaList) names.add(a.getName());
        return names;
    }

    private List<String> getVolumeNames() {
        List<String> names = new ArrayList<>();
        for (Volume v : volumeList) names.add(v.getName());
        return names;
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public List<Angle> getAngleList() {
        return angleList;
    }

    public List<Vector> getVectorList() {
        return vectorList;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public List<Volume> getVolumeList() {
        return volumeList;
    }

    @NonNull
    @Override
    public String toString() {
        return "ElementsLists{" +
                "pointList=" + pointList +
                ", angleList=" + angleList +
                ", vectorList=" + vectorList +
                ", areaList=" + areaList +
                ", volumeList=" + volumeList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(pointList);
        parcel.writeTypedList(angleList);
        parcel.writeTypedList(vectorList);
        parcel.writeTypedList(areaList);
        parcel.writeTypedList(volumeList);
    }
}
