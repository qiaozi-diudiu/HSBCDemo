package com.qz.actions;

import com.qz.entities.Role;
import com.qz.global.GlobalData;

import java.util.Set;

public class AllRolesAction {
    public static Set<Role> doAllRoles(String token) throws IllegalArgumentException {
        long currTime = System.currentTimeMillis();
        if (!GlobalData.tokens.containsKey(token) || currTime - GlobalData.tokens.get(token) > GlobalData.TOKEN_INVALID_TIME) {
            GlobalData.tokens.remove(token);
            throw new IllegalArgumentException(String.format("Token: %s is invalid", token));
        }
        System.out.println("Get all roles successfully");
        return GlobalData.roles.keySet();
    }
}
