package com.qz.actions;

import com.qz.entities.User;
import com.qz.global.GlobalData;

public class DeleteUserAction {
    public static void doDeleteUser(User user) throws IllegalArgumentException {
        if (!GlobalData.users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException(String.format("User: %s not exist!", user.getUsername()));
        }
        if (!user.getPassword().equals(GlobalData.users.get(user.getUsername()).getPassword())) {
            throw new IllegalArgumentException(String.format("User: %s not exist!", user.getUsername()));
        }
        GlobalData.users.remove(user.getUsername());
        System.out.printf("User: %s deleted successfully%n", user.getUsername());
    }
}
