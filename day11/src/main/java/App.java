import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class App {
  public static void main(String[] args) throws IOException {
    System.out.println("Java");
    if (Objects.equals(System.getenv("part"), "part1")) {

      solve(20, true);
    } else {
      solve(10000, false);
    }
  }

  private static void solve(int rounds, boolean round1) throws IOException {
    Supplier<LongUnaryOperator> worryManager = () -> (i) -> i / 3;
    if (!round1) {
      worryManager = () -> (i) -> i % (11 * 19 * 5 * 3 * 13 * 17 * 7 * 2);
    }
    Supplier<LongUnaryOperator> finalWorryManager = worryManager;
    var res = Files.readString(Paths.get("input.txt")).split("\\n\\n");
    List<Monkey> newMonkeys = new ArrayList<>();

    for (String re : res) {
      var inLines = re.split("\n");
      var commaSeperated = inLines[1].split(":");
      int test = 0;
      List<Long> startingItems =
              Arrays.stream(commaSeperated[1].split(","))
                      .map(String::trim)
                      .map(Long::parseLong)
                      .toList();
      LongUnaryOperator operation;
      Pattern p = Pattern.compile("[0-9]+");
      Matcher m = p.matcher(inLines[2]);
      if (m.find()) {
        String op = m.group();
        Pattern p2 = Pattern.compile("[+*]");
        Matcher m2 = p2.matcher(inLines[2]);
        if (m2.find()) {
          String op2 = m2.group();
          if (op2.equals("+")) {
            operation = (i) -> i + Long.parseLong(op);
          } else {
            operation = (i) -> i * Long.parseLong(op);
          }
        } else {
          throw new RuntimeException("No operation found");
        }
      } else {
        operation = old -> old * old;
      }
      m = p.matcher(inLines[3]);
      if (m.find()) {
        String test2 = m.group();
        test = Integer.parseInt(test2);
      }
      int trueDestMonkey = 0;
      int falseDestMonkey = 0;
      m = p.matcher(inLines[4]);
      if (m.find()) {
        String trueDest = m.group();
        trueDestMonkey = Integer.parseInt(trueDest);

      }
      m = p.matcher(inLines[5]);
      if (m.find()) {
        String falseDest = m.group();
        falseDestMonkey = Integer.parseInt(falseDest);
      }
      newMonkeys.add(
              new Monkey(
                      startingItems,
                      test,
                      operation,
                      trueDestMonkey,
                      falseDestMonkey,
                      finalWorryManager));
    }

    for (int i = 1; i < rounds + 1; i++) {
      newMonkeys.forEach(
          monkey ->
              monkey.grabAndClearItems().stream()
                  .map(monkey::inspect)
                  .forEach(
                      inspectRes -> newMonkeys.get((int) inspectRes[0]).addItem(inspectRes[1])));
    }

    var resz =
        newMonkeys.stream()
            .map(Monkey::getInspectCount)
            .mapToLong(Integer::longValue)
            .sorted()
            .boxed()
            .skip(6)
            .reduce((a, b) -> a * b)
            .orElseThrow(() -> new IllegalArgumentException("No value present"));
    System.out.println(resz);
  }
}
