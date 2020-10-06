package in.rsh.jutil;

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import in.rsh.jutil.modules.PropertiesModule;

public class InjectorProvider {

  static Injector getInjector() {
    return createInjector(
        new AbstractModule() {
          @Override
          protected void configure() {
            install(new PropertiesModule());
          }
        });
  }
}
