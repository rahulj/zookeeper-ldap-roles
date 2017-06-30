package org.ms.zk.plugin;


import com.unboundid.ldap.sdk.LDAPException;
import org.apache.zookeeper.server.auth.SASLAuthenticationProvider;
import org.slf4j.Logger;

import java.io.IOException;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;


public class KerberosAuthProvider extends SASLAuthenticationProvider {

  private static final Logger logger = getLogger(KerberosAuthProvider.class);

  @Override
  public boolean matches(String id, String aclExpr) {
    logger.info(format("matching id=%s, acl=%s", id, aclExpr));
    try {

      return AclRole.get(aclExpr).match(id);

    } catch (IOException | LDAPException e) {
      logger.warn("cannot authorize " + id, e);
      return false;
    }
  }
}
