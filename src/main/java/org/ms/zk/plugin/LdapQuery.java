package org.ms.zk.plugin;

import com.unboundid.ldap.sdk.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.System.getenv;
import static org.ms.zk.plugin.Configuration.get;

public class LdapQuery {

  public static Collection<Attribute> getAttributes(String baseDN, String filter) throws LDAPException, IOException {
    LDAPConnection connection = getConnection();
    List<SearchResultEntry> searchEntries = connection.search(baseDN, SearchScope.ONE, filter).getSearchEntries();

    return searchEntries.get(0).getAttributes();
  }

  private static LDAPConnection getConnection() throws LDAPException, IOException {
    String[] ldapUrl = getenv("TREADMILL_LDAP").split(":", 2);
    String userDN = getenv("TREADMILL_LDAP_USER");
    String password = get("password");

    return new LDAPConnection(ldapUrl[0], parseInt(ldapUrl[1]), userDN, password);
  }

//  public static void main(String[] args) throws LDAPException, IOException {
//    String baseDN = "ou=servers,ou=treadmill,dc=local";
//    String filter = "(&(objectClass=tmServer)(cell=local))";
//
//
//    Collection<Attribute> results = getAttributes(baseDN, filter);
//
//    for (Attribute attribute : results) {
//      System.out.println(attribute.getName() + " : " + attribute.getValue());
//    }
//  }


}
