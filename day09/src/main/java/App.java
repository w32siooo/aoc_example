import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

public class App {
    public static final int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // down, up, right, left

    public static void main(String[] args) throws IOException {

        System.out.println("Java");
        if (Objects.equals(System.getenv("part"), "part1")) {
            solve(2);
        } else {
            solve(10);
        }
    }

    public static void solve(int kCount) throws IOException {

        var inputList = parseInput();

        int gridWidth = 1000;

        char[][] tails = new char[gridWidth][gridWidth];

        var knots = new ArrayList<int[]>();

        for (int i = 0; i < kCount; i++) {
            knots.add(new int[]{gridWidth / 2 + 4, gridWidth / 2});
        }

        for (String[] input : inputList
        ) {
            for (int i = 0; i < Integer.parseInt(input[1]); i++) {
                switch (input[0]) {
                    case "R" -> knots.get(0)[1] = knots.get(0)[1] + 1;
                    case "U" -> knots.get(0)[0] = knots.get(0)[0] - 1;
                    case "L" -> knots.get(0)[1] = knots.get(0)[1] - 1;
                    case "D" -> knots.get(0)[0] = knots.get(0)[0] + 1;
                }

                for (int knotIndex = 1; knotIndex < knots.size(); knotIndex++) {
                    int dist = (int) Math.sqrt((knots.get(knotIndex - 1)[0] - knots.get(knotIndex)[0])
                            * (knots.get(knotIndex - 1)[0] - knots.get(knotIndex)[0])
                            + (knots.get(knotIndex - 1)[1] - knots.get(knotIndex)[1])
                            * (knots.get(knotIndex - 1)[1] - knots.get(knotIndex)[1]));

                    if (dist > 1) {
                        if (knots.get(knotIndex - 1)[1] - knots.get(knotIndex)[1] >= 1) { //head x 5 tail x 3 = 2 tail is behind add one
                            knots.get(knotIndex)[1]++;
                        }
                        if (knots.get(knotIndex - 1)[1] - knots.get(knotIndex)[1] <= -1) { //head x 0 tail x 2 = -2 tail is behind subtract one
                            knots.get(knotIndex)[1]--;
                        }

                        if (knots.get(knotIndex - 1)[0] - knots.get(knotIndex)[0] >= 1) { //head y 5 tail y 3 = 2 tail is behind add one
                            knots.get(knotIndex)[0]++;
                        }
                        if (knots.get(knotIndex - 1)[0] - knots.get(knotIndex)[0] <= -1) { //head y 0 tail y 2 = -2 tail is behind subtract one
                            knots.get(knotIndex)[0]--;
                        }
                    }

                }
                tails[knots.get(kCount-1)[0]][knots.get(kCount-1)[1]] = '#';

            }
        }
        int count = 0;

        for (char[] x : tails) {
            for (char y : x) {
                if (y == '#') count++;
            }
        }
        System.out.println(count);

    }
    private static List<String[]> parseInput() throws IOException {

        try (Stream<String> lines = Files.lines(new File("input.txt").toPath())) {
            return lines.map(s -> s.split(" ")).toList();
        }
    }

}
