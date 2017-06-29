package org.ms.zk.plugin;

public enum AclRole {

  admin,
  servers,
  readers;

  static String PREFIX = "file:///treadmill/roles/";

  public static AclRole get(String acl) {
    String[] splits = acl.split(PREFIX, 2);
    return (splits.length < 2) ? null : valueOf(splits[1]);
  }
}
