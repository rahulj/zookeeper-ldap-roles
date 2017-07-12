package org.treadmill.zk.plugin.utils;

import com.google.inject.Provider;
import com.unboundid.ldap.sdk.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.treadmill.zk.plugin.TestBase;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LDAPConnection.class, SearchResult.class, LdapQuery.class, Configuration.class})
public class LdapQueryTest extends TestBase {

  @Test
  @Ignore
  public void ifConnectedItShouldSearchLDAP() throws Exception {
    String baseDN = "ou=cells,ou=treadmill,dc=local";
    String filter = "(objectClass=tmCell)";
    Attribute attribute = new Attribute("master-hostname", "master1.treadmill");

    LDAPConnection mockConnection = mock(LDAPConnection.class);
    Provider<LDAPInterface> mockProvider = mock(Provider.class);
    when(mockProvider.get()).thenReturn(mockConnection);
    SearchResult mockSearchResult = mock(SearchResult.class);
    List<SearchResultEntry> searchResultEntry = singletonList(new SearchResultEntry("cn=Manager,dc=local", new Attribute[]{attribute}));

    when(mockConnection.search(baseDN, SearchScope.ONE, filter)).thenReturn(mockSearchResult);
    when(mockSearchResult.getSearchEntries()).thenReturn(searchResultEntry);

  }
}