package org.ms.zk.plugin;

import org.junit.Before;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static java.lang.System.getProperty;
import static java.lang.System.getenv;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest(Configuration.class)
public class TestBase {

  public static void mockConfiguration() {
    String dir = getProperty("user.dir");
    String testFilePath = dir + "/src/test/java/resources/configs-test.properties";

    mockStatic(System.class);
    when(getProperty("org.ms.zk.plugin.configuration")).thenReturn(testFilePath);

    mockEnv("TREADMILL_LDAP", "10.10.10.10:22389");
    mockEnv("TREADMILL_LDAP_USER", "cn=Manager,dc=local");
    mockEnv("TREADMILL_LDAP_SUFFIX", "dc=suffix");
    mockEnv("TREADMILL_CELL", "cellABC");

  }

  private static void mockEnv(String prop, String value) {
    when(getenv(prop)).thenReturn(value);
  }

  @Before
  public void setup() {
    mockConfiguration();
  }
}
