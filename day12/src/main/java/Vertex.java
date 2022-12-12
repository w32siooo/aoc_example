public record Vertex(int x, int y, int distance) implements Comparable<Vertex> {
    @Override
    public int compareTo(Vertex o) {
        return Integer.compare(distance, o.distance);
    }
}
