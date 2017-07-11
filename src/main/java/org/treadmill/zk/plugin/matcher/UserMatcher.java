package org.treadmill.zk.plugin.matcher;

import com.google.inject.Singleton;
import com.unboundid.ldap.sdk.LDAPException;
import org.slf4j.Logger;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public class UserMatcher extends Matcher {

  private static final Logger logger = getLogger(UserMatcher.class);

  @Override
  public boolean matchAcl(String id, String aclExpr) throws IOException, LDAPException {
    logger.info("UserMatcher {}, id={}, aclExpr={}", this, id, aclExpr);
    return id.split("@")[0].equals(aclExpr);
  }
}
