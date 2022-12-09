import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App {
    private static List<int[]> rope;

    public static void main(String[] args) throws IOException {

        System.out.println("Java");
        if (Objects.equals(System.getenv("part"), "part1")) {
            System.out.println(solve(2));
        } else {
            System.out.println(solve(10));
        }
    }

    public static Set<String> solve(String[] input) {

        var set = new HashSet<String>();

        for (int i = 0; i < Integer.parseInt(input[1]); i++) { //Move the rope x times.
            switch (input[0]) {
                case "R" -> rope.get(0)[1] = rope.get(0)[1] + 1;
                case "U" -> rope.get(0)[0] = rope.get(0)[0] - 1;
                case "L" -> rope.get(0)[1] = rope.get(0)[1] - 1;
                case "D" -> rope.get(0)[0] = rope.get(0)[0] + 1;
            }

            for (int knot = 1; knot < rope.size(); knot++) {
                double dist = (int) Math.sqrt(
                        (rope.get(knot - 1)[0] - rope.get(knot)[0])
                                * (rope.get(knot - 1)[0] - rope.get(knot)[0])
                                + (rope.get(knot - 1)[1] - rope.get(knot)[1])
                                * (rope.get(knot - 1)[1] - rope.get(knot)[1]));

                if (dist > 1) {
                    if (rope.get(knot - 1)[1] - rope.get(knot)[1] >= 1) {
                        rope.get(knot)[1]++;
                    }
                    if (rope.get(knot - 1)[1] - rope.get(knot)[1] <= -1) {
                        rope.get(knot)[1]--;
                    }

                    if (rope.get(knot - 1)[0] - rope.get(knot)[0] >= 1) {
                        rope.get(knot)[0]++;
                    }
                    if (rope.get(knot - 1)[0] - rope.get(knot)[0] <= -1) {
                        rope.get(knot)[0]--;
                    }
                }

            }
            set.add(Arrays.toString(rope.get(rope.size() - 1)));
        }
        return set;
    }

    private static long solve(int kCount) throws IOException {
        rope = IntStream.range(0, kCount)
                .mapToObj(s -> new int[]{0, 0})
                .toList();
        try (Stream<String> lines = Files.lines(new File("input.txt").toPath())) {
            return lines.map(s -> s.split(" "))
                    .map(App::solve)
                    .reduce((s1, s2) -> {
                        s1.addAll(s2);
                        return s1;
                    }).orElseGet(HashSet::new)
                    .size();
        }
    }

}
