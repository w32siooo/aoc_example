import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class App {
    public static final int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // down, up, right, left

    public static void main(String[] args) throws IOException {
        System.out.println("Java");
        if (Objects.equals(System.getenv("part"), "part1")) {

            part1();
        } else {
            part2();
        }
    }

    public static void part1() throws IOException {
        var score = 0;

        AtomicInteger count = new AtomicInteger();
        final int[][] table = getTable();
        var scored = new boolean[table.length][table.length];

        try (Stream<String> lines = Files.lines(new File("inputf.txt").toPath())) {
            lines.map(line -> Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray())
                    .forEach(line -> {
                        table[count.getAndIncrement()] = line;
                    });
        }
        for (int i = 0; i < 4; i++) {
            for (int pointer = 1; pointer < table.length - 1; pointer++) {
                int x = 0, y = 0;
                switch (i) {
                    case 0 -> y = pointer;
                    case 1 -> {
                        x = table.length - 1;
                        y = pointer;
                    }
                    case 2 -> {
                        x = pointer;
                    }
                    case 3 -> {
                        x = pointer;
                        y = table.length - 1;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + i);
                }
                int currentTree = table[x][y];

                int lookCount = 1;

                while (lookCount < table.length - 1) { // spot taller trees
                    int nextTree = table[x + directions[i][0] * lookCount][y + directions[i][1] * lookCount];
                    if (currentTree < nextTree) {
                        if (!scored[x + directions[i][0] * lookCount][y + directions[i][1] * lookCount]) {
                            score++;
                            scored[x + directions[i][0] * lookCount][y + directions[i][1] * lookCount] = true;
                        }
                        currentTree = nextTree;
                    }
                    lookCount++;
                }
            }
        }

        System.out.println(score + (table.length * 4 - 4));
    }


    public static void part2() throws IOException {

        final int[][] table = getTable();


        ArrayList<Integer> topScenic = new ArrayList<>();
        for (int rows = 0; rows < table.length; rows++) {
            for (int cols = 0; cols < table.length; cols++) {
                int startTree = table[rows][cols];
                int[] scenics = new int[4];

                for (int i = 0; i < 4; i++) { // Walk in every dir from tree, until blocked.
                    int lookCount = 1;
                    boolean viewIsBlocked = false;
                    int tempScenic = 0;
                    while (!viewIsBlocked && rows + directions[i][0] * lookCount < table.length && rows + directions[i][0] * lookCount >= 0
                            && cols + directions[i][1] * lookCount < table.length && cols + directions[i][1] * lookCount >= 0
                    ) {
                        int newTree = table[rows + directions[i][0] * lookCount][cols + directions[i][1] * lookCount];
                        tempScenic++;
                        if (newTree >= startTree) viewIsBlocked = true;
                        lookCount++;
                    }
                    scenics[i] = tempScenic;
                }

                Integer totalScenic = Arrays.stream(scenics).reduce((a, b) -> a * b).getAsInt();
                topScenic.add(totalScenic);
            }

        }
        System.out.println(Collections.max(topScenic));

    }

    private static int[][] getTable() throws IOException {
        int dim = (int) Files.lines(new File("input.txt").toPath()).count();

        AtomicInteger count = new AtomicInteger();
        final int[][] table = new int[dim][dim];
        try (Stream<String> lines = Files.lines(new File("input.txt").toPath())) {
            lines.map(line -> Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray())
                    .forEach(line -> {
                        table[count.getAndIncrement()] = line;
                    });
        }
        return table;
    }


}
