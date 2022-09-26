package com.qz.actions;

import com.qz.entities.Role;
import com.qz.entities.User;
import com.qz.global.GlobalData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AllRolesActionTest {
    @Before
    public void init() {
        GlobalData.clearAll();
    }

    @Test
    public void testDoAllRoles_tokenNotExist() {
        Set<Role> roles;
        try {
            roles = AllRolesAction.doAllRoles("aaa");
        } catch (IllegalArgumentException e) {
            roles = new HashSet<>();
        }
        Assert.assertEquals(0, roles.size());
    }

    @Test
    public void testDoAllRoles_tokenExpired() {
        GlobalData.tokens.put("aaa", 1L);
        Set<Role> roles;
        try {
            roles = AllRolesAction.doAllRoles("aaa");
        } catch (IllegalArgumentException e) {
            roles = new HashSet<>();
        }
        Assert.assertEquals(0, roles.size());
    }

    @Test
    public void testDoAllRoles_tokenValid() {
        GlobalData.roles.put(new Role("worker"), new HashSet<>());
        GlobalData.tokens.put("aaa", System.currentTimeMillis());
        User user = new User("qz", "123456");
        Role role = new Role("leader");
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        GlobalData.tokens2User.put("aaa", user);
        GlobalData.users.put("qz", user);
        GlobalData.roles.put(role, new HashSet<>(Collections.singletonList(user)));
        Set<Role> roles;
        try {
            roles = AllRolesAction.doAllRoles("aaa");
        } catch (IllegalArgumentException e) {
            roles = new HashSet<>();
        }
        Assert.assertEquals(1, roles.size());
    }
}
