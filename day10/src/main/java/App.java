import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

public class App {
  public static int cycles = 0;
  public static String[][] array = new String[6][40];

  static int signalStrength = 1;
  static int sum = 0;

  public static void main(String[] args) throws IOException {

    System.out.println("Java");
    if (Objects.equals(System.getenv("part"), "part1")) {
      cycles = 1;
      solve();
      System.out.println(sum);
    } else {
      cycles = 0;
      solve();
      printMatrix(array);
    }
  }

  public static void incrementCycle() {

    switch (cycles) {
      case 20 -> sum += signalStrength * 20;
      case 60 -> sum += signalStrength * 60;
      case 100 -> sum += signalStrength * 100;
      case 140 -> sum += signalStrength * 140;
      case 180 -> sum += signalStrength * 180;
      case 220 -> sum += signalStrength * 220;
    }
    drawX();
    cycles++;
  }

  public static void solve(String[] input) {

    if (input[0].equals("addx")) {
      incrementCycle();
      incrementCycle();
      signalStrength += Integer.parseInt(input[1]);
    } else if (input[0].equals("noop")) {
      incrementCycle();
    }
  }

  public static void printMatrix(String[][] matrix) {
    for (String[] strings : matrix) {
      for (String string : strings) {
        System.out.print(Objects.requireNonNullElse(string, " "));
      }
      System.out.println();
    }
  }

  private static void drawX() {
    if (cycles < 240) {
      int xToDraw = cycles;
      if (cycles >= 40) {
        xToDraw = cycles % 40;
      }
      int yToDraw = cycles / 40;

      if (signalStrength == xToDraw
          || signalStrength - 1 == xToDraw
          || signalStrength + 1 == xToDraw) {
        array[yToDraw][xToDraw] = "0";
      }
    }
  }

  private static void solve() throws IOException {

    try (Stream<String> lines = Files.lines(new File("input.txt").toPath())) {
      lines.map(s -> s.split(" "))
              .forEach(App::solve);
    }
  }
}
