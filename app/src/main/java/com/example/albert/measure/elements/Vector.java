package com.example.albert.measure.elements;

public class Vector {

    private String name;
    private Point first;
    private Point second;

    public Vector() {
        name = "";
        first = new Point();
        second = new Point();
    }

    public Vector(Point first, Point second) {
        name = "";
        this.first = first;
        this.second = second;
    }

    public Vector(String name, Point first, Point second) {
        this.name = name;
        this.first = first;
        this.second = second;
    }

    public Vector projectionX () {
        return new Vector(name, new Point(first.getName(), 0, first.getY(), first.getZ()),
                new Point(second.getName(), 0, second.getY(), second.getZ()));
    }

    public Vector projectionY () {
        return new Vector(name, new Point(first.getName(), first.getX(), 0, first.getZ()),
                new Point(second.getName(), second.getX(), 0, second.getZ()));
    }

    public Vector projectionZ () {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
