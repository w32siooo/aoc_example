import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

public class App {
  private static int cycles = 0;
  private static int signalStrength = 1;
  private static int sum = 0;
  private static int part = 1;

  public static void main(String[] args) throws IOException {

    System.out.println("Java");
    if (Objects.equals(System.getenv("part"), "part1")) {
      cycles = 1;
      part = 1;
      solve();
      System.out.println(sum);
    } else {
      part = 2;
      cycles = 0;
      solve();
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
    if(part==2) drawX();
    cycles++;
  }

  public static void sink(String[] input) {

    if (input[0].equals("addx")) {
      incrementCycle();
      incrementCycle();
      signalStrength += Integer.parseInt(input[1]);
    } else if (input[0].equals("noop")) {
      incrementCycle();
    }
  }

  private static void drawX() {
    int xToDraw = cycles % 40;
    if (cycles % 40 == 0) {
      System.out.print("\n");
    }
    if (signalStrength - 1 == xToDraw
        || signalStrength == xToDraw
        || signalStrength + 1 == xToDraw) {
      System.out.print("%");
    } else {
      System.out.print(" ");
    }
  }

  private static void solve() throws IOException {

    try (Stream<String> lines = Files.lines(new File("input.txt").toPath())) {
      lines.map(s -> s.split(" ")).forEach(App::sink);
    }
  }
}
