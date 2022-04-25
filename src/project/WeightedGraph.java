package project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.function.Supplier;

public abstract class WeightedGraph<V extends Comparable<V>> {
    protected static final Comparator<WeightedEdge<?>> WEIGHT_COMPARATOR = Comparator.comparingDouble(e -> e.weight);

    private final Map<V, Collection<WeightedEdge<V>>> graph;
    private final Supplier<Collection<WeightedEdge<V>>> collectionSupplier;

    public WeightedGraph(Supplier<Collection<WeightedEdge<V>>> collectionSupplier) {
        graph = new LinkedHashMap<>();
        this.collectionSupplier = collectionSupplier;
    }

    public boolean addVertex(V vertex) {
        if (graph.containsKey(vertex))
            return false;

        graph.put(vertex, collectionSupplier.get());
        return true;
    }

    public boolean addEdge(V source, V destination, double weight) throws IllegalArgumentException {
        addVertex(source);
        addVertex(destination);

        graph.get(source).add(new WeightedEdge<>(source, destination, weight));
        graph.get(destination).add(new WeightedEdge<>(destination, source, weight));
        return true;
    }

    //TODO - Determine that graph is fully connected
    public List<WeightedEdge<V>> primsAlgorithm(V startNode) {
        if (graph.isEmpty() || !graph.containsKey(startNode))
            return null;
        int graphSize = graph.size();

        Map<V, WeightedEdge<V>> spanningTree = new HashMap<>(graphSize);
        PriorityQueue<WeightedEdge<V>> queue = new PriorityQueue<>(WEIGHT_COMPARATOR);

        spanningTree.put(startNode, null);
        queue.addAll(graph.get(startNode));

        while (!queue.isEmpty()) {
            WeightedEdge<V> currentEdge = queue.poll();
            if (spanningTree.containsKey(currentEdge.destination)) continue;

            spanningTree.put(currentEdge.destination, currentEdge);
            queue.addAll(graph.get(currentEdge.destination));

            if (spanningTree.size() == graphSize) break;
        }

        return new ArrayList<>(spanningTree.values());
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
            return source + " - " + destination + " Weight: " + weight;
        }
    }
}
