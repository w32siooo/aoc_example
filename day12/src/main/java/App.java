import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

public class App {
    private static final int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // down, up, right, left

    public static void main(String[] args) throws IOException {
        System.out.println("Java");
        findShortestPathInTable(!Objects.equals(System.getenv("part"), "part1"));
    }

    private static int[][] getTable() throws IOException {

        try (Stream<String> lines = Files.lines(new File("input.txt").toPath())) {
            return lines.map(line -> Arrays.stream(line.split(""))
                            .map(s -> s.charAt(0))
                            .mapToInt(c -> c)
                            .toArray())
                    .toArray(int[][]::new);
        }
    }

    private static void findShortestPathInTable(boolean multipleStarts) throws IOException {
        int destY = 0, destX = 0;
        int[][] table = getTable();
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
        int[][] distance = new int[table.length][table[0].length];

        for (int[] ints : distance) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] == 83) {
                    priorityQueue.add(new Vertex(i, j, 0));
                    distance[i][j] = 0;
                }
                if (table[i][j] == 69) {
                    destX = i;
                    destY = j;
                    table[i][j] = 122;
                }
                if (table[i][j] == 97 && multipleStarts) {
                    distance[i][j] = 0;
                    priorityQueue.add(new Vertex(i, j, 0));
                }
            }
        }

        while (!priorityQueue.isEmpty()) {
            Vertex current = priorityQueue.poll();
            int dist = current.distance();
            if (current.x() == destX && current.y() == destY) {
                System.out.println("Shortest distance is: " + dist);
                break;
            }
            for (int[] direction : directions) {
                int nextX = current.x() + direction[0];
                int nextY = current.y() + direction[1];
                if (nextX >= 0 && nextX < table.length && nextY >= 0 && nextY < table[0].length) {
                    if (dist + 1 < distance[nextX][nextY] && table[nextX][nextY] - table[current.x()][current.y()] <= 1
                            || table[current.x()][current.y()] == 83) {
                        distance[nextX][nextY] = dist + 1;
                        priorityQueue.add(new Vertex(nextX, nextY, dist + 1));
                    }
                }
            }
        }
    }
}


