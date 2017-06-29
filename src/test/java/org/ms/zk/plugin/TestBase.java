package org.ms.zk.plugin;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@PrepareForTest(Configuration.class)
public class TestBase {

  public static void mockConfigurationFile() {
    String dir = System.getProperty("user.dir");
    String testFilePath = dir + "/src/test/java/resources/configs-test.properties";

    PowerMockito.mockStatic(System.class);
    PowerMockito.when(System.getProperty("org.ms.zk.plugin.configuration")).thenReturn(testFilePath);

  }
}
