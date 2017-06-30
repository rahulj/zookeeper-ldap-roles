package org.ms.zk.plugin.matcher;

import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.LDAPException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ms.zk.plugin.LdapQuery;
import org.ms.zk.plugin.TestBase;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static org.ms.zk.plugin.LdapQuery.getAttributes;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LdapQuery.class, AdminRoleMatcher.class})
public class AdminRoleMatcherTest extends TestBase {

  @Test
  public void shouldMatchAdminRole() throws IOException, LDAPException {
    AdminRoleMatcher adminRoleMatcher = new AdminRoleMatcher();

    mockStatic(LdapQuery.class);
    String baseDN = "ou=cells,ou=treadmill,dc=suffix";
    String filter = "(&(objectClass=tmCell)(cell=cellABC))";

    List<Attribute> attributes = asList(
      new Attribute("master-hostname;tm-master-xyz", "master1.treadmill"),
      new Attribute("master-hostname;tm-master-c4ca42", "master2.treadmill"));
    when(getAttributes(baseDN, filter)).thenReturn(attributes);

    assertTrue(adminRoleMatcher.matches("master2.treadmill@DOMAIN"));
  }
}