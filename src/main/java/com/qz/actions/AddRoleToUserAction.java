package com.qz.actions;

import com.qz.entities.Role;
import com.qz.entities.User;
import com.qz.global.GlobalData;

public class AddRoleToUserAction {
    public static void doAddRoleToUser(User user, Role role) throws IllegalArgumentException {
        if (!GlobalData.users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException(String.format("User: %s not exist!", user.getUsername()));
        }
        if (!GlobalData.roles.containsKey(role)) {
            throw new IllegalArgumentException(String.format("Role: %s not exist!", role.getRoleName()));
        }
        Role oldRole = GlobalData.users.get(user.getUsername()).getRole();
        GlobalData.users.get(user.getUsername()).setRole(role);
        if (GlobalData.roles.containsKey(oldRole)) {
            GlobalData.roles.get(oldRole).remove(user);
        }
        GlobalData.roles.get(role).add(user);
        System.out.printf("Add role: %s to user: %s successfully", role.getRoleName(), user.getUsername());
    }
}
