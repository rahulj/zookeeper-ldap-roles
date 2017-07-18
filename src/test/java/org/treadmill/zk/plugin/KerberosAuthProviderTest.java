package org.treadmill.zk.plugin;

import com.google.inject.Injector;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.treadmill.zk.plugin.matcher.RoleMatcher;
import org.treadmill.zk.plugin.matcher.UserMatcher;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@PrepareForTest({KerberosAuthProvider.class, KrbGuiceModule.class})
public class KerberosAuthProviderTest extends TestBase {

  @Test
  public void testIfValid() {
    KerberosAuthProvider provider = new KerberosAuthProvider();
    assertTrue(provider.isValid("host/hostname@domain"));

  }

  @Test
  public void shouldMatchUsingUserMatcher() throws IOException, ExecutionException {
    mockStatic(KrbGuiceModule.class);
    Injector mockInjector = mock(Injector.class);
    when(KrbGuiceModule.injector()).thenReturn(mockInjector);
    RoleMatcher mockedRoleMatcher = mock(RoleMatcher.class);
    UserMatcher mockedUserMatcher = mock(UserMatcher.class);

    when(mockInjector.getInstance(RoleMatcher.class)).thenReturn(mockedRoleMatcher);
    when(mockInjector.getInstance(UserMatcher.class)).thenReturn(mockedUserMatcher);
    when(mockedUserMatcher.matches("someUser@SOME_DOMAIN", "someUser")).thenReturn(true);

    KerberosAuthProvider provider = new KerberosAuthProvider();
    assertTrue(provider.matches("someUser@SOME_DOMAIN", "someUser"));
    verify(mockedUserMatcher).matches("someUser@SOME_DOMAIN", "someUser");
    verify(mockedRoleMatcher, never()).matchAcl(anyString(), anyString());
  }

  @Test
  public void shouldMatchUsingRoleMatcher() throws IOException, ExecutionException {
    mockStatic(KrbGuiceModule.class);
    Injector mockInjector = mock(Injector.class);
    when(KrbGuiceModule.injector()).thenReturn(mockInjector);
    RoleMatcher mockedRoleMatcher = mock(RoleMatcher.class);
    UserMatcher mockedUserMatcher = mock(UserMatcher.class);

    when(mockInjector.getInstance(RoleMatcher.class)).thenReturn(mockedRoleMatcher);
    when(mockInjector.getInstance(UserMatcher.class)).thenReturn(mockedUserMatcher);
    when(mockedRoleMatcher.matches("host/someUser@SOME_DOMAIN", "someRole")).thenReturn(true);

    assertTrue(new KerberosAuthProvider().matches("host/someUser@SOME_DOMAIN", "role/someRole"));
    verify(mockedRoleMatcher).matches("host/someUser@SOME_DOMAIN", "someRole");
    verify(mockedUserMatcher, never()).matchAcl(anyString(), anyString());
  }

  @Test
  public void matchShouldFailUsingUserMatcher() {
    KerberosAuthProvider provider = new KerberosAuthProvider();
    assertFalse(provider.matches("someUser@SOME_DOMAIN", "someOtherUser"));

  }
}