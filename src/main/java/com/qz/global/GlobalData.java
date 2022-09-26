package com.qz.global;

import com.qz.entities.Role;
import com.qz.entities.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GlobalData {
    private static Properties properties;

    static {
        InputStream inputStream = null;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            inputStream = loader.getResourceAsStream("settings.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Map<String, Long> tokens = new HashMap<>();

    public static Map<String, User> tokens2User = new HashMap<>();

    public static Map<String, User> users = new HashMap<>();

    public static Map<Role, Set<User>> roles = new HashMap<>();

    public static final long TOKEN_INVALID_TIME = TimeUnit.HOURS.toMillis(Long.parseLong(properties.getProperty("TOKEN_INVALID_TIME") == null ? "2" : properties.getProperty("TOKEN_INVALID_TIME")));

    public static void clearAll() {
        tokens.clear();
        tokens2User.clear();
        users.clear();
        roles.clear();
    }
}
