package com.qz.actions;

import com.qz.global.GlobalData;

public class InvalidateAction {
    public static void doInvalidate(String token)  throws IllegalArgumentException {
        if (!GlobalData.tokens.containsKey(token)) {
            throw new IllegalArgumentException(String.format("Token: %s not exist!", token));
        }
        GlobalData.tokens.remove(token);
        System.out.printf("Token: %s invalidated successfully%n", token);
    }
}
