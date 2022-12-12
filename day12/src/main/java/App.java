import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class App {


    private static final int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static void main(String[] args) throws IOException {

        System.out.println("Java");
        if (Objects.equals(System.getenv("part"), "part1")) {

            solve();
        } else {
            solve();

        }
    }

    private static int[][] getTable() throws IOException {
        int dim = (int) Files.lines(new File("input.txt").toPath()).count();

        AtomicInteger count = new AtomicInteger();
        final int[][] table = new int[dim][dim];
        try (Stream<String> lines = Files.lines(new File("input.txt").toPath())) {
            lines.map(line -> Arrays.stream(line.split(""))
                            .map(s -> s.charAt(0) )
                            .mapToInt(c -> c)
                            .toArray())
                    .forEach(row -> {
                        table[count.get()] = row;
                        count.getAndIncrement();
                    });
            ;
        }
        return table;
    }



    private static void findShortestPathInTable() throws IOException {

        var startingPoints = new ArrayList<int[]>();

        int startX = 0;
        int startY = 0;
        int destY = 0;
        int destX = 0;
        int[][] table = getTable();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] == 0) {
                    startX = i;
                    startY = j;
                }
                if (table[i][j] == -14) {
                    destX = i;
                    destY = j;
                }
                if (table[i][j] == 14) {
                    startingPoints.add(new int[]{i, j});
                }
            }
        }
        int[][] distance = new int[table.length][table[0].length];

        for (int[] ints : distance) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }

        table[destX][destY] = 122 - 83;

        distance[startX][startY] = 0;
        PriorityQueue<int[]> aliveQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        // startingPoints.forEach(point -> aliveQueue.add(new int[]{point[0], point[1], 0}));
        aliveQueue.add(new int[]{startX, startY, 0});

        while (!aliveQueue.isEmpty()) {
            int[] current = aliveQueue.poll();
            int x = current[0];
            int y = current[1];
            int dist = current[2];
            if (x == destX && y == destY) {
                System.out.println("Shortest distance: " + dist);
                break;
            }
            for (int[] direction : directions) {
                int nextX = x + direction[0];
                int nextY = y + direction[1];
                if (nextX >= 0 && nextX < table.length && nextY >= 0 && nextY < table[0].length) {
                    if (dist + 1 < distance[nextX][nextY] && table[nextX][nextY] != 0
                            && table[nextX][nextY] - table[x][y] < 2 || table[x][y] == 0) {
                        distance[nextX][nextY] = dist + 1;
                        aliveQueue.add(new int[]{nextX, nextY, dist + 1});
                    }
                }
            }

        }
        // printMatrix(distance);

        //20-50 = -30
        //printMatrix(table);
        //print
        //System.out.println("Distance: "+distance[destX][destY]);

        //69 = E

        //   System.out.println( table[destX][destY]);


    }

    private static void solve() throws IOException {

        var table = getTable();

        int startX = 20; // row, y
        int startY = 0; // col, x
        int destY = 154;
        int destX = 20;

        //djikstra target is 69, we can only move up one step at a time

        findShortestPathInTable();


    }


    // a-z = 97-122


    //start i=20 j = 0

    //Also included on the heightmap are marks for your current position (S)
    // and the location that should get the best signal (E).
    // Your current position (S) has elevation a,
    // and the location that should get the best signal (E) has elevation z.
    //where a is the lowest elevation, b is the next-lowest, and so on up to the highest elevation, z.
    //You'd like to reach E,


}


