package org.treadmill.zk.plugin.matcher.role;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unboundid.ldap.sdk.LDAPException;
import org.slf4j.Logger;
import org.treadmill.zk.plugin.matcher.Matcher;
import org.treadmill.zk.plugin.utils.LdapQuery;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public class AdminRoleMatcher extends Matcher {

  private static final Logger logger = getLogger(AdminRoleMatcher.class);

  LdapQuery ldapQuery;

  @Inject
  public AdminRoleMatcher(LdapQuery ldapQuery) {
    this.ldapQuery = ldapQuery;
  }

  @Override
  public boolean matchAcl(String id, String aclExpr) throws IOException, LDAPException, ExecutionException {
    logger.info("matching id={} against aclExpr={}, ldapQuery={}", id, aclExpr, ldapQuery);

    String hostName = id.split("@")[0].replace(HOST_PREFIX, "");

    return ldapQuery.getAdmins().stream().anyMatch(
      a -> a.equals(hostName));
  }
}