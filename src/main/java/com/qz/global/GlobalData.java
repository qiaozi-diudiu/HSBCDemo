package com.qz.global;

import com.qz.entities.Role;
import com.qz.entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GlobalData {
    public static Map<String, Long> tokens = new HashMap<>();

    public static Map<String, User> tokens2User = new HashMap<>();

    public static Map<String, User> users = new HashMap<>();

    public static Map<Role, Set<User>> roles = new HashMap<>();

    public static final long TOKEN_INVALID_TIME = TimeUnit.HOURS.toMillis(2);
}
