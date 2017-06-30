package org.ms.zk.plugin.matcher;

public class AdminRoleMatcher extends RoleMatcher {

  @Override
  String baseDN() {
    return "base_dn_for_admin_role";
  }

  @Override
  String filter() {
    return "filter_for_admin_role";
  }

  @Override
  String pattern() {
    return "master-hostname;";
  }
}