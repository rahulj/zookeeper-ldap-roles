package org.treadmill.zk.plugin.utils;

public class CacheKey {

  public String baseDN;
  public String filter;

  public CacheKey(String baseDN, String filter) {
    this.baseDN = baseDN;
    this.filter = filter;
  }
}
