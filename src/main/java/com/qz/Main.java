package com.qz;

import com.qz.actions.*;
import com.qz.entities.Role;
import com.qz.entities.User;
import com.qz.global.GlobalData;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to user management system! Please enter you command: ");
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            try {
                if (input.toLowerCase(Locale.ENGLISH).startsWith("create user ")) {
                    String[] parts = input.substring("create user ".length()).split(" ");
                    if (parts.length != 2) {
                        System.out.println("Invalid Command!");
                        continue;
                    }
                    CreateUserAction.doCreateUser(parts[0], parts[1]);
                } else if (input.toLowerCase(Locale.ENGLISH).startsWith("delete user ")) {
                    String[] parts = input.substring("delete user ".length()).split(" ");
                    if (parts.length != 1) {
                        System.out.println("Invalid Command!");
                        continue;
                    }
                    User user = GlobalData.users.get(parts[0]);
                    if (user == null) {
                        throw new IllegalArgumentException(String.format("User: %s not exist!", parts[0]));
                    }
                    DeleteUserAction.doDeleteUser(user);
                } else if (input.toLowerCase(Locale.ENGLISH).startsWith("create role ")) {
                    String[] parts = input.substring("create role ".length()).split(" ");
                    if (parts.length != 1) {
                        System.out.println("Invalid Command!");
                        continue;
                    }
                    CreateRoleAction.doCreateRole(parts[0]);
                } else if (input.toLowerCase(Locale.ENGLISH).startsWith("delete role ")) {
                    String[] parts = input.substring("delete role ".length()).split(" ");
                    if (parts.length != 1) {
                        System.out.println("Invalid Command!");
                        continue;
                    }
                    DeleteRoleAction.doDeleteRole(new Role(parts[0]));
                } else if (input.toLowerCase(Locale.ENGLISH).startsWith("add role to user ")) {
                    String[] parts = input.substring("add role to user ".length()).split(" ");
                    if (parts.length != 2) {
                        System.out.println("Invalid Command!");
                        continue;
                    }
                    User user = GlobalData.users.get(parts[0]);
                    if (user == null) {
                        throw new IllegalArgumentException(String.format("User: %s not exist!", parts[0]));
                    }
                    AddRoleToUserAction.doAddRoleToUser(user, new Role(parts[1]));
                } else if (input.toLowerCase(Locale.ENGLISH).startsWith("authenticate ")) {
                    String[] parts = input.substring("authenticate ".length()).split(" ");
                    if (parts.length != 2) {
                        System.out.println("Invalid Command!");
                        continue;
                    }
                    String token = AuthenticateAction.doAuthenticate(parts[0], parts[1]);
                    System.out.printf("Auth token is %s%n", token);
                } else if (input.toLowerCase(Locale.ENGLISH).startsWith("invalidate ")) {
                    String[] parts = input.substring("invalidate ".length()).split(" ");
                    if (parts.length != 1) {
                        System.out.println("Invalid Command!");
                        continue;
                    }
                    InvalidateAction.doInvalidate(parts[0]);
                } else if (input.toLowerCase(Locale.ENGLISH).startsWith("check role ")) {
                    String[] parts = input.substring("check role ".length()).split(" ");
                    if (parts.length != 2) {
                        System.out.println("Invalid Command!");
                        continue;
                    }
                    CheckRoleAction.doCheckRole(parts[0], new Role(parts[1]));
                } else if (input.toLowerCase(Locale.ENGLISH).startsWith("all roles ")) {
                    String[] parts = input.substring("all roles ".length()).split(" ");
                    if (parts.length != 1) {
                        System.out.println("Invalid Command!");
                        continue;
                    }
                    System.out.printf("doAllRoles: %s%n", AllRolesAction.doAllRoles(parts[0]).toString());
                } else if (input.toLowerCase(Locale.ENGLISH).startsWith("exit")) {
                    break;
                } else {
                    System.out.println("Invalid Command!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Please enter your command: ");
            }
        }
        System.out.println("Thanks for using! Goodbye!");
    }
}
