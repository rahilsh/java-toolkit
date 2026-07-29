package com.rsh.jtoolkit.scratch.problems;

/** Small combinatorial helpers. */
public final class Combinatorics {

  private Combinatorics() {}

  /**
   * Counts the number of distinct ways to climb a staircase of {@code steps} steps when each move
   * may cover 1, 2 or 3 steps.
   *
   * <p>Examples: {@code countStaircaseWays(3) == 4}, {@code countStaircaseWays(4) == 7},
   * {@code countStaircaseWays(10) == 274}. Returns {@code 0} for {@code steps <= 0}.
   */
  public static long countStaircaseWays(int steps) {
    if (steps <= 0) {
      return 0;
    }
    long a = 1; // ways(0)
    long b = 1; // ways(1)
    long c = 2; // ways(2)
    if (steps == 1) {
      return b;
    }
    if (steps == 2) {
      return c;
    }
    long current = c;
    for (int n = 3; n <= steps; n++) {
      current = a + b + c;
      a = b;
      b = c;
      c = current;
    }
    return current;
  }
}
