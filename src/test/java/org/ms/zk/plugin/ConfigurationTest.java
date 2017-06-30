package org.ms.zk.plugin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.ms.zk.plugin.Configuration.get;

@RunWith(PowerMockRunner.class)
public class ConfigurationTest extends TestBase {

  @Before
  public void setup() {
    mockConfiguration();
  }

  @Test
  public void shouldLoadBundleIfFileIsPassedAsSystemProperty() throws IOException {

    Configuration.loadBundle();

    assertEquals("ou=cells,ou=treadmill,{0}", get("base_dn_for_admin_role"));
  }

  @Test
  public void shouldReturnFormattedConfiguration() throws IOException {
    assertEquals("ou=cells,ou=treadmill,suffix", get("base_dn_for_admin_role", "suffix"));
  }
}