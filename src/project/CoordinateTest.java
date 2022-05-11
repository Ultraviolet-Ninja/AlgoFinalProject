package project;

import java.util.ArrayList;
import java.util.List;

public class CoordinateTest {
    public static void main(String[] args) {

    }

    private static void generateFirstGraph(WeightedGraph<Coordinates> graph) {
        int[][] coordinatePairArray = new int[][]{{2, 4}, {10, 5}, {12, 15}, {3, 11}, {2, 15}, {1, 9}, {9, 12},
                {13, 12}, {0, 2}, {3, 14}, {4, 4}, {3, 14}, {1, 14}, {1, 12}, {5, 12}, {4, 1}, {3, 2}, {1, 6},
                {12, 3}, {12, 0}, {5, 5}, {3, 8}, {2, 7}, {8, 11}, {13, 15}, {10, 14}};

        List<Coordinates> coordinatesList = generateCoordinates(coordinatePairArray);

        for (Coordinates first : coordinatesList) {
            for (Coordinates second : coordinatesList) {
                if (first != second) {
                    graph.addEdge(first, second, first.distanceTo(second));
                }
            }
        }
    }

    private static List<Coordinates> generateCoordinates(int[][] coordinatePairArray) {
        List<Coordinates> coordinates = new ArrayList<>();

        int offset = 0;
        for (int[] pair : coordinatePairArray) {
            char letter = (char) ('A' + offset++);
            coordinates.add(new Coordinates(String.valueOf(letter), pair[0], pair[1]));
        }
        return coordinates;
    }
}
