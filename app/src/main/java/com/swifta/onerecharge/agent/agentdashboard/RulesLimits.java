package com.swifta.onerecharge.agent.agentdashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/15/16.
 */
public class RulesLimits {

    @SerializedName("send_monthly_statement")
    @Expose
    private Boolean sendMonthlyStatement;
    @SerializedName("send_daily_summary")
    @Expose
    private Boolean sendDailySummary;
    @SerializedName("rule_title")
    @Expose
    private String ruleTitle;
    @SerializedName("msisdn_purchase_limit")
    @Expose
    private Integer msisdnPurchaseLimit;
    @SerializedName("atm_purchase_limit")
    @Expose
    private Integer atmPurchaseLimit;
    @SerializedName("minimum_wallet")
    @Expose
    private Integer minimumWallet;
    @SerializedName("airtime_purchase_discount")
    @Expose
    private Double airtimePurchaseDiscount;
    @SerializedName("airtime_sold_commission")
    @Expose
    private Double airtimeSoldCommission;

    /**
     * @return The sendMonthlyStatement
     */
    public Boolean getSendMonthlyStatement() {
        return sendMonthlyStatement;
    }

    /**
     * @param sendMonthlyStatement The send_monthly_statement
     */
    public void setSendMonthlyStatement(Boolean sendMonthlyStatement) {
        this.sendMonthlyStatement = sendMonthlyStatement;
    }

    /**
     * @return The sendDailySummary
     */
    public Boolean getSendDailySummary() {
        return sendDailySummary;
    }

    /**
     * @param sendDailySummary The send_daily_summary
     */
    public void setSendDailySummary(Boolean sendDailySummary) {
        this.sendDailySummary = sendDailySummary;
    }

    /**
     * @return The ruleTitle
     */
    public String getRuleTitle() {
        return ruleTitle;
    }

    /**
     * @param ruleTitle The rule_title
     */
    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    /**
     * @return The msisdnPurchaseLimit
     */
    public Integer getMsisdnPurchaseLimit() {
        return msisdnPurchaseLimit;
    }

    /**
     * @param msisdnPurchaseLimit The msisdn_purchase_limit
     */
    public void setMsisdnPurchaseLimit(Integer msisdnPurchaseLimit) {
        this.msisdnPurchaseLimit = msisdnPurchaseLimit;
    }

    /**
     * @return The atmPurchaseLimit
     */
    public Integer getAtmPurchaseLimit() {
        return atmPurchaseLimit;
    }

    /**
     * @param atmPurchaseLimit The atm_purchase_limit
     */
    public void setAtmPurchaseLimit(Integer atmPurchaseLimit) {
        this.atmPurchaseLimit = atmPurchaseLimit;
    }

    /**
     * @return The minimumWallet
     */
    public Integer getMinimumWallet() {
        return minimumWallet;
    }

    /**
     * @param minimumWallet The minimum_wallet
     */
    public void setMinimumWallet(Integer minimumWallet) {
        this.minimumWallet = minimumWallet;
    }

    /**
     * @return The airtimePurchaseDiscount
     */
    public Double getAirtimePurchaseDiscount() {
        return airtimePurchaseDiscount;
    }

    /**
     * @param airtimePurchaseDiscount The airtime_purchase_discount
     */
    public void setAirtimePurchaseDiscount(Double airtimePurchaseDiscount) {
        this.airtimePurchaseDiscount = airtimePurchaseDiscount;
    }

    /**
     * @return The airtimeSoldCommission
     */
    public Double getAirtimeSoldCommission() {
        return airtimeSoldCommission;
    }

    /**
     * @param airtimeSoldCommission The airtime_sold_commission
     */
    public void setAirtimeSoldCommission(Double airtimeSoldCommission) {
        this.airtimeSoldCommission = airtimeSoldCommission;
    }
}
