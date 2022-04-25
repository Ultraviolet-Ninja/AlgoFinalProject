package project;

import java.util.PriorityQueue;

public class WeightedGraphQ <V extends Comparable<V>> extends WeightedGraph<V> {
    public WeightedGraphQ() {
        super(() -> new PriorityQueue<>(WEIGHT_COMPARATOR));
    }
}