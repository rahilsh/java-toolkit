package in.r.util.modules;

import com.google.common.base.Strings;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.commons.configuration2.ConfigurationConverter;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesModule extends AbstractModule {

  private static final String CONFIG_FILE_JAR = "config/settings.properties";
  private static final String CONFIG_FILE_SYSTEM = "/etc/commons.properties";
  private static final String CONFIG_FILE_APPLICATION =
      String.format("/etc/%1$s/%1$s.properties", System.getProperty("service.name"));
  private List<String> filePaths;

  public PropertiesModule() {
    this(CONFIG_FILE_JAR, CONFIG_FILE_SYSTEM, CONFIG_FILE_APPLICATION);
  }

  private PropertiesModule(String... filePaths) {
    this.filePaths = Arrays.asList(filePaths);
  }

  protected void configure() {
    String env = System.getProperty("env");
    if (Strings.isNullOrEmpty(env)) {
      env = "dev";
      System.setProperty("env", env);
    }
    bindConstant().annotatedWith(Names.named("system.env")).to(env);
    Properties properties = new Properties();
    for (String path : filePaths) {
      properties.putAll(readProperties(path));
    }
    Names.bindProperties(binder(), properties);
  }

  private Properties readProperties(String configFile) {
    Properties properties = new Properties();
    try {
      Configurations configs = new Configurations();
      PropertiesConfiguration config = configs.properties(configFile);
      properties = ConfigurationConverter.getProperties(config);
      getLogger().info("Included properties from {}", configFile);
    } catch (ConfigurationException ce) {
      if (getLogger().isDebugEnabled()) {
        getLogger().debug("Could not load properties from {}", configFile, ce);
      } else {
        getLogger().info("Could not load properties from {}", configFile);
      }
    }
    return properties;
  }

  private Logger getLogger() {
    return LoggerFactory.getLogger(this.getClass());
  }
}
