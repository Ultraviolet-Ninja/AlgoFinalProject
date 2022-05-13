package project;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Supplier;

public abstract class WeightedGraph<V extends Comparable<V>> {
    protected static final Comparator<WeightedEdge<?>> WEIGHT_COMPARATOR = Comparator.comparingDouble(e -> e.weight);

    private final Map<V, Collection<WeightedEdge<V>>> graph;
    private final Supplier<Collection<WeightedEdge<V>>> collectionSupplier;

    public WeightedGraph(Supplier<Collection<WeightedEdge<V>>> collectionSupplier) {
        graph = new LinkedHashMap<>();
        this.collectionSupplier = collectionSupplier;
    }

    public void addVertex(V vertex) {
        if (graph.containsKey(vertex))
            return;

        graph.put(vertex, collectionSupplier.get());
    }

    public void addEdge(V source, V destination, double weight) throws IllegalArgumentException {
        addVertex(source);
        addVertex(destination);

        WeightedEdge<V> firstEdge = new WeightedEdge<>(source, destination, weight);
        WeightedEdge<V> secondEdge = new WeightedEdge<>(destination, source, weight);

        if (!graph.get(source).contains(firstEdge))
            graph.get(source).add(firstEdge);

        if (!graph.get(destination).contains(secondEdge))
            graph.get(destination).add(secondEdge);
    }

    public List<WeightedEdge<V>> primsAlgorithm(V startNode) {
        if (graph.isEmpty() || !graph.containsKey(startNode) || isNotFullyConnected())
            return null;
        int graphSize = graph.size();
        Map<V, WeightedEdge<V>> spanningTree = new HashMap<>((int) (graphSize / 0.75));
        PriorityQueue<WeightedEdge<V>> queue = new PriorityQueue<>(WEIGHT_COMPARATOR);

        spanningTree.put(startNode, null);
        queue.addAll(graph.get(startNode));

        //E
        while (!queue.isEmpty()) { // 1
            WeightedEdge<V> currentEdge = queue.poll(); //log n
            if (spanningTree.containsKey(currentEdge.destination)) continue; //1

            spanningTree.put(currentEdge.destination, currentEdge); //1
            if (spanningTree.size() == graphSize)//1
                return new ArrayList<>(spanningTree.values()); //n

            queue.addAll(graph.get(currentEdge.destination)); // E log(n)
        }

        return new ArrayList<>(spanningTree.values()); //n
    }

    private boolean isNotFullyConnected() {
        V start = graph.keySet()
                .iterator()
                .next();
        int size = graph.size();
        Set<V> allNodes = new HashSet<>();
        ArrayDeque<WeightedEdge<V>> deque = new ArrayDeque<>(graph.get(start));

        allNodes.add(start);
        while (!deque.isEmpty()) {
            V destinationNode = deque.poll().destination;
            if (allNodes.contains(destinationNode))
                continue;

            allNodes.add(destinationNode);
            deque.addAll(graph.get(destinationNode));
            if (allNodes.size() == size)
                return false;
        }

        return allNodes.size() != size;
    }

    @Override
    public String toString() {
        List<String> strings = graph.values()
                .stream()
                .flatMap(Collection::stream)
                .map(Objects::toString)
                .toList();
        return String.join("\n", strings);
    }

    protected record WeightedEdge<V>(V source, V destination, double weight) {
        public WeightedEdge {
            if (weight < 0.0)
                throw new IllegalArgumentException("Weight cannot be negative");
        }

        @Override
        public String toString() {
            return String.format("%s - %s Weight: %.2f", source, destination, weight);
        }
    }
}
