package com.swifta.onerecharge.util;

/**
 * Created by moyinoluwa on 8/23/16.
 */
public class Url {

    public static final String BASE_URL = "http://40.117.36.121:3001/";

    // Login
    public static final String AGENT_LOGIN_URL = "api/auth/{email}/{password}";
    public static final String EMAIL_PATH = "email";
    public static final String PASSWORD_PATH = "password";

    // Change password
    public static final String AGENT_CHANGE_PASSWORD_URL =
            "api/agents/password/reset";
}
