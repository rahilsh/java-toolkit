package in.r.util;

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import in.r.util.modules.PropertiesModule;

public class Main {

  static Injector getInjector() {
    Injector injector =
        createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                install(new PropertiesModule());
              }
            });
    return injector;
  }
}
