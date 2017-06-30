package org.ms.zk.plugin.matcher;

import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.LDAPException;
import org.ms.zk.plugin.LdapQuery;

import java.io.IOException;
import java.util.Collection;

import static java.lang.System.getenv;
import static org.ms.zk.plugin.Configuration.get;

public abstract class RoleMatcher {
  public boolean matches(String id) throws IOException, LDAPException {
    String baseDN = get(baseDN(), getenv("TREADMILL_LDAP_SUFFIX"));
    String filter = get(filter(), getenv("TREADMILL_CELL"));

    Collection<Attribute> attributes = LdapQuery.getAttributes(baseDN, filter);

    return attributes.stream().anyMatch(
      a ->
        a.getName().startsWith(pattern()) &&
          a.getValue().equals(id.split("@")[0]));
  }

  abstract String baseDN();

  abstract String filter();

  abstract String pattern();
}
