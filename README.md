# HSBCDemo

##How to use

1. Set TOKEN_INVALID_TIME in setting.properties in resource folder.
2. Run main() function in Main.java.
3. Enter command based on following rules:
   1. `create user $username $password` Create a new user into system. Should fail if the user already exists.
   2. `delete user $username $password` Delete a user from system. Should fail if the user doesn't exist or password not correct.
   3. `create role $roleName` Create a new role into system. Should fail if the role already exists.
   4. `delete role $roleName` Delete a role from system. Should fail if the role doesn't exist.
   5. `add role to user $username $password $roleName` Associate a role to a user. If the role is already associated with the user, nothing should happen. Should fail if the role or the user not exist, or password not correct.
   6. `authenticate $username $password` Return a special "secret" auth token or error, if not found. The token is only valid for pre-configured time (2h by default).
   7. `invalidate $token` The token is no longer valid after the call.
   8. `check role $token $roleName` Return true if the user, identified by the token, belongs to the role, false otherwise, error if token is invalid, expired, etc.
   9. `all roles $token` Return all roles for the user, error if token is invalid.

## External Libraries
1. mockito, for unit test purpose
2. powermock, for unit test purpose