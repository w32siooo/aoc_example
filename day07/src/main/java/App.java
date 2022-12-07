
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class App {

    public static final String baseDir= String.format("%s%saoc", ".", File.separator);
    private static final SortedMap<String, Integer>dirs= new TreeMap<>();
    public static String currentDir= String.format("%s/aoc", ".");
    static boolean writeMode= false;

    public static void day7BuildDirs() throws IOException {
        dirs.put(currentDir, 0);
        Files.lines(Path.of("input.txt"))
                .skip(1)
                .forEach(App::findAndFireCommand
                );
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Java");
        if (Objects.equals(System.getenv("part"), "part1")) {
            day7BuildDirs();
            System.out.println(part1());
        } else {
            day7BuildDirs();
            System.out.println(part2());
        }
    }

    static <V> SortedMap<String, V> filterPrefix(SortedMap<String, V> baseMap, String prefix) {
        if (prefix.length() > 0) {
            char nextLetter = (char) (prefix.charAt(prefix.length() - 1) + 1);
            String end = prefix.substring(0, prefix.length() - 1) + nextLetter;
            return baseMap.subMap(prefix, end);
        }
        return baseMap;
    }

    static int part1() {
        AtomicInteger newSum = new AtomicInteger();
        dirs.keySet().forEach(dir -> {
                    if (dirs.get(dir) <= 100000 && !Objects.equals(dir,baseDir)) {
                        var tempsum = 0;
                        for (Map.Entry<String, Integer> entry :filterPrefix(dirs, dir).entrySet()) {
                            tempsum += entry.getValue(); // filter through subdirectories
                        }
                        if (tempsum < 100000) {
                            newSum.addAndGet(tempsum);
                        }
                    }
                }
        );

        return newSum.get();
    }

    static int part2() {
        int usedSpace =dirs.values().stream().mapToInt(java.lang.Integer::intValue).sum(), totalSpace = 70000000, needSpace = 30000000, currentSpace = totalSpace - usedSpace;
        final int[] smallestDir = {2147483647};
        dirs.keySet().forEach(dir -> {
            if (!Objects.equals(dir,baseDir)) {
                // check subdirectories
                var tempsum = 0;
                for (Map.Entry<String, Integer> entry :filterPrefix(dirs, dir).entrySet()) {
                    tempsum += entry.getValue();
                }
                if (tempsum < smallestDir[0]) {
                    if (currentSpace + tempsum >= needSpace) smallestDir[0] = tempsum;
                }
            }
        });


        return smallestDir[0];
    }

    static void findAndFireCommand(String s) {
        String[] split = s.split(" ");
        if (s.startsWith("$ cd")) {
            if (s.contains("..")) {
                currentDir=currentDir.substring(0,currentDir.lastIndexOf("/"));
            } else {
                currentDir= String.format("%s/%s",currentDir, split[2]);
            }
            writeMode= false;
        } else if (s.contains("$")) {
            writeMode= false;
        } else if (writeMode) {
            if (s.contains("dir")) {
                dirs.put(String.format("%s/%s",currentDir, split[1]),0);
            } else {
                var fileSize = s.substring(0, s.lastIndexOf(" "));
                dirs.put(currentDir,dirs.get(currentDir) + Integer.parseInt(fileSize));
            }
        }
        if (s.contains("ls")) {
            writeMode= true; // next line is a file or dir
        }
    }
}


