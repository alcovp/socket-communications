package com.alc.game.common.Data;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by alc on 28.02.2015.
 */
public class XYZ {
    public double x;
    public double y;
    public double z;
    public static final XYZ yawAxis = new XYZ(0, 1, 0);

    public XYZ(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public XYZ outerProduct(XYZ v)
    {
        return new XYZ(
            this.y * v.z - this.z * v.y,
            -(this.x * v.z - this.z * v.x),
            this.x * v.y - this.y * v.x
        );
    }

    public double scalarOfVector()
    {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }

    public XYZ normalize()
    {
        double scalar = this.scalarOfVector();
        if (scalar != 0) {
            return new XYZ(
                    this.x / scalar,
                    this.y / scalar,
                    this.z / scalar
            );
        } else {
            return this;
        }
    }

    public XYZ rotateFromAxis(double angle, XYZ axis)
    {
        double[][] matrix = new double[][] {
                new double[] { Math.cos(angle) + (1 - Math.cos(angle)) * axis.x, (1 - Math.cos(angle)) * axis.x * axis.y - Math.sin(angle) * axis.z, (1 - Math.cos(angle)) * axis.x * axis.z + Math.sin(angle) * axis.y},
                new double[] { (1 - Math.cos(angle)) * axis.y * axis.x + Math.sin(angle) * axis.z, Math.cos(angle) + (1 - Math.cos(angle)) * axis.y, (1 - Math.cos(angle)) * axis.y * axis.z - Math.sin(angle) * axis.x},
                new double[] { (1 - Math.cos(angle)) * axis.z * axis.x - Math.sin(angle) * axis.y, (1 - Math.cos(angle)) * axis.z * axis.y + Math.sin(angle) * axis.x, Math.cos(angle) + (1 - Math.cos(angle)) * axis.z}
        };

        return this.transform(matrix);
    }

    public XYZ rotateFromYaw(double angle)
    {
        return rotateFromAxis(angle, yawAxis);
    }

    public XYZ transform(double[][] matrix)
    {
        return new XYZ(
            matrix[0][0] * this.x + matrix[0][1] * this.y + matrix[0][2] * this.z,
            matrix[1][0] * this.x + matrix[1][1] * this.y + matrix[1][2] * this.z,
            matrix[2][0] * this.x + matrix[2][1] * this.y + matrix[2][2] * this.z
        );
    }

    public XYZ add(XYZ v)
    {
        return new XYZ(
                this.x + v.x,
                this.y + v.y,
                this.z + v.z
        );
    }

    public XYZ addX(double deltaX)
    {
        return new XYZ(
                this.x + deltaX,
                this.y,
                this.z
        );
    }

    public XYZ addY(double deltaY)
    {
        return new XYZ(
                this.x,
                this.y + deltaY,
                this.z
        );
    }

    public XYZ addZ(double deltaZ)
    {
        return new XYZ(
                this.x,
                this.y,
                this.z + deltaZ
        );
    }

    public XYZ substract(XYZ v)
    {
        return new XYZ(
            this.x - v.x,
            this.y - v.y,
            this.z - v.z
        );
    }

    public XYZ product(double s)
    {
        return new XYZ(
            this.x * s,
            this.y * s,
            this.z * s
        );
    }

    @Override
    public String toString() {
        NumberFormat formatter = new DecimalFormat("#0.000");
        return formatter.format(x) + ", " + formatter.format(y) + ", " + formatter.format(z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XYZ xyz = (XYZ) o;

        if (Double.compare(xyz.x, x) != 0) return false;
        if (Double.compare(xyz.y, y) != 0) return false;
        return Double.compare(xyz.z, z) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
