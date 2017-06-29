package org.ms.zk.plugin;

import com.unboundid.ldap.sdk.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static java.lang.Integer.parseInt;
import static org.ms.zk.plugin.Configuration.get;

public class LdapQuery {

  public Collection<Attribute> getAttributes(String baseDN, String filter) throws LDAPException, IOException {
    LDAPConnection connection = getConnection();
    List<SearchResultEntry> searchEntries = connection.search(baseDN, SearchScope.ONE, filter).getSearchEntries();

    return searchEntries.get(0).getAttributes();
  }

  public LDAPConnection getConnection() throws LDAPException, IOException {
    String host = get("ldap_host");
    int port = parseInt(get("ldap_port"));
    String userDN = get("user_dn");
    String password = get("password");

    return new LDAPConnection(host, port, userDN, password);
  }


}
