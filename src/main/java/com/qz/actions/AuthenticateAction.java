package com.qz.actions;

import com.qz.global.GlobalData;

import java.util.UUID;

public class AuthenticateAction {
    public static String doAuthenticate(String username, String password) throws IllegalArgumentException {
        long currTime = System.currentTimeMillis();
        if (!GlobalData.users.containsKey(username) || !password.equals(GlobalData.users.get(username).getPassword())) {
            throw new IllegalArgumentException(String.format("Invalid %s or %s", username, password));
        }
        String token = UUID.randomUUID().toString();
        while (GlobalData.tokens.containsKey(token)) {
            token = UUID.randomUUID().toString();
        }
        GlobalData.tokens.put(token, currTime);
        GlobalData.tokens2User.put(token, GlobalData.users.get(username));
        System.out.println("authenticate successfully");
        return token;
    }
}
