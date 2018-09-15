package com.example.albert.measure;

public class TriangleUtils {
//    private float alpha;
//    private float beta;
//    private float gamma;
//    private float a;
//    private float b;
//    private float c;


    public static double getDistanceFromAngle(double pitch, double heigth, boolean up) {
        int offset = up ? 0 : 90;
        return (heigth * Math.tan(Math.toRadians(pitch + offset)));

    }
}
