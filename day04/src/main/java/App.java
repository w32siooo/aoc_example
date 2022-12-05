import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;


public class App {

    public static void main(final String[] args) throws IOException {
        System.out.println(String.format("Java \n%s",System.getenv("part").equals("part1") ? "" + Files.lines(new File("input3.txt").toPath()).map(s -> s.split(",")).map(s -> Arrays.stream(s).map(range -> Arrays.stream(range.split("-")).map(Integer::parseInt).toList()).toList()).filter(s -> s.get(0).get(0) <= s.get(1).get(0) && s.get(0).get(1) >= s.get(1).get(1) || s.get(0).get(0) >= s.get(1).get(0) && s.get(0).get(1) <= s.get(1).get(1)).count() : "" + Files.lines(new File("input3.txt").toPath()).map(s -> s.split(",")).map(s -> Arrays.stream(s).map(range -> Arrays.stream(range.split("-")).map(Integer::parseInt).toList()).toList()).filter(s -> s.get(0).get(1) >= s.get(1).get(0) && s.get(0).get(1) <= s.get(1).get(1) || s.get(1).get(1) >= s.get(0).get(0) && s.get(1).get(1) <= s.get(0).get(1) || s.get(0).get(0) >= s.get(1).get(0) && s.get(0).get(0) <= s.get(1).get(1) || s.get(1).get(0) >= s.get(0).get(0) && s.get(1).get(0) <= s.get(0).get(1)).count()));
    }
}