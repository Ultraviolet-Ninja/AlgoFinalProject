package project;

public class Main {
    public static void main(String[] args) {
//        WeightedGraph<String> graph = new WeightedGraphQ<>();
//        setupVideoGraph(graph);
//
//        graph.primsAlgorithm("G").forEach(System.out::println);

        WeightedGraph<String> graph2 = new WeightedGraphQ<>();
        setupSecondGraph(graph2);

        graph2.primsAlgorithm("S").forEach(System.out::println);
    }

    private static void setupVideoGraph(WeightedGraph<String> graph) {
        graph.addEdge("A", "B", 2.0);
        graph.addEdge("A", "C", 3.0);
        graph.addEdge("A", "D", 3.0);

        graph.addEdge("B", "C", 4.0);
        graph.addEdge("B", "E", 3.0);

        graph.addEdge("C", "F", 6.0);
        graph.addEdge("C", "E", 1.0);
        graph.addEdge("C", "D", 5.0);

        graph.addEdge("D", "F", 7.0);

        graph.addEdge("E", "F", 8.0);

        graph.addEdge("F", "G", 9.0);
    }

    //https://www.tutorialspoint.com/data_structures_algorithms/prims_spanning_tree_algorithm.htm
    private static void setupSecondGraph(WeightedGraph<String> graph) {
        graph.addEdge("S", "A", 7.0);
        graph.addEdge("S", "C", 8.0);

        graph.addEdge("C", "A", 3.0);
        graph.addEdge("C", "D", 3.0);
        graph.addEdge("C", "B", 4.0);

        graph.addEdge("B", "A", 6.0);

        graph.addEdge("D", "B", 2.0);
        graph.addEdge("D", "T", 2.0);

        graph.addEdge("T", "B", 5.0);
    }
}
