package org.ms.zk.plugin;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;

import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

public class Configuration {
  static PropertyResourceBundle bundle;


  static void loadBundle() throws IOException {
    String propFile = getProperty("org.ms.zk.plugin.configuration");
    if (propFile == null) {
      throw new IllegalArgumentException("missing system property org.ms.zk.plugin.configuration");
    }
    bundle = new PropertyResourceBundle(new FileInputStream(propFile));
  }

  public static String get(String key, String... args) throws IOException {
    if (bundle == null) loadBundle();

    return format(bundle.getString(key), args);
  }
}
