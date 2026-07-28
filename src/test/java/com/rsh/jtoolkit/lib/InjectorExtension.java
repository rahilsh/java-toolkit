package com.rsh.jtoolkit.lib;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

public class InjectorExtension implements TestInstancePostProcessor {
  private static final Injector INJECTOR = Guice.createInjector();

  @Override
  public void postProcessTestInstance(Object o, ExtensionContext extensionContext) {
    INJECTOR.injectMembers(o);
  }
}
