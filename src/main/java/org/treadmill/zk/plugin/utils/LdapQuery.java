package org.treadmill.zk.plugin.utils;

import com.google.common.cache.*;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.unboundid.ldap.sdk.*;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;
import static org.treadmill.zk.plugin.utils.Configuration.get;

@Singleton
public class LdapQuery {

  private static final Logger logger = getLogger(LdapQuery.class);
  Provider<LDAPInterface> ldapProvider;
  LoadingCache<String, Set<String>> cache;

  @Inject
  public LdapQuery(Provider<LDAPInterface> ldapProvider) throws IOException {
    this.ldapProvider = ldapProvider;

    RemovalListener<? super String, ? super Set<String>> removalListener =
      (RemovalNotification<String, Set<String>> notification) -> logger.info("removing {} from cache", notification.getKey());

    this.cache = CacheBuilder.from(get("cache_spec"))
      .removalListener(removalListener)
      .build(new CacheLoader<String, Set<String>>() {
        @Override
        public Set<String> load(String key) throws Exception {
          logger.info("querying LDAP for {}", key);
          if ("servers".equals(key)) {
            return searchServers();
          } else {
            return searchAdmins();
          }
        }
      });
  }

  public Set<String> getServers() throws ExecutionException {
    return cache.get("servers");
  }

  public Set<String> getAdmins() throws ExecutionException {
    return cache.get("admin");
  }

  private Set<String> searchServers() throws IOException, LDAPSearchException {
    String baseDN = get("base_dn_for_server_role");
    String filter = get("filter_for_server_role");
    List<SearchResultEntry> searchEntries = ldapProvider.get().search(baseDN, SearchScope.ONE, filter).getSearchEntries();
    return searchEntries.stream().map(s -> s.getAttribute("server").getValue()).collect(Collectors.toSet());
  }

  private Set<String> searchAdmins() throws IOException, LDAPSearchException {
    String baseDN = get("base_dn_for_admin_role");
    String filter = get("filter_for_admin_role");
    SearchResultEntry searchResultEntry = ldapProvider.get().searchForEntry(baseDN, SearchScope.ONE, filter);

    return searchResultEntry.getAttributes().stream()
      .filter(a -> a.getName().startsWith("master-hostname;"))
      .map(Attribute::getValue).collect(Collectors.toSet());
  }

}
