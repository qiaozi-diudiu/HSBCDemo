package com.qz.actions;

import com.qz.entities.Role;
import com.qz.global.GlobalData;

import java.util.HashSet;

public class CreateRoleAction {
    public static void doCreateRole(String roleName) throws IllegalArgumentException {
        Role role = new Role(roleName);
        if (GlobalData.roles.containsKey(role)) {
            throw new IllegalArgumentException(String.format("Role: %s already exist!", roleName));
        }
        GlobalData.roles.put(role, new HashSet<>());
        System.out.printf("Role: %s created successfully%n", roleName);
    }
}
