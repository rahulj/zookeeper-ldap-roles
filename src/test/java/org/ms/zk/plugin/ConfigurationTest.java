package org.ms.zk.plugin;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Configuration.class)
public class ConfigurationTest extends TestCase {

  @Test
  public void shouldLoadBundleIfFileIsPassedAsSystemProperty() throws IOException {
    String dir = System.getProperty("user.dir");
    String testFilePath = dir + "/src/test/java/resources/configs-test.properties";

    PowerMockito.mockStatic(System.class);
    PowerMockito.when(System.getProperty("org.ms.zk.plugin.configuration")).thenReturn(testFilePath);

    Configuration.loadBundle();

    assertEquals("file:///treadmill/roles/", Configuration.get("zookeeper_roles_acl_prefix"));
  }
}