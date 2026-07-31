package com.rsh.jtoolkit.emoji;

/** Utility for stripping emoji (and emoji modifier) characters from text. */
public final class EmojiUtil {

  private EmojiUtil() {}

  /**
   * Returns a copy of {@code input} with emoji characters removed.
   *
   * <p>This removes characters in the supplementary emoji planes, {@code OTHER_SYMBOL} characters
   * (such as {@code ❤} and {@code ☺}), variation selectors, zero-width joiners and keycap
   * combiners. Regular letters, digits, punctuation and whitespace are preserved.
   *
   * @param input the text to clean; may be {@code null}
   * @return the cleaned text, or {@code null} if {@code input} was {@code null}
   */
  public static String removeEmojis(String input) {
    if (input == null) {
      return null;
    }
    StringBuilder sb = new StringBuilder(input.length());
    input
        .codePoints()
        .forEach(
            cp -> {
              if (!isEmoji(cp)) {
                sb.appendCodePoint(cp);
              }
            });
    return sb.toString();
  }

  private static boolean isEmoji(int codePoint) {
    if (codePoint >= 0x1F000) {
      // Supplementary symbol/emoji blocks (emoticons, transport, symbols & pictographs, ...)
      return true;
    }
    if (Character.getType(codePoint) == Character.OTHER_SYMBOL) {
      // BMP pictographic symbols, e.g. ❤ (U+2764), ☺ (U+263A), ☀ (U+2600)
      return true;
    }
    // Variation selectors, zero-width joiner, keycap combining enclosing mark
    return (codePoint >= 0xFE00 && codePoint <= 0xFE0F)
        || codePoint == 0x200D
        || codePoint == 0x20E3;
  }
}
