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

@PrepareForTest({LdapQuery.class, AdminRoleMatcher.class})
public class AdminRoleMatcherTest extends TestBase {

  @Test
  public void shouldMatchAdminRole() throws IOException, LDAPException, ExecutionException {
    LdapQuery mockedQuery = mock(LdapQuery.class);
    AdminRoleMatcher adminRoleMatcher = new AdminRoleMatcher(mockedQuery);

    when(mockedQuery.getAdmins()).thenReturn(newHashSet("master1.treadmill", "master2.treadmill"));

    assertTrue(adminRoleMatcher.matches("host/master2.treadmill@TREADMILL", "role/admin"));
    verify(mockedQuery).getAdmins();
  }
}