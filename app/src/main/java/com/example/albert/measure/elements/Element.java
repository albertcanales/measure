package com.example.albert.measure.elements;

import android.widget.EditText;

import java.util.List;

public class Element {

    public static final int VALID_NAME = 0;
    private static final int EMPTY_NAME = 1;
    private static final int NOT_ALPHANUMERICAL = 2;
    private static final int NAME_ALREADY_EXISTS = 3;
    String name;
    public Element() {
    }
    public Element(String name) {
        this.name = name;
    }

    public static int validName(String name, List<Point> points, List<Angle> angles,
                                List<Vector> vectors, List<Area> areas) {
        if (name.isEmpty()) return EMPTY_NAME;
        else if (!name.matches("[A-Za-z0-9]+")) return NOT_ALPHANUMERICAL;
        else if (existingName(name, points, angles, vectors, areas)) return NAME_ALREADY_EXISTS;
        else return VALID_NAME;
    }

    private static boolean existingName(String name, List<Point> points, List<Angle> angles,
                                        List<Vector> vectors, List<Area> areas) {
        for (Point p : points) if (name.equals(p.getName())) return true;
        for (Angle a : angles) if (name.equals(a.getName())) return true;
        for (Vector v : vectors) if (name.equals(v.getName())) return true;
        for (Area a : areas) if (name.equals(a.getName())) return true;
        return false;
    }

    public static int validNameFromEditText(EditText editText, List<Point> points, List<Angle> angles,
                                            List<Vector> vectors, List<Area> areas) {
        String name = editText.getText().toString().trim();
        int exitCode = validName(name, points, angles, vectors, areas);
        if (exitCode == EMPTY_NAME)
            editText.setError("Cannot be empty");
        else if (exitCode == NOT_ALPHANUMERICAL)
            editText.setError("Can only contain alphanumeric characters");
        else if (exitCode == NAME_ALREADY_EXISTS)
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
