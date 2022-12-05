import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class App {
    private static final Predicate<List<int[]>> part1 = s -> s.get(0)[0] <= s.get(1)[0] && s.get(0)[1] >= s.get(1)[1] || s.get(0)[0] >= s.get(1)[0] && s.get(0)[1] <= s.get(1)[1];
    private static final Predicate<List<int[]>> part2 = s -> s.get(0)[1] >= s.get(1)[0] && s.get(0)[1] <= s.get(1)[1] || s.get(1)[1] >= s.get(0)[0] && s.get(1)[1] <= s.get(0)[1] || s.get(0)[0] >= s.get(1)[0] && s.get(0)[0] <= s.get(1)[1] || s.get(1)[0] >= s.get(0)[0] && s.get(1)[0] <= s.get(0)[1];

    public static void main(final String[] args) throws IOException {

        System.out.println(String.format("Java \n%s", System.getenv("part").equals("part1")
                ? fs(part1)
                : fs(part2)));
    }
    private static int fs (final Predicate<List<int[]>> sol) throws IOException {
       return (int) Files.lines(new File("input3.txt").toPath())
                .map(s -> s.split(","))
                .map(s -> List.of(
                        Arrays.stream(Arrays.asList(s).get(0).split("-"))
                                .mapToInt(Integer::parseInt)
                                .toArray(),
                        Arrays.stream(Arrays.asList(s).get(1).split("-"))
                                .mapToInt(Integer::parseInt)
                                .toArray())
                )
                .filter(sol)
                .count();
    }
}