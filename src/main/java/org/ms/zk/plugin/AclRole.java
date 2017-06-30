package org.ms.zk.plugin;

import com.unboundid.ldap.sdk.LDAPException;
import org.ms.zk.plugin.matcher.AdminRoleMatcher;
import org.ms.zk.plugin.matcher.RoleMatcher;

import java.io.IOException;

public enum AclRole {

  admin(new AdminRoleMatcher()),
  servers(new AdminRoleMatcher()),
  readers(new AdminRoleMatcher());

  static String PREFIX = "file:///treadmill/roles/";
  private RoleMatcher roleMatcher;

  AclRole(RoleMatcher roleMatcher) {
    this.roleMatcher = roleMatcher;
  }

  public static AclRole get(String acl) {
    String[] splits = acl.split(PREFIX, 2);
    return (splits.length < 2) ? null : valueOf(splits[1]);
  }

  public boolean match(String id) throws IOException, LDAPException {
    return roleMatcher.matches(id);
  }
}
