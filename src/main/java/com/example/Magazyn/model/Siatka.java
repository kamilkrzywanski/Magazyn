package com.example.Magazyn.model;


import java.util.ArrayList;
import java.util.List;


public class Siatka {

    public Wezel[][] wezly;
    int siatkaSzerokosc, siatkaDługosc;





    public Siatka(int szerokosc, int dlugosc, boolean[][] walkableTiles) {
        siatkaSzerokosc = szerokosc;
        siatkaDługosc = dlugosc;
        wezly = new Wezel[szerokosc][dlugosc];

        for (int x = 0; x < szerokosc; x++)
            for (int y = 0; y < dlugosc; y++)
                wezly[x][y] = new Wezel(x, y, walkableTiles[x][y] ? 1.0f : 0.0f);
    }

    public List<Wezel> get8Sasiadow(Wezel node) {
        List<Wezel> neighbours = new ArrayList<Wezel>();





        int  y = 0;
        for (int x = -1; x <= 1; ++x)
        {
            var sasiad = AddWezelNeighbour(x, y, node);
            if (sasiad != null)
                neighbours.add(sasiad);
        }

        int x = 0;
        for (y = -1; y <= 1; ++y)
        {
            var sasiad = AddWezelNeighbour(x, y, node);
            if (sasiad != null)
                neighbours.add(sasiad);
        }





        return neighbours;
    }

    public List<Wezel> get4Sasiadow(Wezel wezel) {
        List<Wezel> neighbours = new ArrayList<Wezel>();

        if (wezel.y + 1 >= 0 && wezel.y + 1  < siatkaDługosc) neighbours.add(wezly[wezel.x][wezel.y + 1]); // N
        if (wezel.y - 1 >= 0 && wezel.y - 1  < siatkaDługosc) neighbours.add(wezly[wezel.x][wezel.y - 1]); // S
        if (wezel.x + 1 >= 0 && wezel.x + 1  < siatkaDługosc) neighbours.add(wezly[wezel.x + 1][wezel.y]); // E
        if (wezel.x - 1 >= 0 && wezel.x - 1  < siatkaDługosc) neighbours.add(wezly[wezel.x - 1][wezel.y]); // W

        return neighbours;
    }




    Wezel AddWezelNeighbour(int x, int y, Wezel wezel)
    {
        if (x == 0 && y == 0)
        {
            return null;
        }

        int checkX = wezel.x + x;
        int checkY = wezel.y + y;

        if (checkX >= 0 && checkX < siatkaSzerokosc && checkY >= 0 && checkY < siatkaDługosc)
        {
            return wezly[checkX] [checkY];
        }

        return null;
    }






    public int getSiatkaWidth() {
        return siatkaSzerokosc;
    }

    public int getSiatkaHeight() {
        return siatkaDługosc;
    }
}
