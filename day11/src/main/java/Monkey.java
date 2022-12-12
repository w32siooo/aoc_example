import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;

public class Monkey {
  private final Long test;
  private Queue<Long> itemList;
  private final LongUnaryOperator operation;
  private final int falseDestMonkey;
  private final int trueDestMonkey;
  private final AtomicInteger inspectCount = new AtomicInteger(0);
  private final Supplier<LongUnaryOperator> worryManager;

  public Monkey(
      List<Long> initWorryLevels,
      int test,
      LongUnaryOperator operation,
      int trueDestMonkey,
      int falseDestMonkey,
      Supplier<LongUnaryOperator> worryManager) {
    this.itemList = new LinkedList<>(initWorryLevels);
    this.test = (long) test;
    this.operation = operation;
    this.falseDestMonkey = falseDestMonkey;
    this.trueDestMonkey = trueDestMonkey;
    this.worryManager = worryManager;
  }

  public int getInspectCount() {
    return inspectCount.get();
  }

  public void addItem(Long item) {
    itemList.add(item);
  }

  public long[] inspect(long itemIn) {
    inspectCount.incrementAndGet();
    long item = worryManager.get().applyAsLong(operation.applyAsLong(itemIn));
    return new long[] {item % test == 0 ? trueDestMonkey : falseDestMonkey, item};
  }

  public Queue<Long> grabAndClearItems() {
    Queue<Long> res = new LinkedList<>(itemList);
    itemList = new LinkedList<>();
    return res;
  }

}
