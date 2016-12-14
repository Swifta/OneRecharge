package com.swifta.onerecharge.customerregistration.registerresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 11/4/16.
 */

public class Wallet {
    @SerializedName("promo")
    @Expose
    private Integer promo;
    @SerializedName("discounts")
    @Expose
    private Integer discounts;
    @SerializedName("recharged")
    @Expose
    private Integer recharged;
    @SerializedName("balance")
    @Expose
    private Integer balance;

    /**
     * @return The promo
     */
    public Integer getPromo() {
        return promo;
    }

    /**
     * @param promo The promo
     */
    public void setPromo(Integer promo) {
        this.promo = promo;
    }

    /**
     * @return The discounts
     */
    public Integer getDiscounts() {
        return discounts;
    }

    /**
     * @param discounts The discounts
     */
    public void setDiscounts(Integer discounts) {
        this.discounts = discounts;
    }

    /**
     * @return The recharged
     */
    public Integer getRecharged() {
        return recharged;
    }

    /**
     * @param recharged The recharged
     */
    public void setRecharged(Integer recharged) {
        this.recharged = recharged;
    }

    /**
     * @return The balance
     */
    public Integer getBalance() {
        return balance;
    }

    /**
     * @param balance The balance
     */
    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
