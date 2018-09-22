package in.problems;
/* Goldman sach */
/* Problem Name is &&& Pangram &&& PLEASE DO NOT REMOVE THIS LINE. */

/**
 * Pangram Detector
 *
 * <p>The sentence "The quick brown fox jumps over the lazy dog" contains every single letter in the
 * alphabet. Such sentences are called pangrams.
 *
 * <p>Write a function findMissingLetters, which takes a String `sentence`, and returns all the
 * letters it is missing (which prevent it from being a pangram). You should ignore the case of the
 * letters in sentence, and your return should be all lower case letters, in alphabetical order. You
 * should also ignore all non US-ASCII characters.
 */
public class PanagramDetector {

  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

  /*
   * Finds the letters of the alphabet not included in the input string
   *
   * @param sentence a string to examine
   * @return a string made up of the missing letters of the alphabet in sorted order
   */
  public String findMissingLetters(String sentence) {

    if (sentence == null && sentence.isEmpty()) {
      return "";
    }
    String output = "";
    for (int i = 0; i < ALPHABET.length(); i++) {
      if (!sentence.toLowerCase().contains("" + ALPHABET.charAt(i))) {
        output = output + ALPHABET.charAt(i);
      }
    }
    return output;
  }

  public static void main(String[] args) {
    PanagramDetector pd = new PanagramDetector();
    boolean success = true;
    success =
        success && "".equals(pd.findMissingLetters("The quick brown fox jumps over the lazy dog"));
    success =
        success
            && "bfgjkvz"
                .equals(
                    pd.findMissingLetters(
                        "The slow purple oryx meanders past the quiescent canine"));
    success = success && "cdfjklmopqruvxyz".equals(pd.findMissingLetters("We hates Bagginses!"));
    success = success && "abcdefghijklmnopqrstuvwxyz".equals(pd.findMissingLetters(""));
    if (success) {
      System.out.println("All tests passed.");
    } else {
      System.out.println("At least one of your tests failed.");
    }
  }
}
