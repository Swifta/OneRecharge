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
    public static final String MFISA_BASE_URL = "https://mfisa.com/mfisa/global/";

    /**
     * General
     **/
    // Available Countries
    public static final String AVAILABLE_COUNTRIES = "api/countries/available.json";

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

    // Agent Wallet Top Up
    public static final String AGENT_WALLET_TOPUP_URL = "api/agent/wallet/fund";

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

    // Logout
    public static final String AGENT_LOGOUT_URL = "api/logout/{email}/{token}";

    /**
     * Customers
     **/

    // Login
    public static final String CUSTOMER_LOGIN_URL = "api/customer/auth";

    // Customer Signup
    public static final String CUSTOMER_SIGNUP_URL = "api/customer/signup/API";

    // Customer Wallet Top Up
    public static final String CUSTOMER_WALLET_TOPUP_URL = "api/customer/wallet/fund";

    // Customer Wallet Balance
    public static final String CUSTOMER_WALLET_BALANCE_URL = "api/customer/wallet/balance";

    // Customer Quick Recharge from wallet
    public static final String CUSTOMER_WALLET_QUICK_RECHARGE = "api/recharge/new";

    // Logout
    public static final String CUSTOMER_LOGOUT_URL = "api/customer/logout/{email}/{token}";

    /**
     * Mfisa
     **/

    // Pay with card
    public static final String MFISA_PAY_WITH_CARD = "v1/pay";

    // Authorize with OTPa
    public static final String MFISA_AUTHORIZE_WITH_OTP = "v1/authorize";
}
