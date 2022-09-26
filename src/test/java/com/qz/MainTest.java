package com.qz;

import com.qz.actions.*;
import com.qz.entities.Role;
import com.qz.entities.User;
import com.qz.global.GlobalData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Main.class, CreateUserAction.class, CreateRoleAction.class, DeleteUserAction.class,
        DeleteRoleAction.class, AddRoleToUserAction.class, AuthenticateAction.class, InvalidateAction.class,
        CheckRoleAction.class, AllRolesAction.class})
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
        Main.main(new String[0]);
        PowerMockito.verifyStatic(CreateUserAction.class, times(2));
        CreateUserAction.doCreateUser(anyString(), anyString());
        Assert.assertEquals(1, GlobalData.users.size());
    }

    @Test
    public void testMain_addUserWithIncorrectParameters() throws Exception {
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("create user qz");
        PowerMockito.spy(CreateUserAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(CreateUserAction.class, times(0));
        CreateUserAction.doCreateUser(anyString(), anyString());
        Assert.assertEquals(0, GlobalData.users.size());
    }

    @Test
    public void testMain_normalAddRoleAndRoleAlreadyExits() throws Exception {
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("create role worker").thenReturn("create role worker");
        PowerMockito.spy(CreateRoleAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(CreateRoleAction.class, times(2));
        CreateRoleAction.doCreateRole(anyString());
        Assert.assertEquals(1, GlobalData.roles.size());
    }

    @Test
    public void testMain_addRoleWithIncorrectParameters() throws Exception {
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("create role worker leader");
        PowerMockito.spy(CreateRoleAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(CreateRoleAction.class, times(0));
        CreateRoleAction.doCreateRole(anyString());
        Assert.assertEquals(0, GlobalData.roles.size());
    }

    @Test
    public void testMain_normalDeleteUserAndUserNotExists() throws Exception {
        GlobalData.users.put("qz", new User("qz", "123456"));
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("delete user qz 123456").thenReturn("delete user qz 123456");
        PowerMockito.spy(DeleteUserAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(DeleteUserAction.class, times(2));
        DeleteUserAction.doDeleteUser(any(User.class));
        Assert.assertEquals(0, GlobalData.users.size());
    }

    @Test
    public void testMain_deleteUserWithIncorrectPassword() throws Exception {
        GlobalData.users.put("qz", new User("qz", "123456"));
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("delete user qz 1234567");
        PowerMockito.spy(DeleteUserAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(DeleteUserAction.class, times(1));
        DeleteUserAction.doDeleteUser(any(User.class));
        Assert.assertEquals(1, GlobalData.users.size());
    }

    @Test
    public void testMain_deleteUserWithIncorrectParameters() throws Exception {
        GlobalData.users.put("qz", new User("qz", "123456"));
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("delete user qz");
        PowerMockito.spy(DeleteUserAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(DeleteUserAction.class, times(0));
        DeleteUserAction.doDeleteUser(any(User.class));
        Assert.assertEquals(1, GlobalData.users.size());
    }

    @Test
    public void testMain_normalDeleteRoleAndRoleNotExists() throws Exception {
        Role workerRole = new Role("worker");
        Role leaderRole = new Role("leader");
        Set<Role> roles = new HashSet<>(Arrays.asList(workerRole, leaderRole));
        User user = new User("qz", "123456", roles);
        GlobalData.users.put("qz", user);
        Set<User> users = new HashSet<>(Collections.singletonList(user));
        GlobalData.roles.put(workerRole, users);
        GlobalData.roles.put(leaderRole, users);
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("delete role leader").thenReturn("delete role ceo");
        PowerMockito.spy(DeleteRoleAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(DeleteRoleAction.class, times(2));
        DeleteRoleAction.doDeleteRole(any(Role.class));
        Assert.assertEquals(1, GlobalData.roles.size());
        Assert.assertEquals(1, GlobalData.users.get("qz").getRoles().size());
    }

    @Test
    public void testMain_deleteRoleWithIncorrectParameters() throws Exception {
        Role workerRole = new Role("worker");
        Role leaderRole = new Role("leader");
        Set<Role> roles = new HashSet<>(Arrays.asList(workerRole, leaderRole));
        User user = new User("qz", "123456", roles);
        GlobalData.users.put("qz", user);
        Set<User> users = new HashSet<>(Collections.singletonList(user));
        GlobalData.roles.put(workerRole, users);
        GlobalData.roles.put(leaderRole, users);
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("delete role worker leader");
        PowerMockito.spy(DeleteRoleAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(DeleteRoleAction.class, times(0));
        DeleteRoleAction.doDeleteRole(any(Role.class));
        Assert.assertEquals(2, GlobalData.roles.size());
        Assert.assertEquals(2, GlobalData.users.get("qz").getRoles().size());
    }

    @Test
    public void testMain_normalAddRoleToUserAndUserNotExistsAndIncorrectPasswordAndRoleNotExists() throws Exception {
        GlobalData.users.put("qz", new User("qz", "123456"));
        GlobalData.roles.put(new Role("worker"), new HashSet<>());
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("add role to user qz 123456 worker").thenReturn("add role to user qz1 123456 worker").thenReturn("add role to user qz 123456 worker1").thenReturn("add role to user qz 1234567 worker");
        PowerMockito.spy(AddRoleToUserAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(AddRoleToUserAction.class, times(4));
        AddRoleToUserAction.doAddRoleToUser(any(User.class), any(Role.class));
        Assert.assertEquals(1, GlobalData.users.get("qz").getRoles().size());
    }

    @Test
    public void testMain_addRoleToUserWithIncorrectParameters() throws Exception {
        GlobalData.users.put("qz", new User("qz", "123456"));
        GlobalData.roles.put(new Role("worker"), new HashSet<>());
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("add role to user qz 123456 worker leader");
        PowerMockito.spy(AddRoleToUserAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(AddRoleToUserAction.class, times(0));
        AddRoleToUserAction.doAddRoleToUser(any(User.class), any(Role.class));
        Assert.assertEquals(0, GlobalData.users.get("qz").getRoles().size());
    }

    @Test
    public void testMain_normalAuthenticateAndUserNotExistsAndIncorrectPassword() throws Exception {
        GlobalData.users.put("qz", new User("qz", "123456"));
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("authenticate qz 123456").thenReturn("authenticate qz1 123456").thenReturn("authenticate qz 1234567");
        PowerMockito.spy(AuthenticateAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(AuthenticateAction.class, times(3));
        AuthenticateAction.doAuthenticate(anyString(), anyString());
        Assert.assertEquals(1, GlobalData.tokens.size());
        Assert.assertEquals(1, GlobalData.tokens2User.size());
    }

    @Test
    public void testMain_authenticateWithIncorrectParameters() throws Exception {
        GlobalData.users.put("qz", new User("qz", "123456"));
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("authenticate qz 123456 worker");
        PowerMockito.spy(AuthenticateAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(AuthenticateAction.class, times(0));
        AuthenticateAction.doAuthenticate(anyString(), anyString());
        Assert.assertEquals(0, GlobalData.tokens.size());
        Assert.assertEquals(0, GlobalData.tokens2User.size());
    }

    @Test
    public void testMain_normalInvalidateAndTokenNotExist() throws Exception {
        User user = new User("qz", "123456");
        GlobalData.users.put("qz", user);
        GlobalData.tokens.put("aaa", 0L);
        GlobalData.tokens2User.put("aaa", user);
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("invalidate aaa").thenReturn("invalidate aaaa");
        PowerMockito.spy(InvalidateAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(InvalidateAction.class, times(2));
        InvalidateAction.doInvalidate(anyString());
        Assert.assertEquals(0, GlobalData.tokens.size());
        Assert.assertEquals(0, GlobalData.tokens2User.size());
    }

    @Test
    public void testMain_invalidateWithIncorrectParameters() throws Exception {
        User user = new User("qz", "123456");
        GlobalData.users.put("qz", user);
        GlobalData.tokens.put("aaa", 0L);
        GlobalData.tokens2User.put("aaa", user);
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("invalidate aaa aaa");
        PowerMockito.spy(InvalidateAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(InvalidateAction.class, times(0));
        InvalidateAction.doInvalidate(anyString());
        Assert.assertEquals(1, GlobalData.tokens.size());
        Assert.assertEquals(1, GlobalData.tokens2User.size());
    }

    @Test
    public void testMain_checkRoleWithCorrectParametersAndIncorrectParameters() throws Exception {
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("check role aaa worker").thenReturn("check role aaa qz worker");
        PowerMockito.spy(CheckRoleAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(CheckRoleAction.class, times(1));
        CheckRoleAction.doCheckRole(anyString(), any(Role.class));
    }

    @Test
    public void testMain_allRoleWithCorrectParametersAndIncorrectParameters() throws Exception {
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("all roles aaa").thenReturn("all roles aaa qz");
        GlobalData.tokens.put("aaa", System.currentTimeMillis());
        GlobalData.tokens2User.put("aaa", new User("qz", "123456"));
        PowerMockito.spy(AllRolesAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(AllRolesAction.class, times(1));
        AllRolesAction.doAllRoles(anyString());
    }

    @Test
    public void testMain_invalidCommand() throws Exception {
        PowerMockito.whenNew(Scanner.class).withAnyArguments().thenReturn(mockScanner);
        when(mockScanner.hasNextLine()).thenReturn(true).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("aaa");
        PowerMockito.spy(CreateUserAction.class);
        PowerMockito.spy(CreateRoleAction.class);
        PowerMockito.spy(DeleteUserAction.class);
        PowerMockito.spy(DeleteRoleAction.class);
        PowerMockito.spy(AddRoleToUserAction.class);
        PowerMockito.spy(AuthenticateAction.class);
        PowerMockito.spy(InvalidateAction.class);
        PowerMockito.spy(CheckRoleAction.class);
        PowerMockito.spy(AllRolesAction.class);
        Main.main(new String[0]);
        PowerMockito.verifyStatic(CreateUserAction.class, times(0));
        CreateUserAction.doCreateUser(anyString(), anyString());
        PowerMockito.verifyStatic(CreateRoleAction.class, times(0));
        CreateRoleAction.doCreateRole(anyString());
        Assert.assertEquals(0, GlobalData.roles.size());
        PowerMockito.verifyStatic(DeleteUserAction.class, times(0));
        DeleteUserAction.doDeleteUser(any(User.class));
        PowerMockito.verifyStatic(DeleteRoleAction.class, times(0));
        DeleteRoleAction.doDeleteRole(any(Role.class));
        PowerMockito.verifyStatic(AddRoleToUserAction.class, times(0));
        AddRoleToUserAction.doAddRoleToUser(any(User.class), any(Role.class));
        PowerMockito.verifyStatic(AuthenticateAction.class, times(0));
        AuthenticateAction.doAuthenticate(anyString(), anyString());
        PowerMockito.verifyStatic(InvalidateAction.class, times(0));
        InvalidateAction.doInvalidate(anyString());
        PowerMockito.verifyStatic(CheckRoleAction.class, times(0));
        CheckRoleAction.doCheckRole(anyString(), any(Role.class));
        PowerMockito.verifyStatic(AllRolesAction.class, times(0));
        AllRolesAction.doAllRoles(anyString());
    }
}
