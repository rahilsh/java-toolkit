package in.future;

import java.util.Iterator;
import java.util.Stack;

public class ProcessClass {
  public static void main(String[] args) {
    Stack<String> stack = new Stack<>();
    stack.add("a");
    stack.add("e");
    stack.add("b");
    stack.add("k");
    stack.add("b");
    stack.add("c");
    stack.add("l");

    System.out.println("Original stack: " + stack);
    remove(stack, "b");
    System.out.println("Modified stack: " + stack);
  }

  private static void remove(Stack<String> stack, String toRemove) {
    for (Iterator<String> iterator = stack.iterator(); iterator.hasNext(); ) {
      if (iterator.next().equals(toRemove)) {
        iterator.remove();
        break;
      }
    }
  }
  //    int indexOfToRemove = stack.lastIndexOf(toRemove);
  //    if (indexOfToRemove == -1) {
  //      return;
  //    }
  //    Stack<String> tempStack = new Stack<>();
  //    int originalSize = stack.size();
  //    for (int i = 0; i < originalSize - indexOfToRemove - 1; i++) {
  //      tempStack.push(stack.pop());
  //    }
  //    stack.pop();
  //    System.out.println("Temporary stack: " + tempStack);
  //    while (!tempStack.empty()) {
  //      stack.push(tempStack.pop());
  //    }
}
