package com.swifta.onerecharge.agentquickrecharge.quickrechargerequestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/19/16.
 */
public class QuickRechargeRequest {
    @SerializedName("recipient")
    @Expose
    public String recipient;
    @SerializedName("amount")
    @Expose
    public Integer amount;
    @SerializedName("network")
    @Expose
    public String network;
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("owner")
    @Expose
    public String owner;

    public QuickRechargeRequest(String recipient, Integer amount, String network, String
            customerId, String owner) {
        this.recipient = recipient;
        this.amount = amount;
        this.network = network;
        this.customerId = customerId;
        this.owner = owner;
    }
}
