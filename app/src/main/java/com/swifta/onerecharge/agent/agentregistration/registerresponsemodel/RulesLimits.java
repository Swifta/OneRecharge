package com.swifta.onerecharge.agent.agentregistration.registerresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 1/23/17.
 */

public class RulesLimits {
    @SerializedName("send_daily_summary")
    @Expose
    private Boolean sendDailySummary;
    @SerializedName("send_monthly_statement")
    @Expose
    private Boolean sendMonthlyStatement;

    public Boolean getSendDailySummary() {
        return sendDailySummary;
    }

    public void setSendDailySummary(Boolean sendDailySummary) {
        this.sendDailySummary = sendDailySummary;
    }

    public Boolean getSendMonthlyStatement() {
        return sendMonthlyStatement;
    }

    public void setSendMonthlyStatement(Boolean sendMonthlyStatement) {
        this.sendMonthlyStatement = sendMonthlyStatement;
    }
}
