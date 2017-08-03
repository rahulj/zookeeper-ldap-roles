Name: zookeeper-ldap-plugin
Version: 0.1
Release: 0
Summary: zookeeper plugin for ldap
Source0: zookeeper-ldap-plugin-0.1.0.tar.gz
License: Apache License Version 2.0
BuildArch: x86_64
BuildRoot: %{_tmppath}/%{name}-buildroot
%description
zookeeper plugin for ldap
%prep
%setup -q
%build
%install
install -m 0755 -d $RPM_BUILD_ROOT/etc/zookeeper/conf
install -m 0755 -d $RPM_BUILD_ROOT/usr/lib/zookeeper
install -m 0755 treadmill.conf $RPM_BUILD_ROOT/etc/zookeeper/conf/treadmill.conf
install -m 0755 jaas.conf $RPM_BUILD_ROOT/etc/zookeeper/conf/jaas.conf
install -m 0755 java.env $RPM_BUILD_ROOT/etc/zookeeper/conf/java.env
install -m 0755 zookeeper-ldap-roles-all.jar $RPM_BUILD_ROOT/usr/lib/zookeeper/zookeeper-ldap-roles-all.jar
%post
rm -f /usr/lib/zookeeper/zookeeper.jar
ln -s /usr/lib/zookeeper/zookeeper-ldap-roles-all.jar /usr/lib/zookeeper/zookeeper.jar
(
cat <<EOF
authProvider.1=org.treadmill.zk.plugin.KerberosAuthProvider
jaasLoginRenew=3600000
standaloneEnabled=true
EOF
) >> /etc/zookeeper/conf/zoo.cfg
%files
/etc/zookeeper/conf/treadmill.conf
/etc/zookeeper/conf/jaas.conf
/etc/zookeeper/conf/java.env
/usr/lib/zookeeper/zookeeper-ldap-roles-all.jar
