package com.example.albert.measure;

import com.example.albert.measure.fragments.DistanceParametersFragment;

public class DistanceUtils {

    private DistanceParametersFragment parameters = new DistanceParametersFragment();

    public double height;

    public double getDistance(double[][] angles) {
        height = parameters.getUserHeight();
        if (parameters.getDirection().equals("PARALLEL")) {
            if (parameters.getPlane().equals("HORIZONTAL"))
                return parallelHorizontal(height, angles[0][0]+90, angles[1][0]+90, angles[2][0]+90);
            else
                return parallelVertical(height, angles[0][0]+90, angles[1][0]+90, angles[2][0]+90);
        }
        return -1;
    }

    private double PHS(double h, double A, double B) {
        return h * (Math.tan(Math.toRadians(B)) - Math.tan(Math.toRadians(A)));
    }

    private double parallelHorizontal(double h, double A, double B, double C) {
        return PHS(h - PVS(h, A, B), B, C);
    }

    private double PVS(double h, double A, double B) {
        if (Math.toDegrees(B) < 90)
            return h * (1 - Math.tan(Math.toRadians(A))/Math.tan(Math.toRadians(B)));
        return h * (Math.tan(Math.toRadians(B - 90)) * Math.tan(Math.toRadians(A)) + 1);
    }

    private double parallelVertical(double h, double A, double B, double C) {
        return PVS(h, A, C) - PVS(h, A, B);
    }
}
