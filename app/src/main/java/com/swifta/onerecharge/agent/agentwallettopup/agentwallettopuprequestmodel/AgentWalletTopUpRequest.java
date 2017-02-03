package com.swifta.onerecharge.agent.agentwallettopup.agentwallettopuprequestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 2/3/17.
 */

public class AgentWalletTopUpRequest {

    @SerializedName("amount")
    @Expose
    public Integer amount;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("transactionId")
    @Expose
    public String transactionId;
    @SerializedName("referenceId")
    @Expose
    public String referenceId;
    @SerializedName("email")
    @Expose
    public String email;

    /**
     * No args constructor for use in serialization
     */
    public AgentWalletTopUpRequest() {
    }

    /**
     * @param amount
     * @param transactionId
     * @param email
     * @param token
     * @param description
     * @param referenceId
     */
    public AgentWalletTopUpRequest(Integer amount, String token, String description, String transactionId, String referenceId, String email) {
        super();
        this.amount = amount;
        this.token = token;
        this.description = description;
        this.transactionId = transactionId;
        this.referenceId = referenceId;
        this.email = email;
    }
}
