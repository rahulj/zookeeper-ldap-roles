package org.ms.zk.plugin;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AclRoleTest {

  @Test
  public void shouldParseReadersRoleFromAcl() {
    assertEquals(AclRole.readers, AclRole.get("file:///treadmill/roles/readers"));
  }

  @Test
  public void shouldParseAdminRoleFromAcl() {
    assertEquals(AclRole.admin, AclRole.get("file:///treadmill/roles/admin"));
  }

  @Test
  public void shouldParseServersRoleFromAcl() {
    assertEquals(AclRole.servers, AclRole.get("file:///treadmill/roles/servers"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRaiseExceptionForUnknownRole() {
    AclRole.get("file:///treadmill/roles/random");
  }

  @Test
  public void shouldBeNullForWrongPrefix() {
    assertNull(AclRole.get("file:///treadmill/random/readers"));
  }

}