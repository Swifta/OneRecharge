package com.swifta.onerecharge.agent.agentquicktransactionhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/26/16.
 */

public class Summary {
    @SerializedName("total_total")
    @Expose
    private Integer totalTotal;
    @SerializedName("successful")
    @Expose
    private Integer successful;
    @SerializedName("failed")
    @Expose
    private Integer failed;
    @SerializedName("cash_sold")
    @Expose
    private Integer cashSold;

    /**
     * @return The totalTotal
     */
    public Integer getTotalTotal() {
        return totalTotal;
    }

    /**
     * @param totalTotal The total_total
     */
    public void setTotalTotal(Integer totalTotal) {
        this.totalTotal = totalTotal;
    }

    /**
     * @return The successful
     */
    public Integer getSuccessful() {
        return successful;
    }

    /**
     * @param successful The successful
     */
    public void setSuccessful(Integer successful) {
        this.successful = successful;
    }

    /**
     * @return The failed
     */
    public Integer getFailed() {
        return failed;
    }

    /**
     * @param failed The failed
     */
    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    /**
     * @return The cashSold
     */
    public Integer getCashSold() {
        return cashSold;
    }

    /**
     * @param cashSold The cash_sold
     */
    public void setCashSold(Integer cashSold) {
        this.cashSold = cashSold;
    }
}
