package com.qz;

import com.qz.actions.CreateUserAction;
import com.qz.global.GlobalData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Scanner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Main.class})
public class MainTest {
    @Mock
    private Scanner mockScanner;

    @Before
    public void init() {
        GlobalData.clearAll();
    }

    @Test
    public void testMain_normalAddUserAndUserAlreadyExits() throws Exception {
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("create user qz 123456").thenReturn("create user qz 1234567");
        PowerMockito.spy(CreateUserAction.class);
        PowerMockito.verifyStatic(CreateUserAction.class, times(2));
        Assert.assertEquals(1, GlobalData.users.size());
    }

    @Test
    public void testMain_normalAddRoleAndRoleAlreadyExits() {

    }
}
