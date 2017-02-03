package com.swifta.onerecharge.customer.customerwallettopup.customerwallettopupresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 2/3/17.
 */

public class Data {

    @SerializedName("topped_id")
    @Expose
    private String toppedId;
    @SerializedName("channel")
    @Expose
    private String channel;
    @SerializedName("topup_amount")
    @Expose
    private Integer topupAmount;
    @SerializedName("topup_by_token")
    @Expose
    private String topupByToken;
    @SerializedName("topup_by")
    @Expose
    private String topupBy;
    @SerializedName("topup_description")
    @Expose
    private String topupDescription;
    @SerializedName("topup_status")
    @Expose
    private Boolean topupStatus;
    @SerializedName("topup_tranx_ref")
    @Expose
    private String topupTranxRef;
    @SerializedName("topup_ref_code")
    @Expose
    private String topupRefCode;
    @SerializedName("topup_tranx_amount")
    @Expose
    private Integer topupTranxAmount;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("topup_time")
    @Expose
    private String topupTime;
    @SerializedName("message")
    @Expose
    private String message;

    public String getToppedId() {
        return toppedId;
    }

    public void setToppedId(String toppedId) {
        this.toppedId = toppedId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getTopupAmount() {
        return topupAmount;
    }

    public void setTopupAmount(Integer topupAmount) {
        this.topupAmount = topupAmount;
    }

    public String getTopupByToken() {
        return topupByToken;
    }

    public void setTopupByToken(String topupByToken) {
        this.topupByToken = topupByToken;
    }

    public String getTopupBy() {
        return topupBy;
    }

    public void setTopupBy(String topupBy) {
        this.topupBy = topupBy;
    }

    public String getTopupDescription() {
        return topupDescription;
    }

    public void setTopupDescription(String topupDescription) {
        this.topupDescription = topupDescription;
    }

    public Boolean getTopupStatus() {
        return topupStatus;
    }

    public void setTopupStatus(Boolean topupStatus) {
        this.topupStatus = topupStatus;
    }

    public String getTopupTranxRef() {
        return topupTranxRef;
    }

    public void setTopupTranxRef(String topupTranxRef) {
        this.topupTranxRef = topupTranxRef;
    }

    public String getTopupRefCode() {
        return topupRefCode;
    }

    public void setTopupRefCode(String topupRefCode) {
        this.topupRefCode = topupRefCode;
    }

    public Integer getTopupTranxAmount() {
        return topupTranxAmount;
    }

    public void setTopupTranxAmount(Integer topupTranxAmount) {
        this.topupTranxAmount = topupTranxAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTopupTime() {
        return topupTime;
    }

    public void setTopupTime(String topupTime) {
        this.topupTime = topupTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
