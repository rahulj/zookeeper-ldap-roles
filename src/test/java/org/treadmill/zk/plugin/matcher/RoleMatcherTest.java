package org.treadmill.zk.plugin.matcher;

import com.unboundid.ldap.sdk.LDAPException;
import org.junit.Test;
import org.treadmill.zk.plugin.TestBase;
import org.treadmill.zk.plugin.utils.LdapQuery;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class RoleMatcherTest extends TestBase {


  @Test
  public void shouldAlwaysMatchForReaderRole() throws IOException, LDAPException, ExecutionException {

    LdapQuery mockedQuery = mock(LdapQuery.class);
    RoleMatcher matcher = new RoleMatcher(mockedQuery);

    assertTrue(matcher.matchAcl("any principal", "readers"));
  }



}