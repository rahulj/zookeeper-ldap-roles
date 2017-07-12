package org.treadmill.zk.plugin;


import com.google.inject.Injector;
import com.unboundid.ldap.sdk.LDAPException;
import org.apache.zookeeper.server.auth.SASLAuthenticationProvider;
import org.slf4j.Logger;
import org.treadmill.zk.plugin.matcher.MatcherFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;


public class KerberosAuthProvider extends SASLAuthenticationProvider {

  private static final Logger logger = getLogger(KerberosAuthProvider.class);
  MatcherFactory factory;

  public KerberosAuthProvider() {
    Injector injector = KrbGuiceModule.injector();
    factory = injector.getInstance(MatcherFactory.class);
  }

  @Override
  public boolean matches(String id, String aclExpr) {
    logger.info(format("matching id=%s, acl=%s", id, aclExpr));
    try {
      return factory.getMatcher(aclExpr).matches(id, aclExpr);
    } catch (IOException | LDAPException | ExecutionException e) {
      logger.warn("cannot authorize " + id, e);
      return false;
    }
  }
}
