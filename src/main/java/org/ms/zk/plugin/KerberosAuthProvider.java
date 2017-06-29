package org.ms.zk.plugin;


import org.apache.zookeeper.server.auth.SASLAuthenticationProvider;
import org.slf4j.Logger;

import static java.lang.String.format;
import static org.ms.zk.plugin.AclRole.readers;
import static org.slf4j.LoggerFactory.getLogger;


public class KerberosAuthProvider extends SASLAuthenticationProvider {

  private static final Logger logger = getLogger(KerberosAuthProvider.class);

  @Override
  public boolean matches(String id, String aclExpr) {
    logger.info(format("matching id=%s, acl=%s", id, aclExpr));

    AclRole role = AclRole.get(aclExpr);

    return role == readers || RoleMatcher.matches(role, aclExpr);

  }
}
