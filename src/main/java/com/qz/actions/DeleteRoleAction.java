package com.qz.actions;

import com.qz.entities.Role;
import com.qz.entities.User;
import com.qz.global.GlobalData;

import java.util.Set;

public class DeleteRoleAction {
    public static void doDeleteRole(Role role) throws IllegalArgumentException {
        if (!GlobalData.roles.containsKey(role)) {
            throw new IllegalArgumentException(String.format("Role: %s not exist!", role.getRoleName()));
        }
        Set<User> users = GlobalData.roles.remove(role);
        for (User user : users) {
            GlobalData.users.get(user.getUsername()).getRoles().remove(role);
        }
        System.out.printf("Role: %s deleted successfully%n", role.getRoleName());
    }
}
