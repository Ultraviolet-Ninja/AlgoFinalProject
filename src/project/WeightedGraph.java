package project;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class WeightedGraph<V extends Comparable<V>> {
    protected static final Comparator<WeightedEdge<?>> WEIGHT_COMPARATOR = Comparator.comparingInt(e -> e.weight);

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

    public boolean addEdge(V source, V destination, int weight) throws IllegalArgumentException {
        addVertex(source);
        addVertex(destination);

        return graph.get(source).add(new WeightedEdge<>(destination, weight)) &&
                graph.get(destination).add(new WeightedEdge<>(source, weight));
    }

    public int primAlgorithm() {
        return 0;
    }

    protected record WeightedEdge<V>(V destination, int weight) {
        public WeightedEdge {
            if (weight < 0)
                throw new IllegalArgumentException("Weight cannot be negative");
        }

        @Override
        public int hashCode() {
            return destination.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof WeightedEdge)) return false;

            return this.destination.equals(((WeightedEdge<?>) o).destination);
        }
    }
}
