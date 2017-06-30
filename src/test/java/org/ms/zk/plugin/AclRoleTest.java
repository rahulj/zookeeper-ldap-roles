package org.ms.zk.plugin;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AclRoleTest extends TestBase {

  @Test
  public void shouldParseReadersRoleFromAcl() throws IOException {
    assertEquals(AclRole.readers, AclRole.get("file:///treadmill/roles/readers"));
  }

  @Test
  public void shouldParseAdminRoleFromAcl() throws IOException {
    assertEquals(AclRole.admin, AclRole.get("file:///treadmill/roles/admin"));
  }

  @Test
  public void shouldParseServersRoleFromAcl() throws IOException {
    assertEquals(AclRole.servers, AclRole.get("file:///treadmill/roles/servers"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRaiseExceptionForUnknownRole() throws IOException {
    AclRole.get("file:///treadmill/roles/random");
  }

  @Test
  public void shouldBeNullForWrongPrefix() throws IOException {
    assertNull(AclRole.get("file:///treadmill/random/readers"));
  }

}