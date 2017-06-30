package org.ms.zk.plugin.matcher;

import com.unboundid.ldap.sdk.LDAPException;

import java.io.IOException;

public class ReaderRoleMatcher extends RoleMatcher {
  @Override
  String baseDN() {
    throw new RuntimeException();
  }

  @Override
  String filter() {
    throw new RuntimeException();
  }

  @Override
  String pattern() {
    throw new RuntimeException();
  }

  @Override
  public boolean matches(String id) throws IOException, LDAPException {
    return true; // everyone is in readers role
  }
}
