package org.treadmill.zk.plugin;


import com.google.inject.Injector;
import org.apache.zookeeper.server.auth.SASLAuthenticationProvider;
import org.slf4j.Logger;
import org.treadmill.zk.plugin.matcher.RoleMatcher;
import org.treadmill.zk.plugin.matcher.UserMatcher;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;


public class KerberosAuthProvider extends SASLAuthenticationProvider {

  private static final Logger logger = getLogger(KerberosAuthProvider.class);

  RoleMatcher roleMatcher;

  UserMatcher userMatcher;


  public KerberosAuthProvider() {
    Injector injector = KrbGuiceModule.injector();
    roleMatcher = injector.getInstance(RoleMatcher.class);
    userMatcher = injector.getInstance(UserMatcher.class);
  }

  @Override
  public boolean matches(String id, String aclExpr) {
    logger.info(format("matching id=%s, acl=%s", id, aclExpr));
    try {
      String[] acl = aclExpr.split("/", 2);

      return acl[0].equals("role") ? roleMatcher.matches(id, acl[1]) : userMatcher.matches(id, aclExpr);
    } catch (IOException | ExecutionException e) {
      logger.error("cannot authorize {} with acl {}", id, aclExpr, e);
      return false;
    }
  }
}
