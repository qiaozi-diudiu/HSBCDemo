package com.qz.actions;

import com.qz.EncryptUtil;
import com.qz.global.GlobalData;

import java.util.Objects;
import java.util.UUID;

public class AuthenticateAction {
    public static String doAuthenticate(String username, String password) throws IllegalArgumentException {
        long currTime = System.currentTimeMillis();
        String encrypted = EncryptUtil.encrypt(password);
        if (!GlobalData.users.containsKey(username) || !Objects.equals(encrypted, GlobalData.users.get(username).getPassword())) {
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
