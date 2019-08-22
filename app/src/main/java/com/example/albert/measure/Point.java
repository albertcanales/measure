package com.example.albert.measure;

import android.support.annotation.NonNull;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Point {

    public static final double DEFAULT_PROXIMITY_DISTANCE = 20; // TODO Implement a more flexible method

    private final double x, y, z;
    private String name;
    private double pitch, azimuth;

    // Zero Constructor
    public Point() {
        this.name = "Origin";
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    // Origin Constructor
    public Point(double h) {
        this.name = "Device";
        this.x = 0;
        this.y = 0;
        this.z = h;
    }

    // Manual Constructor
    public Point(String name, double x, double y, double z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Manual constructor for base points
    public Point(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    // Constructor for base points
    public Point(String name, double h, Pair<Double, Double> angles) {
        this.name = name;
        z = 0;
        this.pitch = angles.first;
        this.azimuth = angles.second;
        this.y = Math.tan(pitch) * h;
        this.x = this.y * Math.tan(azimuth);
    }

    // Manual constructor for non-base points given its base point
    public Point(String name, Point p, double h) {
        this.name = name;
        z = h;
        this.y = p.getY();
        this.x = p.getX();
    }

    // TODO Control possible points as both azimuths must be equal or similar
    // Constructor for non-base points given its base point
    public Point(String name, Point p, double h, Pair<Double, Double> angles) {
        this.name = name;
        this.pitch = angles.first;
        this.azimuth = angles.second;
        this.y = p.getY();
        this.x = p.getX();
        this.z = (new DistanceUtils()).PVS(h, p.pitch, this.pitch);
    }

    public double DistanceTo(Point q) {
        return Math.sqrt(Math.pow(this.getX() - q.getX(), 2) + Math.pow(this.getY() - q.getY(), 2) + Math.pow(this.getZ() - q.getZ(), 2));
    }

    public List<Point> CloseBasePoints(List<Point> points, double distance) {
        List<Point> closePoints = new ArrayList<>();
        for (Point q : points) {
            if (this.DistanceTo(q) <= distance && q.isBased())
                closePoints.add(q);
        }
        return closePoints;
    }

    public String getName() {
        return name;
    }

    private double getX() {
        return x;
    }

    private double getY() {
        return y;
    }

    private double getZ() {
        return z;
    }

    // TODO Calculate it if not given
    // Angle (0,0,0) -- o -- this
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
}
