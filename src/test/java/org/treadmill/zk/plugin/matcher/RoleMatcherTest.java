package org.treadmill.zk.plugin.matcher;

import com.google.common.collect.Sets;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSearchException;
import org.junit.Before;
import org.junit.Test;
import org.treadmill.zk.plugin.TestBase;
import org.treadmill.zk.plugin.utils.LdapQuery;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

public class RoleMatcherTest extends TestBase {
  LdapQuery mockedQuery;
  RoleMatcher matcher;

  @Before
  public void setup() throws IOException {
    mockedQuery = mock(LdapQuery.class);
    matcher = new RoleMatcher(mockedQuery);
  }

  @Test
  public void shouldAlwaysMatchForReaderRole() throws IOException, LDAPException, ExecutionException {
    assertTrue(matcher.matchAcl("any principal", "readers"));
  }

  @Test
  public void shouldReturnTrueIfAdminFoundInLdap() throws IOException, ExecutionException, LDAPSearchException {
    when(mockedQuery.searchAdmins()).thenReturn(Sets.newHashSet("some-host", "some-other-host"));

    assertTrue(matcher.matchAcl("host/some-host", "admins"));

    verify(mockedQuery).searchAdmins();
  }

  @Test
  public void shouldReturnTrueIfServerFoundInLdap() throws IOException, ExecutionException, LDAPSearchException {
    when(mockedQuery.searchServers()).thenReturn(Sets.newHashSet("some-host", "some-other-host"));

    assertTrue(matcher.matchAcl("host/some-host", "servers"));
    verify(mockedQuery).searchServers();
  }

  @Test
  public void shouldReturnFalseIfAdminNotFound() throws IOException, ExecutionException, LDAPSearchException {
    when(mockedQuery.searchAdmins()).thenReturn(Collections.<String>emptySet());

    assertFalse(matcher.matchAcl("host/some-host", "admins"));

    verify(mockedQuery).searchAdmins();
  }

  @Test
  public void shouldReturnFalseIfServersNotFound() throws IOException, ExecutionException, LDAPSearchException {
    when(mockedQuery.searchServers()).thenReturn(Collections.<String>emptySet());

    assertFalse(matcher.matchAcl("host/some-host", "servers"));

    verify(mockedQuery).searchServers();
  }


}