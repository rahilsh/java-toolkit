import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.*;

public class DigitalSignatureTest {

  public static void main(String[] args) throws Exception {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    kpg.initialize(512);
    KeyPair keyPair = kpg.genKeyPair();

    byte[] data = "test".getBytes("UTF8");
    System.out.println(new BASE64Encoder().encode(keyPair.getPrivate().getEncoded()));
    System.out.println("public="+new BASE64Encoder().encode(keyPair.getPublic().getEncoded()));

    byte [] privatekey = new BASE64Decoder().decodeBuffer("MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAoui5tF8TjOZTpTsbe/Sy8x+9LTkE" +
            "BSSFrfvc4K1m4ajYJv3HdJZDQ8wONPZC0iZWc4QIGV8ErzQJn0R6XkRJOQIDAQABAkAReO559vyM" +
            "zyNHhHhlKhCLXOsYT5Yrywf/ahT9j/uJ1jrLenABh5zmYOuVM/evykpARpWstGrMZldUHEabCVZ1" +
            "AiEA7m3v7wMQeV3ZAWUubaG1wUPlvX9+g3WeaQ/GvW2/49MCIQCu6hFS//DZczszVxAYW4awQyVN" +
            "3k7N5Zf3zsSfbXETQwIhAK1ubIPID9ioaiw4pD/8y8+sCtX/glAvYy362s55oYYfAiA1Ih1JHT5I" +
            "j9x36AaXn5X2jS8TzGgb25aiTEkmNNSYiQIhAMOBTCsXWG794gmeHsXcBdBQvERRrxWt1MAtjwgR" +
            "8OLf");

    byte[] publickey=new BASE64Decoder().decodeBuffer("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIzREWnfS6fzjsO7GLloAeHgjPzOd441cUjUj0tLEXuj" +
            "8RfEIQsFS8+1q5WBuSZZPUGYOR4KX0ZWWMz7yVmhwzsCAwEAAQ==" +
            "Singature:D4WVkg9dnSlT1u+FzKjff1PySzz7XL4nsJPu5ki64AvMgleDcsCCbvzlHYMcRtUW56yuVb8d9Q58" +
            "CsMmgNTj0w==");

    new KeyPair(new PublicKey() {
        @Override
        public String getAlgorithm() {
            return null;
        }

        @Override
        public String getFormat() {
            return null;
        }

        @Override
        public byte[] getEncoded() {
            return new byte[0];
        }
    }, new PrivateKey() {
        @Override
        public String getAlgorithm() {
            return null;
        }

        @Override
        public String getFormat() {
            return null;
        }

        @Override
        public byte[] getEncoded() {
            return new byte[0];
        }
    });

    Signature sig = Signature.getInstance("MD5WithRSA");
    sig.initSign(keyPair.getPrivate());
    sig.update(data);
    byte[] signatureBytes = sig.sign();
    System.out.println("Singature:" + new BASE64Encoder().encode(signatureBytes));

    sig.initVerify(keyPair.getPublic());
    sig.update(data);

    System.out.println(sig.verify(signatureBytes));
  }
}
