package com.example.Magazyn.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Wyszukiwarka {

    public static List<Punkt> znajdzDroge(Siatka siatka, Punkt startPos, Punkt targetPos, boolean poPrzekatnych) {

        List<Wezel> pathInNodes = znajdzWezlyDrogi(siatka, startPos, targetPos, poPrzekatnych);


        List<Punkt> pathInPoints = new ArrayList<Punkt>();

        if (pathInNodes != null)
            for (Wezel node : pathInNodes)
                pathInPoints.add(new Punkt(node.x, node.y, 0));

        return pathInPoints;
    }


    private static List<Wezel> znajdzWezlyDrogi(Siatka siatka, Punkt startPos, Punkt targetPos, boolean allowDiagonals) {
        Wezel startNode = siatka.wezly[startPos.x][startPos.y];
        Wezel targetNode = siatka.wezly[targetPos.x][targetPos.y];

        List<Wezel> openSet = new ArrayList<Wezel>();
        HashSet<Wezel> closedSet = new HashSet<Wezel>();
        openSet.add(startNode);

        while (openSet.size() > 0) {
            Wezel obecnyWezel = openSet.get(0);

            for (int k = 1; k < openSet.size(); k++) {
                Wezel open = openSet.get(k);

                if (open.getFCost() < obecnyWezel.getFCost() ||
                        open.getFCost() == obecnyWezel.getFCost() &&
                                open.hCost < obecnyWezel.hCost)
                    obecnyWezel = open;
            }

            openSet.remove(obecnyWezel);
            closedSet.add(obecnyWezel);

            if (obecnyWezel == targetNode)
                return retracePath(startNode, targetNode);

            List<Wezel> neighbours;
            if (allowDiagonals) neighbours = siatka.get8Sasiadow(obecnyWezel);
            else neighbours = siatka.get4Sasiadow(obecnyWezel);

            for (Wezel sasiad : neighbours) {
                if (!sasiad.walkable || closedSet.contains(sasiad)) continue;

                int newMovementCostToNeighbour = obecnyWezel.gCost + getDistance(obecnyWezel, sasiad) * (int) (10.0f * sasiad.price);
                if (newMovementCostToNeighbour < sasiad.gCost || !openSet.contains(sasiad)) {
                    sasiad.gCost = newMovementCostToNeighbour;
                    sasiad.hCost = getDistance(sasiad, targetNode);
                    sasiad.parent = obecnyWezel;

                    if (!openSet.contains(sasiad)) openSet.add(sasiad);
                }
            }
        }

        return null;
    }

    private static List<Wezel> retracePath(Wezel startNode, Wezel endNode) {
        List<Wezel> path = new ArrayList<Wezel>();
        Wezel obecnyWezel = endNode;

        while (obecnyWezel != startNode) {
            path.add(obecnyWezel);
            obecnyWezel = obecnyWezel.parent;
        }

        Collections.reverse(path);
        return path;
    }

    private static int getDistance(Wezel nodeA, Wezel nodeB) {
        int odlegoloscX = Math.abs(nodeA.x - nodeB.x);
        int odlegoloscY = Math.abs(nodeA.y - nodeB.y);

        if (odlegoloscX > odlegoloscY)
            return 14 * odlegoloscY + 10 * (odlegoloscX - odlegoloscY);
        return 14 * odlegoloscX + 10 * (odlegoloscY - odlegoloscX);
    }

}
