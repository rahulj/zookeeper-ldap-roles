package org.ms.zk.plugin.matcher;

import com.unboundid.ldap.sdk.LDAPException;

import java.io.IOException;

public interface RoleMatcher {
  boolean matches(String id) throws IOException, LDAPException;
}
