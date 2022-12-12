import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Monkey {

  private final Long test;
  public Deque<Long> queue = new LinkedList<>();
  boolean factor;
  private int opValue;
  private int falseDestMonkey;
  private int trueDestMonkey;

  private AtomicInteger inspectCount = new AtomicInteger(0);

  public Monkey(
      List<Integer> initWorryLevels,
      int test,
      int opValue,
      boolean factor,
      int trueDestMonkey,
      int falseDestMonkey) {
    initWorryLevels.stream().map(Integer::longValue).forEach(queue::addLast);
    this.test = (long) test;
    this.factor = factor;
    this.opValue = opValue;
    this.falseDestMonkey = falseDestMonkey;
    this.trueDestMonkey = trueDestMonkey;
  }

  public void printQueue() {
    System.out.println(Arrays.toString(queue.toArray()));
  }

  public int getInspectCount() {
    return inspectCount.get();
  }

  public long[] inspect() {
    inspectCount.incrementAndGet();
    int destMonkey;
    Long item = queue.removeLast();
    item = item % (11 * 19 * 5 * 3 * 13 * 17 * 7 * 2);
    if (opValue == 1337) {
      item = item * item;
    } else {
      if (factor) {
        item = item * opValue;
      } else {
        item = item + opValue;
      }
    }

    if (item % test == 0) {
      destMonkey = trueDestMonkey;
    } else {
      destMonkey = falseDestMonkey;
    }
    return new long[] {(long) destMonkey, item};
  }

  public boolean isQueueEmpty() {
    return queue.isEmpty();
  }
}
