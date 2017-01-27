package com.swifta.onerecharge.agent.agentscheduledtransactionhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by moyinoluwa on 1/16/17.
 */

public class RealmRow extends RealmObject {
    @PrimaryKey
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_token")
    @Expose
    private String userToken;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("recipient")
    @Expose
    private String recipient;
    @SerializedName("network")
    @Expose
    private String network;
    @SerializedName("go_time")
    @Expose
    private String goTime;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("cancelled")
    @Expose
    private Boolean cancelled;
    @SerializedName("successful")
    @Expose
    private Boolean successful;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return The userToken
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * @param userToken The user_token
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    /**
     * @return The amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * @param amount The amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * @return The recipient
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * @param recipient The recipient
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * @return The network
     */
    public String getNetwork() {
        return network;
    }

    /**
     * @param network The network
     */
    public void setNetwork(String network) {
        this.network = network;
    }

    /**
     * @return The goTime
     */
    public String getGoTime() {
        return goTime;
    }

    /**
     * @param goTime The go_time
     */
    public void setGoTime(String goTime) {
        this.goTime = goTime;
    }

    /**
     * @return The v
     */
    public Integer getV() {
        return v;
    }

    /**
     * @param v The __v
     */
    public void setV(Integer v) {
        this.v = v;
    }

    /**
     * @return The transactionId
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId The transaction_id
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return The createdTime
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime The created_time
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * @return The cancelled
     */
    public Boolean getCancelled() {
        return cancelled;
    }

    /**
     * @param cancelled The cancelled
     */
    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * @return The successful
     */
    public Boolean getSuccessful() {
        return successful;
    }

    /**
     * @param successful The successful
     */
    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }
}

