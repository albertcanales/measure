package com.example.albert.measure;

public class DistanceUtils {

    public double getDistance(String direction, String plane, double height, double[][] angles) {
        if (direction.equals("PARALLEL")) {
            if (plane.equals("HORIZONTAL"))
                return parallelHorizontal(height, angles[0][0], angles[1][0], angles[2][0]);
            else
                return parallelVertical(height, angles[0][0], angles[1][0], angles[2][0]);
        }
        return -1;
    }

    private double PHS(double h, double A, double B) {
        return h * (Math.tan(B) - Math.tan(A));
    }

    private double parallelHorizontal(double h, double A, double B, double C) {
        return PHS(h - PVS(h, A, B), B, C);
    }

    // Public for points method
    public double PVS(double h, double A, double B) {
        if (B < Math.PI/2)
            return h * (1 - Math.tan(A)/Math.tan(B));
        return h * (Math.tan(B - Math.PI/2) * Math.tan(A) + 1);
    }

    private double parallelVertical(double h, double A, double B, double C) {
        return PVS(h, A, C) - PVS(h, A, B);
    }
}
