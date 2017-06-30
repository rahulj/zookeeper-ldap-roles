package org.ms.zk.plugin;

import com.unboundid.ldap.sdk.LDAPException;
import org.ms.zk.plugin.matcher.AdminRoleMatcher;
import org.ms.zk.plugin.matcher.ReaderRoleMatcher;
import org.ms.zk.plugin.matcher.RoleMatcher;
import org.ms.zk.plugin.matcher.ServerRoleMatcher;

import java.io.IOException;

public enum AclRole {

  admin(new AdminRoleMatcher()),
  servers(new ServerRoleMatcher()),
  readers(new ReaderRoleMatcher());

  private RoleMatcher roleMatcher;

  AclRole(RoleMatcher roleMatcher) {
    this.roleMatcher = roleMatcher;
  }

  public static AclRole get(String acl) throws IOException {
    String prefix = Configuration.get("zookeeper_roles_acl_prefix");
    return acl.startsWith(prefix) ? valueOf(acl.replace(prefix, "")) : null;
  }

  public boolean match(String id) throws IOException, LDAPException {
    return roleMatcher.matches(id);
  }
}
