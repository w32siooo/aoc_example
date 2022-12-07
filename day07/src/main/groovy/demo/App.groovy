package demo
import java.nio.file.Files
import java.nio.file.Path

class App {

    static day7BuildDirs() {
        dirs.put(currentDir, 0)
        Files.lines(Path.of("input7.txt"))
                .skip(1)
                .forEach { line ->
                    {
                        findAndFireCommand(line)
                    }
                }
    }

    static void main(String[] args) {
        println "Groovy"
        if (System.getenv("part") == "part1") {
            day7BuildDirs()
            println part1()
        } else {
            day7BuildDirs()
            println part2()
        }
    }
    public static final String baseDir = String.format("%s%saoc", ".", File.separator)
    public static String currentDir = String.format("%s/aoc", ".")
    public static boolean writeMode = false

     static <V> SortedMap<String, V> filterPrefix(SortedMap<String, V> baseMap, String prefix) {
        if (prefix.length() > 0) {
            char nextLetter = prefix.charAt(prefix.length() - 1) + 1;
            String end = prefix.substring(0, prefix.length() - 1) + nextLetter;
            return baseMap.subMap(prefix, end);
        }
        return baseMap;
    }

    static String part1() {
        def newSum = 0

        dirs.keySet().each { dir ->
            if (dirs.get(dir) <= 100000 && dir != baseDir) {
                String prefix = dir
                def tempsum = 0
                for (Map.Entry<String, Integer> entry : filterPrefix(dirs, prefix).entrySet()) {
                    tempsum += entry.getValue() // filter through subdirectories
                }
                if (tempsum < 100000) {
                    newSum += tempsum
                }
            }
        }

        return newSum.toString()
    }

    static String part2() {
        int usedSpace = dirs.values().sum() as int, totalSpace = 70000000, needSpace = 30000000, smallestDir = Integer.MAX_VALUE,  currentSpace = totalSpace - usedSpace

        dirs.keySet().each { dir ->
            if (dir != baseDir) {
                // check subdirectories
                String prefix = dir
                def tempsum = 0
                for (Map.Entry<String, Integer> entry : filterPrefix(dirs, prefix).entrySet()) {
                    tempsum += entry.getValue()
                }
                if (tempsum < smallestDir) {
                    if (currentSpace + tempsum >= needSpace && tempsum < smallestDir) smallestDir = tempsum
                }
            }
        }


        return smallestDir
    }

    private static SortedMap<String, Integer> dirs = new TreeMap<String, Integer>()

    static void findAndFireCommand(String s) {
        if (s.startsWith("\$ cd")) {
            if (s.contains("..")) {
                currentDir = currentDir.substring(0, currentDir.lastIndexOf("/"))
            } else {
                currentDir = String.format("%s/%s", currentDir, s.find(/[\w.]+(?=$)/))
            }
            writeMode = false
        } else if (s.contains("\$")) {
            writeMode = false
        }
        else if (writeMode) {
            if (s.contains("dir")) {
                dirs.put(String.format("%s/%s", currentDir, s.find(/\s+([\w]+)/).trim()), 0)
            } else {
                def fileSize = s.substring(0, s.lastIndexOf(" "))
                dirs.put(currentDir, dirs.get(currentDir) + fileSize.toInteger())
            }
        }
        if (s.contains("ls")) {
            writeMode = true // next line is a file or dir
        }
    }
}


