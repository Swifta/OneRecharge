package com.swifta.onerecharge.agentregistration.registerresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class RulesLimits {

    @SerializedName("send_daily_summary")
    @Expose
    private Boolean sendDailySummary;
    @SerializedName("send_monthly_statement")
    @Expose
    private Boolean sendMonthlyStatement;

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
}
