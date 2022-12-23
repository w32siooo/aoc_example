package demo

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Stream

class Day14 {

    private static Stream<String> getInput() {
        var rockList = Files.lines(Path.of("input2.txt"))
                .flatMap(s -> s.split("").toList().stream())
    }
    public class Stats {
        int height
        int rockType
        public Stats(int height, int rockType) {
            this.height = height
            this.rockType = rockType
        }
    }

    public static void simulateRock() {
        List<String> jetList = getInput().toList()

        ArrayList<String> rockSet = []
        int highestY = -1 //floor
        int rockType = 0 // 0: Z + L I #
        int jetCount = 0

        Map<String,Integer> patternMap = [:]

        for (i in 1..<100000) {
            int minX = 0
            int maxX = 0
            int currentY = highestY + 4
            List<int[]> positions = null;

            if (rockType == 0) {
                minX = 2
                maxX = 5
                positions = List.of([2, currentY] as int[], [3, currentY] as int[], [4, currentY] as int[], [5, currentY] as int[])
            }
            if (rockType == 1) {
                minX = 2
                maxX = 4
                positions = List.of([3, currentY] as int[], [2, currentY + 1] as int[], [3, currentY + 1] as int[], [4, currentY + 1] as int[], [3, currentY + 2] as int[]
                )
            }
            if (rockType == 2) {
                minX = 2
                maxX = 4
                positions = List.of([2, currentY] as int[], [3, currentY] as int[], [4, currentY] as int[], [4, currentY + 1] as int[], [4, currentY + 2] as int[])

            }
            if (rockType == 3) {
                minX = 2
                maxX = 2
                positions = List.of([2, currentY] as int[], [2, currentY + 1] as int[], [2, currentY + 2] as int[], [2, currentY + 3] as int[])

            }
            if (rockType == 4) {
                minX = 2
                maxX = 3
                positions = List.of([2, currentY] as int[], [3, currentY] as int[], [2, currentY + 1] as int[], [3, currentY + 1] as int[])

            }

            //check if we can push or if there is a wall in the way
            boolean toRest = false
            while (!toRest) {
                positions.forEach { position ->
                }
                positions.forEach { position ->
                }
                int pushX = jetList.get(jetCount) == ">" ? 1 : -1
                if (canPush(maxX, pushX, minX, positions, rockSet)) {
                    minX += pushX
                    maxX += pushX
                    positions = positions.stream().map(pos -> new int[]{pos[0] + pushX, pos[1]}).toList()
                }

                jetCount++
                if (jetCount == jetList.size()) {
                    jetCount = 0
                }

                var tempPositions = positions.stream().map(pos -> new int[]{pos[0], pos[1] - 1}).toList()

                tempPositions.forEach { position -> //check if it can be pushed down
                    if (rockSet.contains(Arrays.toString(position))) {
                        toRest = true
                    }
                }
                if (currentY == 0) {
                    toRest = true
                }
                if (!toRest) {
                    currentY--
                    positions = tempPositions
                } else {
                    positions.forEach { position ->
                        rockSet.add(Arrays.toString(position))
                        //   println("laid to rest: " + Arrays.toString(position))
                        if (position[1] > highestY) {
                            highestY = position[1]
                        }
                    }
                }

            }// while loop end

            if(rockSet.size() > 1500){
                rockSet= rockSet.subList(rockSet.size()-1500,rockSet.size())
            }
            patternMap.put(String.format("%s %s",jetCount,rockType), rockType)

            rockType = rockType == 4 ? 0 : rockType + 1
        }
        highestY++;
        println(highestY)

    }




    private static boolean canPush(int maxX, int pushX, int minX, List<int[]> positions, ArrayList<String> rockSet) {
        boolean canPush = true
        positions.forEach(position -> {
            if (rockSet.contains(Arrays.toString(new int[]{position[0] + pushX, position[1]}))) {
                canPush = false
            }
        })

        return maxX + pushX <= 6 && minX + pushX >= 0 && canPush
    }


       static void main(String[] args) {
        println "Groovy"

        if (System.getenv("part") == "part1") {
            println simulateRock()
        } else {
            println simulateRock()
        }
    }

}
/*


                int pushX = moveList.get(jetCount) == ">" ? 1 : -1
                if (canPush(maxX, pushX, minX)) { //  between being pushed by a jet of hot gas one unit
                    positions = positions.stream().map(pos -> new int[]{pos[0] + pushX, pos[1]}).toList()
                    maxX += pushX
                    minX += pushX
                }
                jetCount++
                if (toRest) {
                    boolean canFall = false

                    var tempPositions = positions.stream().map(pos -> new int[]{pos[0], pos[1] - 1}).toList()
                    // and then falling one unit down.
                    tempPositions.forEach { position -> //check if it can be pushed down
                        if (rockSet.contains(Arrays.toString(position))) {
                            canFall = true //if there is a rock below it, we can't move down
                        }
                    }
                    if (canFall) {
                        int tempHighestY = positions.stream().mapToInt(pos -> pos[1]).max().getAsInt()

                        positions = positions.stream().map(pos -> new int[]{pos[0], pos[1] + 1}).toList()
                        positions.forEach(s ->
                                rockSet.add(Arrays.toString(s))
                        )
                        highestY = tempHighestY
                        break
                    }
                }

                var tempPositions = positions.stream().map(pos -> new int[]{pos[0], pos[1] - 1}).toList()
                // and then falling one unit down.
                //scan if we can move down by checking if every position has a rock below it
                tempPositions.forEach { position -> //check if it can be pushed down
                    if (rockSet.contains(Arrays.toString(position))) {
                        toRest = true //if there is a rock below it, we can't move down
                    }
                }

                jetCount = resetMoveList(jetCount, moveList)
                if (!toRest) {
                    positions = movePositionsOneYDown(positions)
                    currentY--

                }


                if (currentY == 0) {
                    //       println("rock number $i stops falling it reached the floor")
                    positions = positions.stream().map(pos -> new int[]{pos[0], pos[1]}).toList()

                    highestY = positions.stream().mapToInt(pos -> pos[1]).max().getAsInt() + 1
                    positions.forEach { position ->
                        rockSet.add(Arrays.toString(position))
                    }
                    break
                }
* */
