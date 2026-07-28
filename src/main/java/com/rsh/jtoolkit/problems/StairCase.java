package com.rsh.jtoolkit.problems;
/* Goldman sach */
/* Problem Name is &&& Staircase &&& PLEASE DO NOT REMOVE THIS LINE. */
/*
== Instructions ==

** There is a staircase with 'n' number of steps. A child
** walks by and wants to climb up the stairs, starting at
** the bottom step and ascending to the top.

** Of course, the child wants to have fun, too, so instead
** of taking 1 step at a time, it will vary between taking
** either 1, 2 or 3 steps at a time.

** Please complete the 'countSteps' method below so that
** given 'n' number of steps it will return the number of
** unique combinations the child could traverse.

** An example would be countSteps(3) == 4:

** 1 1 1
** 2 1
** 1 2
** 3z
*/

public class StairCase {
  /**
   * Given n steps, returns the number of possible permutations to climb the staircase.
   *
   * <p>Returns 0 when the input n is <= 0.
   */
  public static Integer countSteps(Integer n) {
    if (n <= 0) {
      return 0;
    }
    return ways(n + 1);
  }

  static int ways(Integer n) {
    if (n <= 0) {
      return 0;
    }
    if (n == 1) {
      return 1;
    }
    return ways(n - 1) + ways(n - 2) + ways(n - 3);
  }

  /** Returns true if the tests pass. Otherwise, false. */
  public static boolean doTestsPass() {
    // todo: implement more tests if you'd like
    return countSteps(1) == 1
        && countSteps(0) == 0
        && countSteps(-5) == 0
        && countSteps(2) == 2
        && countSteps(3) == 4
        && countSteps(10) == 274
        && countSteps(4) == 7;
  }

  /** Execution entry point. */
  public static void main(String[] args) {
    // Run the tests
    if (doTestsPass()) {
      System.out.println("All tests pass");
    } else {
      System.out.println("Tests fail.");
    }

    // Try some examples
    for (Integer n = 1; n <= 50; n++) {
      Integer numberOfCombinations = countSteps(n);
      System.out.println(n + " steps => " + numberOfCombinations);
    }
  }
}
