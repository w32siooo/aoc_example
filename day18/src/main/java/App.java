import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class App {

    private static final Set<String> cubeSet = new HashSet<>();
    private static final Set<String> cubesNotFullyInside = new HashSet<>();
    private static final List<int[]> directions = Arrays.asList(new int[]{1, 0, 0}, new int[]{-1, 0, 0}, new int[]{0, 1, 0}, new int[]{0, -1, 0}, new int[]{0, 0, 1}, new int[]{0, 0, -1});

    public static void main(String[] args) throws IOException {

        System.out.println("Java");
        if (Objects.equals(System.getenv("part"), "part1")) {
            readData();
            System.out.println("Surface area of exposed cubes: " + calculateSurfaceAreaOfExposedCubes());
        } else {
            readData();
            calculateInnerSides(calculateSurfaceAreaOfExposedCubes());
        }
    }

    private static void readData() throws IOException {

        try (Stream<String> lines = Files.lines(new File("input.txt").toPath())) {
            lines.map(s -> Arrays.stream(s.split(","))
                    .map(Integer::parseInt).toArray(Integer[]::new)).forEach(App::sink);
        }
    }

    private static int calculateSurfaceAreaOfExposedCubes() {
        //x,y,z
        int x = 0;
        int y = 0;
        int z = 0;
        AtomicInteger surfaceArea = new AtomicInteger(0);
        for (String cube : cubeSet) {
            x = Integer.parseInt(cube.split("\\[")[1].split(",")[0]);
            y = Integer.parseInt(cube.split(",")[1].trim());
            z = Integer.parseInt(cube.split(",")[2].split("\\]")[0].trim());
            surfaceArea.addAndGet(6);
            int finalX = x;
            int finalY = y;
            int finalZ = z;
            directions.stream()
                    .map(direction -> Arrays.toString(new Integer[]{finalX + direction[0], finalY + direction[1], finalZ + direction[2]}))
                    .filter(cubeSet::contains).forEach(s -> surfaceArea.decrementAndGet());

            cubesNotFullyInside.add(Arrays.toString(new Integer[]{x, y, z}));
        }
        return surfaceArea.get();
    }

    private static void calculateInnerSides(int surfaceArea) {
        AtomicInteger surfaceAreaWithoutInnerSides = new AtomicInteger(surfaceArea);
        Set<String> airPockets = new HashSet<>();
        AtomicInteger iterations = new AtomicInteger();

        cubesNotFullyInside.forEach((cube) -> {
            int x = Integer.parseInt(cube.split("\\[")[1].split(",")[0]);
            int y = Integer.parseInt(cube.split(",")[1].trim());
            int z = Integer.parseInt(cube.split(",")[2].split("\\]")[0].trim());

            Queue<String> explorationQueue = new LinkedList<>(); //empty cube spaces to explore

            scanForEmptySidesToExplore(x, y, z, explorationQueue);
            explorationQueue.forEach(toExplore -> {
                        Stack<String> stack = new Stack<>();
                        LinkedList<String> visited = new LinkedList<>();
                        LinkedList<String> exhausted = new LinkedList<>();
                        stack.push(toExplore);
                        AtomicBoolean innerCube = new AtomicBoolean(true);
                        AtomicInteger failsafe = new AtomicInteger(0);
                        while (true) {
                            if ( failsafe.get() > 1000) {
                                innerCube.set(false);
                                break;
                            }
                            iterations.getAndIncrement();

                            failsafe.getAndIncrement();
                            String currentCube = stack.pop();
                            int currentX = Integer.parseInt(currentCube.split("\\[")[1].split(",")[0]);
                            int currentY = Integer.parseInt(currentCube.split(",")[1].trim());
                            int currentZ = Integer.parseInt(currentCube.split(",")[2].split("\\]")[0].trim());
                            var airPocket = directions.stream()
                                    .map(direction -> Arrays.toString(new Integer[]{currentX + direction[0], currentY + direction[1], currentZ + direction[2]}))
                                    .filter(airPockets::contains).findFirst();
                            if(airPocket.isPresent()){
                                airPockets.addAll(visited);
                                break;
                            }

                            var res = directions.stream()
                                    .map(direction -> Arrays.toString(new Integer[]{currentX + direction[0], currentY + direction[1], currentZ + direction[2]}))
                                    .filter(direction -> !cubeSet.contains(direction) && !visited.contains(direction)).findFirst();

                            if (res.isPresent()) {
                                stack.push(res.get());
                                visited.add(res.get());
                                continue;
                            }

                            //backtrack
                            int backtrackCount = 2;
                            while (visited.size() - backtrackCount >= 0) {
                                String previousCube = visited.get(visited.size() - backtrackCount);
                                if (exhausted.contains(previousCube)) {
                                    break;
                                }
                                exhausted.add(previousCube);
                                int previousX = Integer.parseInt(previousCube.split("\\[")[1].split(",")[0]);
                                int previousY = Integer.parseInt(previousCube.split(",")[1].trim());
                                int previousZ = Integer.parseInt(previousCube.split(",")[2].split("\\]")[0].trim());

                                var backTrackRes = directions.stream()
                                        .map(direction -> Arrays.toString(new Integer[]{previousX + direction[0], previousY + direction[1], previousZ + direction[2]}))
                                        .filter(direction -> !cubeSet.contains(direction) && !visited.contains(direction)).findFirst();
                                if (backTrackRes.isPresent()) {
                                    stack.push(backTrackRes.get());
                                    visited.add(backTrackRes.get());
                                } else {
                                    backtrackCount++;
                                }
                            }
                            if (visited.size() - backtrackCount < 0) {
                                break;
                            }
                            if(stack.isEmpty()){
                                airPockets.addAll(visited);
                                break;
                            }
                        }
                        if (innerCube.get()) {
                            surfaceAreaWithoutInnerSides.getAndAdd(-1);
                        }
                    }
            );

        });
        System.out.println("iterations "+ iterations.get());

        System.out.println(" Surface area: " + surfaceAreaWithoutInnerSides.get());
    }

    private static void scanForEmptySidesToExplore(int x, int y, int z, Queue<String> explorationQueue) {
        directions.stream()
                .map(direction -> Arrays.toString(new Integer[]{x + direction[0], y + direction[1], z + direction[2]}))
                .filter(direction -> !cubeSet.contains(direction)).forEach(explorationQueue::add);
    }

    private static void sink(Integer[] cubes) {
        cubeSet.add(Arrays.toString(cubes));
    }
}
