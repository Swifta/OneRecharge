package com.swifta.onerecharge.agent.agentregistration.registerresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 1/23/17.
 */

public class Wallet {
    @SerializedName("balance")
    @Expose
    private Integer balance;

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
