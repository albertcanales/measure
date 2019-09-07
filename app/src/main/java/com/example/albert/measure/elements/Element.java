package com.example.albert.measure.elements;

import android.os.Parcelable;
import android.widget.EditText;

import java.util.List;

public abstract class Element implements Parcelable {

    public static final int VALID = 0;
    private static final int EMPTY = 1;
    private static final int NOT_ALPHANUMERICAL = 2;
    private static final int ALREADY_EXISTS = 3;

    String name;

    Element() {
        name = "";
    }

    Element(String name) {
        this.name = name;
    }

    private static int validName(String name, List<Point> points, List<Angle> angles,
                                 List<Vector> vectors, List<Area> areas, List<Volume> volumes) {
        if (name.isEmpty()) return EMPTY;
        else if (!name.matches("[A-Za-z0-9]+")) return NOT_ALPHANUMERICAL;
        else if (existingName(name, points, angles, vectors, areas, volumes)) return ALREADY_EXISTS;
        else return VALID;
    }

    private static boolean existingName(String name, List<Point> points, List<Angle> angles,
                                        List<Vector> vectors, List<Area> areas, List<Volume> volumes) {
        for (Point p : points) if (name.equals(p.getName())) return true;
        for (Angle a : angles) if (name.equals(a.getName())) return true;
        for (Vector v : vectors) if (name.equals(v.getName())) return true;
        for (Area a : areas) if (name.equals(a.getName())) return true;
        for (Volume v : volumes) if (name.equals(v.getName())) return true;
        return false;
    }

    public static int validNameOfET(EditText editText, List<Point> points, List<Angle> angles,
                                    List<Vector> vectors, List<Area> areas, List<Volume> volumes) {
        String name = editText.getText().toString().trim();
        int exitCode = validName(name, points, angles, vectors, areas, volumes);
        if (exitCode == EMPTY)
            editText.setError("Cannot be empty");
        else if (exitCode == NOT_ALPHANUMERICAL)
            editText.setError("Can only contain alphanumeric characters");
        else if (exitCode == ALREADY_EXISTS)
            editText.setError("Must be unique");
        return exitCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
