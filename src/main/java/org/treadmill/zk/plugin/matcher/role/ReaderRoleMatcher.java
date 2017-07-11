package org.treadmill.zk.plugin.matcher.role;

import com.google.inject.Singleton;
import com.unboundid.ldap.sdk.LDAPException;
import org.treadmill.zk.plugin.matcher.Matcher;

import java.io.IOException;

@Singleton
public class ReaderRoleMatcher extends Matcher {

  @Override
  public boolean matchAcl(String id, String aclExpr) throws IOException, LDAPException {
    return true; // everyone is in readers role
  }
}
