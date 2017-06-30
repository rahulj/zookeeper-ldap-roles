package org.ms.zk.plugin.matcher;

import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.LDAPException;
import org.ms.zk.plugin.LdapQuery;

import java.io.IOException;
import java.util.Collection;

import static java.lang.System.getenv;
import static org.ms.zk.plugin.Configuration.get;

public class AdminRoleMatcher implements RoleMatcher {

  @Override
  public boolean matches(String id) throws IOException, LDAPException {
    String principal = id.split("@")[0];
    String baseDN = get("base_dn_for_admin_role", getenv("TREADMILL_LDAP_SUFFIX"));
    String filter = get("filter_for_admin_role", getenv("TREADMILL_CELL"));
    Collection<Attribute> attributes = LdapQuery.getAttributes(baseDN, filter);

    return attributes.stream().anyMatch(a -> a.getName().startsWith("master-hostname;") &&
      a.getValue().equals(principal));
  }
}
