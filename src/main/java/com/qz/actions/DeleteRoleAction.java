package com.qz.actions;

import com.qz.entities.Role;
import com.qz.global.GlobalData;

public class DeleteRoleAction {
    public static void doDeleteRole(Role role) throws IllegalArgumentException {
        if (!GlobalData.roles.containsKey(role)) {
            throw new IllegalArgumentException(String.format("Role: %s not exist!", role.getRoleName()));
        }
        GlobalData.roles.remove(role);
        System.out.printf("Role: %s deleted successfully%n", role.getRoleName());
    }
}
