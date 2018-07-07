package in.keyStore;

import java.io.FileInputStream;
import java.security.KeyStore;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class KeystoreTest {
  public static void main(String[] args) {
    KeyStore ks = null;
    try {
      ks = KeyStore.getInstance(KeyStore.getDefaultType());
      // ks.load(KeystoreTest.class.getResourceAsStream("/Users/rahil.r/Documents/ks.jks"),
      // "Ggh98#sSsdT$e#asG56".toCharArray());
      // ks.load(new ClassPathResource("Users/rahil.r/Documents/ks.jks").getInputStream(),
      // "directi".toCharArray());
      //      ks.load(
      //          new
      // ClassPathResource(Resources.getResource("/Users/rahil.r/Documents/ks.jks").getFile())
      //              .getInputStream(),
      //          "directi".toCharArray());
      PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();

      configurer.setLocations(
          new Resource[] {
            new FileSystemResource("/Users/rahil.r/Documents/ks.jks"),
          });
      ks.load(new FileInputStream("/Users/rahil.r/Documents/ks.jks"), "directi".toCharArray());

      System.out.println(ks.aliases().nextElement());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
