package com.swifta.onerecharge.agentquicktransactionhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by moyinoluwa on 9/26/16.
 */

public class Row extends RealmObject {
    @PrimaryKey
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("recipient")
    @Expose
    private String recipient;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_token")
    @Expose
    private String userToken;
    @SerializedName("network")
    @Expose
    private String network;
    @SerializedName("gateway")
    @Expose
    private String gateway;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("response_description")
    @Expose
    private String responseDescription;
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("event_time")
    @Expose
    private String eventTime;
    @SerializedName("commission")
    @Expose
    private Integer commission;
    @SerializedName("discount")
    @Expose
    private Double discount;
    @SerializedName("successful")
    @Expose
    private Boolean successful;
    @SerializedName("test")
    @Expose
    private Boolean test;

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
     * @return The gateway
     */
    public String getGateway() {
        return gateway;
    }

    /**
     * @param gateway The gateway
     */
    public void setGateway(String gateway) {
        this.gateway = gateway;
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
     * @return The responseDescription
     */
    public String getResponseDescription() {
        return responseDescription;
    }

    /**
     * @param responseDescription The response_description
     */
    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    /**
     * @return The response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response The response
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return The eventTime
     */
    public String getEventTime() {
        return eventTime;
    }

    /**
     * @param eventTime The event_time
     */
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * @return The commission
     */
    public Integer getCommission() {
        return commission;
    }

    /**
     * @param commission The commission
     */
    public void setCommission(Integer commission) {
        this.commission = commission;
    }

    /**
     * @return The discount
     */
    public Double getDiscount() {
        return discount;
    }

    /**
     * @param discount The discount
     */
    public void setDiscount(Double discount) {
        this.discount = discount;
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

    /**
     * @return The test
     */
    public Boolean getTest() {
        return test;
    }

    /**
     * @param test The test
     */
    public void setTest(Boolean test) {
        this.test = test;
    }

}

