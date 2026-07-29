package com.rsh.jtoolkit.scratch;

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.rsh.jtoolkit.scratch.modules.PropertiesModule;

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
