package com.fu.fuatsbe.constant.role;

public final class RolePreAuthorize {
    public static final String ROLE_ADMIN = "hasRole('ADMIN')";
    public static final String ROLE_CANDIDATE = "hasRole('CANDIDATE')";
    public static final String ROLE_EMPLOYEE = "hasRole('EMPLOYEE')";
    public static final String IS_AUTHENTICATED = "isAuthenticated()";
    public static final String ROLE_ADMIN_EMPLOYEE = "hasRole('ADMIN') or hasRole('EMPLOYEE')";
    public static final String ROLE_ADMIN_CANDIDATE = "hasRole('ADMIN') or hasRole('CANDIDATE')";
    public static final String ROLE_EMPLOYEE_CANDIDATE = "hasRole('EMPLOYEE') or hasRole('CANDIDATE')";
}
