package project;

import java.util.TreeSet;

public class WeightedGraphS<V extends Comparable<V>> extends WeightedGraph<V> {
    public WeightedGraphS() {
        super(() -> new TreeSet<>(WEIGHT_COMPARATOR));
    }
}
