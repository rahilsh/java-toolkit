package in.xml;

import com.google.common.collect.Lists;
import java.util.List;

public class ParseXML {
  public static void main(String[] args) {
    List<String> entities = Lists.newArrayList("<!ENTITY a1 10>", "<!ENTITY a2  a1;a2>");
    int count = 0;
    for (String entity : entities) {
      String removedEntityString =
          entity.substring(entity.lastIndexOf("<!ENTITY") + 8, entity.length() - 1);
      System.out.println("1=" + removedEntityString);
      if (removedEntityString.trim().split(" ").length >= 2) {
        System.out.println("2=" + removedEntityString.trim().split("[ ]+")[1]);
        String[] subEntities = removedEntityString.trim().split("[ ]+")[1].trim().split(";");
        count = count + subEntities.length;
      }
    }
    System.out.println(1 + " " + count);
  }
}