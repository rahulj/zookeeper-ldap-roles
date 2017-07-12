package org.treadmill.zk.plugin.matcher.role;

import com.unboundid.ldap.sdk.LDAPException;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.treadmill.zk.plugin.TestBase;
import org.treadmill.zk.plugin.utils.LdapQuery;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest({LdapQuery.class, ServerRoleMatcher.class})
public class ServerRoleMatcherTest extends TestBase {

  @Test
  public void shouldMatchServerRole() throws IOException, LDAPException, ExecutionException {
    LdapQuery mockedQuery = mock(LdapQuery.class);
    ServerRoleMatcher serverRoleMatcher = new ServerRoleMatcher(mockedQuery);

    when(mockedQuery.getServers()).thenReturn(newHashSet("server1.treadmill", "server2.treadmill", "server3.treadmill"));

    assertTrue(serverRoleMatcher.matches("host/server2.treadmill@TREADMILL", "role/admin"));
    verify(mockedQuery).getServers();
  }
}
