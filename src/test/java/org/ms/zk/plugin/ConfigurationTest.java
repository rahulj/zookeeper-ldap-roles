package org.ms.zk.plugin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class ConfigurationTest extends TestBase {

  @Test
  public void shouldLoadBundleIfFileIsPassedAsSystemProperty() throws IOException {

    mockConfigurationFile();

    Configuration.loadBundle();

    assertEquals("file:///treadmill/roles/", Configuration.get("zookeeper_roles_acl_prefix"));
  }
}