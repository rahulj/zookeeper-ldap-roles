package org.ms.zk.plugin.matcher;

public class ServerRoleMatcher extends RoleMatcher {
  @Override
  String baseDN() {
    return "base_dn_for_server_role";
  }

  @Override
  String filter() {
    return "filter_for_server_role";
  }

  @Override
  String pattern() {
    return "server";
  }
}
