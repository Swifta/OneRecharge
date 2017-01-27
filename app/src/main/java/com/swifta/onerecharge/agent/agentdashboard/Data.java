package com.swifta.onerecharge.agent.agentdashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/15/16.
 */
public class Data {
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("total_success")
    @Expose
    private Integer totalSuccess;
    @SerializedName("total_failed")
    @Expose
    private Integer totalFailed;
    @SerializedName("amount_success")
    @Expose
    private Integer amountSuccess;
    @SerializedName("amount_failed")
    @Expose
    private Integer amountFailed;
    @SerializedName("last_success_amount")
    @Expose
    private Integer lastSuccessAmount;
    @SerializedName("last_failed_amount")
    @Expose
    private Integer lastFailedAmount;
    @SerializedName("last_failed_description")
    @Expose
    private String lastFailedDescription;
    @SerializedName("last_failed_time")
    @Expose
    private String lastFailedTime;
    @SerializedName("last_success_time")
    @Expose
    private String lastSuccessTime;
    @SerializedName("wallet_balance")
    @Expose
    private String walletBalance;
    @SerializedName("rules_limits")
    @Expose
    private RulesLimits rulesLimits;
    @SerializedName("referral_id")
    @Expose
    private String referralId;
    @SerializedName("api_access_token")
    @Expose
    private ApiAccessToken apiAccessToken;
    @SerializedName("agent_type")
    @Expose
    private String agentType;
    @SerializedName("agent_class")
    @Expose
    private String agentClass;
    @SerializedName("joined")
    @Expose
    private String joined;

    /**
     * @return The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return The totalSuccess
     */
    public Integer getTotalSuccess() {
        return totalSuccess;
    }

    /**
     * @param totalSuccess The total_success
     */
    public void setTotalSuccess(Integer totalSuccess) {
        this.totalSuccess = totalSuccess;
    }

    /**
     * @return The totalFailed
     */
    public Integer getTotalFailed() {
        return totalFailed;
    }

    /**
     * @param totalFailed The total_failed
     */
    public void setTotalFailed(Integer totalFailed) {
        this.totalFailed = totalFailed;
    }

    /**
     * @return The amountSuccess
     */
    public Integer getAmountSuccess() {
        return amountSuccess;
    }

    /**
     * @param amountSuccess The amount_success
     */
    public void setAmountSuccess(Integer amountSuccess) {
        this.amountSuccess = amountSuccess;
    }

    /**
     * @return The amountFailed
     */
    public Integer getAmountFailed() {
        return amountFailed;
    }

    /**
     * @param amountFailed The amount_failed
     */
    public void setAmountFailed(Integer amountFailed) {
        this.amountFailed = amountFailed;
    }

    /**
     * @return The lastSuccessAmount
     */
    public Integer getLastSuccessAmount() {
        return lastSuccessAmount;
    }

    /**
     * @param lastSuccessAmount The last_success_amount
     */
    public void setLastSuccessAmount(Integer lastSuccessAmount) {
        this.lastSuccessAmount = lastSuccessAmount;
    }

    /**
     * @return The lastFailedAmount
     */
    public Integer getLastFailedAmount() {
        return lastFailedAmount;
    }

    /**
     * @param lastFailedAmount The last_failed_amount
     */
    public void setLastFailedAmount(Integer lastFailedAmount) {
        this.lastFailedAmount = lastFailedAmount;
    }

    /**
     * @return The lastFailedDescription
     */
    public String getLastFailedDescription() {
        return lastFailedDescription;
    }

    /**
     * @param lastFailedDescription The last_failed_description
     */
    public void setLastFailedDescription(String lastFailedDescription) {
        this.lastFailedDescription = lastFailedDescription;
    }

    /**
     * @return The lastFailedTime
     */
    public String getLastFailedTime() {
        return lastFailedTime;
    }

    /**
     * @param lastFailedTime The last_failed_time
     */
    public void setLastFailedTime(String lastFailedTime) {
        this.lastFailedTime = lastFailedTime;
    }

    /**
     * @return The lastSuccessTime
     */
    public String getLastSuccessTime() {
        return lastSuccessTime;
    }

    /**
     * @param lastSuccessTime The last_success_time
     */
    public void setLastSuccessTime(String lastSuccessTime) {
        this.lastSuccessTime = lastSuccessTime;
    }

    /**
     * @return The walletBalance
     */
    public String getWalletBalance() {
        return walletBalance;
    }

    /**
     * @param walletBalance The wallet_balance
     */
    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    /**
     * @return The rulesLimits
     */
    public RulesLimits getRulesLimits() {
        return rulesLimits;
    }

    /**
     * @param rulesLimits The rules_limits
     */
    public void setRulesLimits(RulesLimits rulesLimits) {
        this.rulesLimits = rulesLimits;
    }

    /**
     * @return The referralId
     */
    public String getReferralId() {
        return referralId;
    }

    /**
     * @param referralId The referral_id
     */
    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    /**
     * @return The apiAccessToken
     */
    public ApiAccessToken getApiAccessToken() {
        return apiAccessToken;
    }

    /**
     * @param apiAccessToken The api_access_token
     */
    public void setApiAccessToken(ApiAccessToken apiAccessToken) {
        this.apiAccessToken = apiAccessToken;
    }

    /**
     * @return The agentType
     */
    public String getAgentType() {
        return agentType;
    }

    /**
     * @param agentType The agent_type
     */
    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    /**
     * @return The agentClass
     */
    public String getAgentClass() {
        return agentClass;
    }

    /**
     * @param agentClass The agent_class
     */
    public void setAgentClass(String agentClass) {
        this.agentClass = agentClass;
    }

    /**
     * @return The joined
     */
    public String getJoined() {
        return joined;
    }

    /**
     * @param joined The joined
     */
    public void setJoined(String joined) {
        this.joined = joined;
    }

}
