package com.rsh.jtoolkit.scratch.problems;

/** Helpers for working with pangrams (sentences using every letter of the alphabet). */
public final class PangramUtil {

  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

  private PangramUtil() {}

  /**
   * Returns the lowercase letters of the English alphabet that are missing from {@code sentence},
   * in alphabetical order. Case is ignored. A {@code null} or empty sentence is missing every
   * letter.
   */
  public static String findMissingLetters(String sentence) {
    if (sentence == null || sentence.isEmpty()) {
      return ALPHABET;
    }
    String lower = sentence.toLowerCase();
    StringBuilder missing = new StringBuilder();
    for (int i = 0; i < ALPHABET.length(); i++) {
      char c = ALPHABET.charAt(i);
      if (lower.indexOf(c) < 0) {
        missing.append(c);
      }
    }
    return missing.toString();
  }

  /** Returns {@code true} if {@code sentence} contains every letter of the English alphabet. */
  public static boolean isPangram(String sentence) {
    return findMissingLetters(sentence).isEmpty();
  }
}
