package com.tfkfan.nettywebgame.game.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class Vector implements Serializable {
    private Double x;
    private Double y;

    public Vector() {
        x = y = 0.0D;
    }

    public Vector(Double x, Double y) {
        setX(x).setY(y);
    }

    public Vector(Vector vector) {
        setX(vector.x).setY(vector.y);
    }

    public Vector set(Vector vector) {
        setX(vector.getX());
        setY(vector.getY());
        return this;
    }

    public Vector set(double x, double y) {
        setX(x);
        setY(y);
        return this;
    }

    public Vector sumX(double x) {
        setX(getX() + x);
        return this;
    }

    public Vector sumY(double y) {
        setY(getY() + y);
        return this;
    }

    public Vector sum(Vector vector) {
        sumX(vector.getX());
        sumY(vector.getY());
        return this;
    }
    public Vector sumNew(Vector vector) {
        Vector v = new Vector(this);
        v.sumX(vector.getX());
        v.sumY(vector.getY());
        return v;
    }
    public Vector sum(double x, double y) {
        sumX(x);
        sumY(y);
        return this;
    }

    public Vector sumNew(double x, double y) {
        Vector v = new Vector(this);
        v.sumX(x);
        v.sumY(y);
        return v;
    }

    public Vector multiplyX(double x) {
        setX(getX() * x);
        return this;
    }

    public Vector multiplyY(double y) {
        setY(getY() * y);
        return this;
    }

    public Vector multiply(Vector vector) {
        multiplyX(vector.getX());
        multiplyY(vector.getY());
        return this;
    }

    public Vector multiply(double x, double y) {
        multiplyX(x);
        multiplyY(y);
        return this;
    }

    public Vector divideX(double x) {
        setX(getX() / x);
        return this;
    }

    public Vector divideY(double y) {
        setY(getY() / y);
        return this;
    }

    public Vector divide(Vector vector) {
        divideX(vector.getX());
        divideY(vector.getY());
        return this;
    }

    public Vector divide(double x, double y) {
        divideX(x);
        divideY(y);
        return this;
    }

    public Vector diff(Vector vector) {
        setX(getX() - vector.getX());
        setY(getY() - vector.getY());
        return this;
    }

    public Vector diff(double x, double y) {
        setX(getX() - x);
        setY(getY() - y);
        return this;
    }

    public Vector diffX(double x) {
        setX(getX() - x);
        return this;
    }

    public Vector diffY(double y) {
        setY(getY() - y);
        return this;
    }

    public Vector reduce(double value) {
        value = Math.abs(value);
        if (getX() > 0)
            setX(getX() - value);
        else if (getX() < 0)
            setX(getX() + value);

        if (getY() > 0)
            setY(getY() - value);
        else if (getY() < 0)
            setY(getY() + value);
        return this;
    }

    public Vector inverse() {
        setX(-getX());
        setY(-getY());
        return this;
    }

    public Vector normalize() {
        set(normalize(this));
        return this;
    }

    public Vector normalizeNew() {
        return normalize(this);
    }

    public Vector inverseNew() {
        return new Vector(-getX(), -getY());
    }

    public void inverse(double multiplicator) {
        setX(-multiplicator * getX());
        setY(-multiplicator * getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        return new EqualsBuilder().append(x, vector.x).append(y, vector.y).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(x).append(y).toHashCode();
    }

    public static Vector diff(Vector v1, Vector v2) {
        return new Vector(v2.getX() - v1.getX(), v2.getY() - v1.getY());
    }

    public static Double getDistance(Vector vector1, Vector vector2) {
        return Math.sqrt(Math.pow(vector2.x - vector1.x, 2) + Math.pow(vector2.y - vector1.y, 2));
    }

    public static Double getLength(Vector vector) {
        return Math.sqrt(Math.pow(vector.x, 2) + Math.pow(vector.y, 2));
    }

    public static Vector normalize(Vector v1) {
        var length = getLength(v1);
        if (length != 0)
            return new Vector(v1.x / length, v1.y / length);

        return new Vector(0.0, 0.0);
    }

    public static Vector multiply(Vector vector, Double scalar) {
        return new Vector(vector.x * scalar, vector.y * scalar);
    }

    public static Vector divide(Vector vector, Double scalar) {
        return new Vector(vector.x / scalar, vector.y / scalar);
    }
}
