package com.qz.actions;

import com.qz.entities.Role;
import com.qz.entities.User;
import com.qz.global.GlobalData;

import java.util.Set;

public class CheckRoleAction {
    public static Set<User> doCheckRole(String token, Role role) throws IllegalArgumentException {
        long currTime = System.currentTimeMillis();
        if (!GlobalData.tokens.containsKey(token) || currTime - GlobalData.tokens.get(token) > GlobalData.TOKEN_INVALID_TIME) {
            GlobalData.tokens.remove(token);
            throw new IllegalArgumentException(String.format("Token: %s is invalid", token));
        }
        System.out.println("Check role successfully");
        return GlobalData.roles.get(role);
    }
}
