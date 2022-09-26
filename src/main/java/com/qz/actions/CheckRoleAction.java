package com.qz.actions;

import com.qz.entities.Role;
import com.qz.global.GlobalData;

public class CheckRoleAction {
    public static boolean doCheckRole(String token, Role role) {
        if (!GlobalData.roles.containsKey(role)) {
            System.out.println("Check role result is false");
            return false;
        }
        long currTime = System.currentTimeMillis();
        boolean result = true;
        if (!GlobalData.tokens.containsKey(token) || currTime - GlobalData.tokens.get(token) > GlobalData.TOKEN_INVALID_TIME) {
            GlobalData.tokens.remove(token);
            GlobalData.tokens2User.remove(token);
            result = false;
        }
        if (!GlobalData.tokens2User.get(token).getRoles().contains(role)) {
            result = false;
        }
        System.out.printf("Check role result is %s%n", result);
        return result;
    }
}
