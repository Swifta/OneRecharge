package com.swifta.onerecharge.agent.agentscheduledtransactionhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/26/16.
 */

public class Summary {
    @SerializedName("total_total")
    @Expose
    private Integer totalTotal;
    @SerializedName("done")
    @Expose
    private Integer done;
    @SerializedName("pending")
    @Expose
    private Integer pending;
    @SerializedName("done_cash_worth")
    @Expose
    private Integer doneCashWorth;
    @SerializedName("pending_cash_worth")
    @Expose
    private Integer pendingCashWorth;

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
     * @return The done
     */
    public Integer getDone() {
        return done;
    }

    /**
     * @param done The done
     */
    public void setDone(Integer done) {
        this.done = done;
    }

    /**
     * @return The pending
     */
    public Integer getPending() {
        return pending;
    }

    /**
     * @param pending The pending
     */
    public void setPending(Integer pending) {
        this.pending = pending;
    }

    /**
     * @return The doneCashWorth
     */
    public Integer getDoneCashWorth() {
        return doneCashWorth;
    }

    /**
     * @param doneCashWorth The done_cash_worth
     */
    public void setDoneCashWorth(Integer doneCashWorth) {
        this.doneCashWorth = doneCashWorth;
    }

    /**
     * @return The pendingCashWorth
     */
    public Integer getPendingCashWorth() {
        return pendingCashWorth;
    }

    /**
     * @param pendingCashWorth The pending_cash_worth
     */
    public void setPendingCashWorth(Integer pendingCashWorth) {
        this.pendingCashWorth = pendingCashWorth;
    }
}
