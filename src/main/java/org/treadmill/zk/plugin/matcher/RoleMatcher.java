package org.treadmill.zk.plugin.matcher;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unboundid.ldap.sdk.LDAPException;
import org.slf4j.Logger;
import org.treadmill.zk.plugin.utils.LdapQuery;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.slf4j.LoggerFactory.getLogger;
import static org.treadmill.zk.plugin.utils.Configuration.get;

@Singleton
public class RoleMatcher extends Matcher {

  private static final Logger logger = getLogger(RoleMatcher.class);
  private LoadingCache<String, Set<String>> cache;


  @Inject
  public RoleMatcher(LdapQuery ldapQuery) throws IOException {
    setupCache(ldapQuery);
  }

  private void setupCache(final LdapQuery ldapQuery) throws IOException {
    cache = CacheBuilder.from(get("cache_spec"))
      .build(new CacheLoader<String, Set<String>>() {
        @Override
        public Set<String> load(String key) throws Exception {
          logger.info("querying LDAP for {}", key);
          return "servers".equals(key) ? ldapQuery.searchServers() : ldapQuery.searchAdmins();
        }
      });
  }

  @Override
  public boolean matchAcl(String principal, String role) throws IOException, LDAPException, ExecutionException {
    if ("readers".equals(role)) return true;

    Set<String> members = this.cache.get(role);
    return members != null && members.contains(principal.replace("host/",""));
  }
}
