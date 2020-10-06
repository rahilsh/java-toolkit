package in.rsh.jutil.emoji;

// import com.vdurmont.emoji.EmojiParser;

public class RemoveEmoji {

  public static void main(String[] args) {

    StringBuilder sb = new StringBuilder();
    String input = "nice joke 😆😆😆 😛 😆☺️❤😛";
    // String input = "Name ❤️ lastname️️";
    // String input = "N° Documento";
    for (int i = 0; i < input.length(); i++) {
      if (i
          < (input.length()
              - 1)) { // Emojis are two characters long in java, e.g. a rocket emoji is
        // "\uD83D\uDE80";
        if (Character.isSurrogatePair(input.charAt(i), input.charAt(i + 1))) {
          i += 1; // also skip the second character of the emoji
          continue;
        }
      }
      sb.append(input.charAt(i));
      Math.abs(-1);
    }
    //  System.out.println("Emoji lib "+EmojiParser.removeAllEmojis(input));
    System.out.println("Original: " + input);
    System.out.println("Without surrogate pairs: " + sb);
    System.out.println("Regex 1: " + input.replaceAll("\\p{So}+", ""));
    System.out.println("Regex 2: " + input.replaceAll("[\uD83C-\uDBFF\uDC00-\uDFFF]+", ""));
  }
}
