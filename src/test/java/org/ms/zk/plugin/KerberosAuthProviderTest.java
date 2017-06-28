package org.ms.zk.plugin;

import org.junit.Test;
import org.ms.zk.plugin.KerberosAuthProvider;

import static org.junit.Assert.assertTrue;


public class KerberosAuthProviderTest {

  @Test
  public void testIfValid() {
    KerberosAuthProvider provider = new KerberosAuthProvider();
    assertTrue(provider.isValid("host/hostname@domain"));

  }
}