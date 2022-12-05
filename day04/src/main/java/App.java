import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(final String[] args) throws IOException {
        System.out.println(String.format("Java \n%s", System.getenv("part").equals("part1")
                ? Files.lines(new File("input3.txt").toPath())
                .map(s -> s.split(","))
                .map(s -> List.of(
                        Arrays.stream(Arrays.asList(s).get(0).split("-"))
                                .mapToInt(Integer::parseInt)
                                .toArray(),
                        Arrays.stream(Arrays.asList(s).get(1).split("-"))
                                .mapToInt(Integer::parseInt)
                                .toArray())
                )
                .filter(s -> s.get(0)[0] <= s.get(1)[0] && s.get(0)[1] >= s.get(1)[1] || s.get(0)[0] >= s.get(1)[0] && s.get(0)[1] <= s.get(1)[1])
                .count()
                : Files.lines(new File("input3.txt")
                        .toPath())
                .map(s -> s.split(","))
                .map(s -> List.of(
                        Arrays.stream(Arrays.asList(s).get(0).split("-"))
                                .mapToInt(Integer::parseInt)
                                .toArray(),
                        Arrays.stream(Arrays.asList(s).get(1).split("-"))
                                .mapToInt(Integer::parseInt)
                                .toArray()))
                .filter(s -> s.get(0)[1] >= s.get(1)[0] && s.get(0)[1] <= s.get(1)[1] || s.get(1)[1] >= s.get(0)[0] && s.get(1)[1] <= s.get(0)[1] || s.get(0)[0] >= s.get(1)[0] && s.get(0)[0] <= s.get(1)[1] || s.get(1)[0] >= s.get(0)[0] && s.get(1)[0] <= s.get(0)[1])
                .count()));
    }
}