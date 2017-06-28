package org.ms.zk.plugin;


import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.server.ServerCnxn;
import org.apache.zookeeper.server.auth.SASLAuthenticationProvider;
import org.slf4j.Logger;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;


public class KerberosAuthProvider extends SASLAuthenticationProvider {

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KerberosAuthProvider.class);

  @Override
  public KeeperException.Code handleAuthentication(ServerCnxn cnxn, byte[] authData) {
    String data = new String(authData, UTF_8);
    logger.info("handle auth for: " + data);
    return super.handleAuthentication(cnxn, authData);
  }

  @Override
  public boolean matches(String id, String aclExpr) {
    logger.info(format("matching id=%s, acl=%s", id, aclExpr));
    return super.matches(id, aclExpr);
  }


  @Override
  public boolean isValid(String id) {
    return super.isValid(id);
  }
}
