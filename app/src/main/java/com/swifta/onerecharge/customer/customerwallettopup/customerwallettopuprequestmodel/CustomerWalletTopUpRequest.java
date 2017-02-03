package com.swifta.onerecharge.customer.customerwallettopup.customerwallettopuprequestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 2/3/17.
 */

public class CustomerWalletTopUpRequest {

    @SerializedName("channel")
    @Expose
    public String channel;
    @SerializedName("amount")
    @Expose
    public Integer amount;
    @SerializedName("customer_token")
    @Expose
    public String customerToken;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("transactionId")
    @Expose
    public String transactionId;
    @SerializedName("referenceId")
    @Expose
    public String referenceId;
    @SerializedName("customer_email")
    @Expose
    public String customerEmail;

    /**
     * No args constructor for use in serialization
     */
    public CustomerWalletTopUpRequest() {
    }

    /**
     * @param amount
     * @param transactionId
     * @param status
     * @param description
     * @param customerToken
     * @param referenceId
     * @param customerEmail
     * @param channel
     */
    public CustomerWalletTopUpRequest(String channel, Integer amount, String customerToken, String description, Boolean status, String transactionId, String referenceId, String customerEmail) {
        super();
        this.channel = channel;
        this.amount = amount;
        this.customerToken = customerToken;
        this.description = description;
        this.status = status;
        this.transactionId = transactionId;
        this.referenceId = referenceId;
        this.customerEmail = customerEmail;
    }

}
