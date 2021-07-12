package com.example.Magazyn.model;

public class Wezel {

    public boolean walkable;
    public int x;
    public int y;
    public float price;

    public int gCost;
    public int hCost;
    public Wezel parent;


    public Wezel(int x, int y, float price) {
        walkable = price != 0.0f;
        this.price = price;
        this.x = x;
        this.y = y;
    }

    public int getFCost() {
        return gCost + hCost;
    }



}
