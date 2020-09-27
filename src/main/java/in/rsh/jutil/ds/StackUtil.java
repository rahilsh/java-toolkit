package in.rsh.jutil.ds;

import java.util.Iterator;
import java.util.Stack;

public class StackUtil {

  private static void removeFirst(Stack<String> stack, String toRemove) {
    for (Iterator<String> iterator = stack.iterator(); iterator.hasNext(); ) {
      if (iterator.next().equals(toRemove)) {
        iterator.remove();
        break;
      }
    }
  }

  private static void removeAllOccurrances(Stack<String> stack, String toRemove) {
    stack.removeIf(s -> s.equals(toRemove));
  }
}
