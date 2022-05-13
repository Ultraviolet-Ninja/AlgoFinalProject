package project;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class CoordinateTest {
    private static final String ANSI_RESET = "\u001B[0m", ANSI_YELLOW = "\u001B[33m";

    private static final double TO_MILLISECONDS = 1_000_000.0, TO_SECONDS = 1000.0;
    private static final int SECONDS_CONVERSION = 60;

    public static void main(String[] args) {
        WeightedGraph<Coordinates> coordinateQueueGraph = new WeightedGraphQ<>();
        WeightedGraph<Coordinates> coordinateSetGraph = new WeightedGraphS<>();
        WeightedGraph<Coordinates> coordinateCustomGraph = new WeightedGraph<>(LinkedHashSet::new) {};

        generateGraph(coordinateQueueGraph);
        generateGraph(coordinateSetGraph);
        generateGraph(coordinateCustomGraph);

        Coordinates testCoord = new Coordinates("C", 6,0);

        long queueStartTime = System.nanoTime();
        coordinateQueueGraph.primsAlgorithm(testCoord);
        long queueStopTime = System.nanoTime();


        long setStartTime = System.nanoTime();
        var resultingList = coordinateSetGraph.primsAlgorithm(testCoord);
        long setStopTime = System.nanoTime();

        long customStartTime = System.nanoTime();
        coordinateCustomGraph.primsAlgorithm(testCoord);
        long customStopTime = System.nanoTime();

        System.out.print("Graph with Priority Queue: ");
        printTime(queueStopTime - queueStartTime);
        System.out.print("Graph with Tree Set: ");
        printTime(setStopTime - setStartTime);
        System.out.print("Graph with Custom Backing: ");
        printTime(customStopTime - customStartTime);

        System.out.println();

        resultingList.forEach(System.out::println);

        double mimimumWeight = resultingList.stream()
                .filter(Objects::nonNull)
                .mapToDouble(WeightedGraph.WeightedEdge::weight)
                .sum();

        System.out.printf("%.2f", mimimumWeight);
    }

    private static void generateGraph(WeightedGraph<Coordinates> graph) {
        int[][] coordinatePairArray = new int[][]{{14, 14}, {3, 6}, {6, 0}, {4, 5}, {14, 1}, {15, 14}, {1, 14},
                {12, 13}, {11, 11}, {0, 5}, {11, 8}, {7, 13}, {9, 4}, {0, 10}, {0, 13}, {7, 15}, {2, 4}, {2, 15},
                {10, 11}, {9, 2}, {2, 11}, {11, 13}, {3, 2}, {4, 1}};

        List<Coordinates> coordinatesList = generateCoordinates(coordinatePairArray);

        for (Coordinates first : coordinatesList) {
            for (Coordinates second : coordinatesList) {
                if (!first.equals(second)) {
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

    private static void printTime(long timeTaken) {
        double toMillis = timeTaken / TO_MILLISECONDS;
        System.out.print(ANSI_YELLOW);
        if (toMillis < TO_SECONDS) {
            System.out.printf("%,.02f ms\n", toMillis);
        } else if (toMillis < TO_SECONDS * SECONDS_CONVERSION) {
            toMillis /= TO_SECONDS;
            System.out.printf("%,.03f sec\n", toMillis);
        } else {
            int totalSeconds = (int) (toMillis / TO_SECONDS);
            int minutes = totalSeconds / SECONDS_CONVERSION;
            int remainingSeconds = totalSeconds % SECONDS_CONVERSION;
            System.out.printf("%d:%d\n", minutes, remainingSeconds);
        }
        System.out.print(ANSI_RESET);
    }
}
