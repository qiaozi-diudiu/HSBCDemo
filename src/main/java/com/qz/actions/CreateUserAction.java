package com.qz.actions;

import com.qz.entities.User;
import com.qz.global.GlobalData;

public class CreateUserAction {
    public static void doCreateUser(String username, String password) throws IllegalArgumentException {
        if (GlobalData.users.containsKey(username)) {
            throw new IllegalArgumentException(String.format("User: %s already exist!", username));
        }
        GlobalData.users.put(username, new User(username, password));
        System.out.printf("User: %s created successfully%n", username);
    }
}
