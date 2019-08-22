package com.example.albert.measure.elements;

public class Angle {

    // Point-based definition included though vectors to avoid data repetition
    private String name;
    private Vector v;
    private Vector u;

    public Angle() {
        name = "";
        v = new Vector();
        u = new Vector();
    }

    public Angle(Vector v, Vector u) {
        name = "";
        this.v = v;
        this.u = u;
    }

    public Angle(String name, Vector v, Vector u) {
        this.name = name;
        this.v = v;
        this.u = u;
    }

    public Angle(Point first, Point second, Point vertex) {
        name = "";
        v = new Vector(vertex, first);
        u = new Vector(vertex, second);
    }

    public Angle(String name, Point first, Point second, Point vertex) {
        this.name = name;
        v = new Vector(vertex, first);
        u = new Vector(vertex, second);
    }

    // TODO Finish calculations
    public double getAngle() {
        return Math.acos(v.dot(u) / (v.getDistance() * u.getDistance()));
    }

    // Considering angle of an axis the angle resulting of the projection to that axis
    public double getAngleX() {
        return new Angle(v.projectionX(), u.projectionX()).getAngle();
    }

    public double getAngleY() {
        return new Angle(v.projectionY(), u.projectionY()).getAngle();
    }

    public double getAngleZ() {
        return new Angle(v.projectionZ(), u.projectionZ()).getAngle();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
