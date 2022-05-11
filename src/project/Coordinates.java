package project;

public record Coordinates(String label, int x, int y) implements Comparable<Coordinates> {
    @Override
    public String toString() {
        return String.format("%s: (%d,%d)", label, x, y);
    }

    @Override
    public int compareTo(Coordinates o) {
        if (o == null) return -1;

        int xComparison = Integer.compare(x, o.x);
        if (xComparison != 0) return xComparison;

        return Integer.compare(y, o.y);
    }

    public double distanceTo(Coordinates o) {
        return Math.sqrt(Math.pow(this.x - o.x, 2) + Math.pow(this.y - o.y, 2));
    }
}
