package com.qz.actions;

import com.qz.entities.Role;
import com.qz.entities.User;
import com.qz.global.GlobalData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;

public class CheckRoleActionTest {
    @Before
    public void init() {
        GlobalData.clearAll();
    }

    @Test
    public void testDoCheckRole_roleNotExist() {
        boolean result = CheckRoleAction.doCheckRole("aaa", new Role("worker"));
        Assert.assertFalse(result);
    }

    @Test
    public void testDoCheckRole_tokenNotExist() {
        GlobalData.roles.put(new Role("worker"), new HashSet<>());
        boolean result = CheckRoleAction.doCheckRole("aaa", new Role("worker"));
        Assert.assertFalse(result);
    }

    @Test
    public void testDoCheckRole_tokenNotExpired() {
        GlobalData.roles.put(new Role("worker"), new HashSet<>());
        GlobalData.tokens.put("aaa", 1L);
        boolean result = CheckRoleAction.doCheckRole("aaa", new Role("worker"));
        Assert.assertFalse(result);
    }

    @Test
    public void testDoCheckRole_roleNotMatch() {
        GlobalData.roles.put(new Role("worker"), new HashSet<>());
        GlobalData.tokens.put("aaa", System.currentTimeMillis());
        User user = new User("qz", "123456");
        Role role = new Role("leader");
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        GlobalData.tokens2User.put("aaa", user);
        GlobalData.users.put("qz", user);
        GlobalData.roles.put(role, new HashSet<>(Collections.singletonList(user)));
        boolean result = CheckRoleAction.doCheckRole("aaa", new Role("worker"));
        Assert.assertFalse(result);
    }

    @Test
    public void testDoCheckRole_roleMatch() {
        GlobalData.roles.put(new Role("worker"), new HashSet<>());
        GlobalData.tokens.put("aaa", System.currentTimeMillis());
        User user = new User("qz", "123456");
        Role role = new Role("leader");
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        GlobalData.tokens2User.put("aaa", user);
        GlobalData.users.put("qz", user);
        GlobalData.roles.put(role, new HashSet<>(Collections.singletonList(user)));
        boolean result = CheckRoleAction.doCheckRole("aaa", new Role("leader"));
        Assert.assertTrue(result);
    }
}
