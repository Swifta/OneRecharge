package com.swifta.onerecharge.agentregistration.registerresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class Wallet {

    @SerializedName("balance")
    @Expose
    private Integer balance;

    /**
     * @return The balance
     */
    public Integer getBalance() {
        return balance;
    }

    /**
     * @param balance The balance
     */
    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
