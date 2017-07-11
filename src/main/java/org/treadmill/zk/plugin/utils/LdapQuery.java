package org.treadmill.zk.plugin.utils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.unboundid.ldap.sdk.*;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public class LdapQuery {

  Provider<LDAPInterface> ldapProvider;

  private static final Logger logger = getLogger(LdapQuery.class);

  @Inject
  public LdapQuery(Provider<LDAPInterface> ldapProvider) {
    this.ldapProvider = ldapProvider;
  }

  public Collection<Attribute> getAttributes(String baseDN, String filter) throws LDAPException, IOException {
    List<SearchResultEntry> searchEntries = ldapProvider.get().search(baseDN, SearchScope.ONE, filter).getSearchEntries();
    logger.info("Found {} attributes for baseDN={} and filter={}", searchEntries.size(), baseDN, filter);
    return searchEntries.get(0).getAttributes();
  }
}
