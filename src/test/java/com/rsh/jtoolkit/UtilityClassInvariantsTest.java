package com.rsh.jtoolkit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rsh.jtoolkit.collection.ListUtil;
import com.rsh.jtoolkit.collection.SetUtil;
import com.rsh.jtoolkit.email.EmailUtil;
import com.rsh.jtoolkit.emoji.EmojiUtil;
import com.rsh.jtoolkit.future.FutureUtil;
import com.rsh.jtoolkit.ip.IPUtil;
import com.rsh.jtoolkit.json.JsonUtil;
import com.rsh.jtoolkit.json.ReadJsonFile;
import com.rsh.jtoolkit.lang.ObjectUtil;
import com.rsh.jtoolkit.pdf.ExtractAttachments;
import com.rsh.jtoolkit.pdf.HTMLToPDF;
import com.rsh.jtoolkit.pdf.PdfUtil;
import com.rsh.jtoolkit.primitive.ShortUtil;
import com.rsh.jtoolkit.stream.StreamUtil;
import com.rsh.jtoolkit.time.DateUtil;
import com.rsh.jtoolkit.time.Time;
import com.rsh.jtoolkit.zip.ZipUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * Verifies that all static-only utility classes are non-instantiable (single private no-arg
 * constructor), and exercises that constructor for coverage.
 */
class UtilityClassInvariantsTest {

  static final class UtilityClasses implements ArgumentsProvider {
    @Override
    public java.util.stream.Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return java.util.stream.Stream.of(
              ListUtil.class,
              SetUtil.class,
              EmailUtil.class,
              EmojiUtil.class,
              FutureUtil.class,
              IPUtil.class,
              JsonUtil.class,
              ReadJsonFile.class,
              ObjectUtil.class,
              ExtractAttachments.class,
              HTMLToPDF.class,
              PdfUtil.class,
              ShortUtil.class,
              StreamUtil.class,
              DateUtil.class,
              Time.class,
              ZipUtil.class)
          .map(Arguments::of);
    }
  }

  @ParameterizedTest
  @ArgumentsSource(UtilityClasses.class)
  void utilityClassHasSinglePrivateConstructor(Class<?> type) throws Exception {
    Constructor<?>[] constructors = type.getDeclaredConstructors();
    assertEquals(1, constructors.length, type + " should have exactly one constructor");

    Constructor<?> constructor = constructors[0];
    assertEquals(
        0, constructor.getParameterCount(), type + " constructor should take no arguments");
    assertTrue(
        Modifier.isPrivate(constructor.getModifiers()), type + " constructor should be private");

    // Invoke it (covers the private constructor line).
    constructor.setAccessible(true);
    constructor.newInstance();
  }
}
