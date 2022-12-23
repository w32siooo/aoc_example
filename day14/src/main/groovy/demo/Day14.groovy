package demo

import java.nio.file.Files
import java.nio.file.Path

class Day14 {
    private static void solve() {
        def rockList = Files.lines(Path.of("input.txt"))
                .map(line -> line.split(" -> "))
                .map(s -> setupPaths(s.toList()))
                .reduce((a, b) -> {
                    a.addAll(b)
                    return a;
                }).get()
        println simulateSand(rockList)
    }

    private static long simulateSand(ArrayList<int[]> rockList) {
       // if (System.getenv("part") == "part2") {
            def highest = []
            rockList.forEach(s-> highest<< s[1])
            def wallY = highest.sort().reverse()[0]+2
            IntRange range = new IntRange(0, 100000)
            range.forEach(s -> rockList << ([s, wallY] as int[]))


        def sandX = 500
        def sandY = 0

        String[][] twoDArray = new String[100001][1000]

        for (int i = 0; i < twoDArray.length; i++) {
            for (int j = 0; j < twoDArray[i].length; j++) {
                twoDArray[i][j] = " "
            }
        }

        rockList.each { rock ->
            twoDArray[rock[0]][rock[1]] = "#"
        }
        twoDArray[sandX][sandY] = "+"

        def count = -1
        while (true) {
            try {
                count++
                while (true) {
                    if (twoDArray[sandX][sandY + 1] == " ") {
                        sandY++
                    }else if (twoDArray[sandX - 1][sandY + 1] == " " ) {
                        sandX--
                        sandY++
                    }
                    else if (twoDArray[sandX + 1][sandY + 1] == " " ) {
                        sandX++
                        sandY++
                    } else {
                        twoDArray[sandX][sandY] = "0"
                        sandX = 500
                        sandY = 0
                        break
                    }
                }
                if(twoDArray[500][0]=="0"){
                    break
                }
            } catch (ArrayIndexOutOfBoundsException fellIntoTheVoid) {
                break
            }
        }
        return count
    }

    private static setupPaths(path) {
        def rocks = [] as ArrayList<int[]>
        for (int i = 0; i < path.size() - 1; i++) {
            def startX = path.get(i).split(",")[0].toInteger()
            def endX = path.get(i + 1).split(",")[0].toInteger()
            def startY = path.get(i).split(",")[1].toInteger()
            def endY = path.get(i + 1).split(",")[1].toInteger()
            if (startX != endX) {
                IntRange range = new IntRange(startX, endX)
                range.forEach(s -> rocks << ([s, startY] as int[]))
            } else {
                IntRange range = new IntRange(startY, endY)
                range.forEach(s -> rocks << ([startX, s] as int[]))
            }
        }
        return rocks
    }

    static void main(String[] args) {
        println "Groovy"
        solve()
    }

}
