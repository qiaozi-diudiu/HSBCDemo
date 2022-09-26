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
        GlobalData.users.get(user.getUsername()).getRoles().add(role);
        GlobalData.roles.get(role).add(user);
        System.out.printf("Add role: %s to user: %s successfully%n", role.getRoleName(), user.getUsername());
    }
}
