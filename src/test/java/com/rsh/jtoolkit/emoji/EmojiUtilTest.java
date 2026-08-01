package com.rsh.jtoolkit.emoji;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class EmojiUtilTest {

  @Test
  void removesSupplementaryPlaneEmoji() {
    assertEquals("ab", EmojiUtil.removeEmojis("a\uD83D\uDE00b")); // a😀b
  }

  @Test
  void removesBmpPictographicSymbols() {
    assertEquals("hi ", EmojiUtil.removeEmojis("hi \u2764")); // hi ❤
    assertEquals("ok", EmojiUtil.removeEmojis("o\u263Ak")); // o☺k
  }

  @Test
  void stripsVariationSelectorAndZwj() {
    // ❤️ = U+2764 U+FE0F ; both should be removed
    assertEquals("x", EmojiUtil.removeEmojis("x\u2764\uFE0F"));
  }

  @Test
  void leavesPlainTextUntouched() {
    assertEquals("Hello, World! 123", EmojiUtil.removeEmojis("Hello, World! 123"));
  }

  @Test
  void returnsNullForNull() {
    assertNull(EmojiUtil.removeEmojis(null));
  }
}
