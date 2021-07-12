package com.example.Magazyn.model;

public class Punkt {

    public int x;
    public int y;
    public int index;

    public Punkt() {
        this(0, 0,0);
    }

    public Punkt(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    public Punkt(Punkt point) {
        x = point.x;
        y = point.y;
        index = point.index;
    }

    @Override
    public boolean equals(Object object) {

        Punkt point = (Punkt) object;

        if (point.equals(null)) return false;

        return (x == point.x) && (y == point.y);
    }

    public boolean equals(Punkt point) {
        if (point.equals(null)) return false;

        return (x == point.x) && (y == point.y);
    }

    public Punkt set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public String toString() {
        return "Point = {" + x +", " + y + ", "+ index + '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
