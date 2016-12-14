package com.swifta.onerecharge.util;

/**
 * Created by moyinoluwa on 8/23/16.
 */
public class Url {

    public static final String NETWORK = "network";
    public static final String RECIPIENT = "recipient";
    public static final String GATEWAY = "gateway";
    public static final String STATUS = "status";
    public static final String DATE_FROM = "date_from";
    public static final String DATE_TO = "date_to";
    public static final String AGENT_EMAIL = "agent_email";
    public static final String CURRENT = "current";
    public static final String ROW_COUNT = "rowCount";
    public static final String SEARCH_PHRASE = "searchphrase";
    public static final String TOKEN_PATH = "token";
    public static final String EMAIL_PATH = "email";
    public static final String PASSWORD_PATH = "password";

    public static final String BASE_URL = "http://40.117.36.121:3001/";

    /**
     * Agents
     **/

    // Login
    public static final String AGENT_LOGIN_URL = "api/auth/{email}/{password}";

    // Agent SignUp
    public static final String AGENT_SIGNUP_URL = "api/agents/add/API";

    // Agent Change password
    public static final String AGENT_CHANGE_PASSWORD_URL =
            "api/agents/password/reset";

    // Get Agent Summary
    public static final String AGENT_SUMMARY_URL =
            "api/agents/transaction/summary/{email}/{token}";

    // Agent Networks
    public static final String AGENT_NETWORKS = "api/recharge/networks";

    // Agent API Key
    public static final String AGENT_API_KEY = "api/agents/key/{email}/{token}";

    // Agent Quick Recharge
    public static final String AGENT_QUICK_RECHARGE = "api/recharge/new";

    // Agent Scheduled Recharge
    public static final String AGENT_SCHEDULED_RECHARGE = "api/recharge/schedule";

    // Agent Quick Recharge Transaction History
    public static final String AGENT_QUICK_TRANSACTION_HISTORY =
            "api/recharge/query/{network}/{recipient}/{gateway}/{status}/{date_from}/{date_to" +
                    "}/{agent_email}";

    // Agent Quick Recharge Transaction History
    public static final String AGENT_SCHEDULED_TRANSACTION_HISTORY =
            "api/schedule/query/{network}/{recipient}/{gateway}/{status}/{date_from}/{date_to" +
                    "}/{agent_email}";

    /**
     * Customers
     **/

    // Login
    public static final String CUSTOMER_LOGIN_URL = "api/customer/auth";

    // Customer Signup
    public static final String CUSTOMER_SIGNUP_URL = "api/customer/signup/API";

    // Customer Signup
    public static final String CUSTOMER_TOPUP_URL = "api/customer/wallet";
}
