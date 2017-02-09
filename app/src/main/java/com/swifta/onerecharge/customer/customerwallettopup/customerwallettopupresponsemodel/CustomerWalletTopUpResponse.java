package com.swifta.onerecharge.customer.customerwallettopup.customerwallettopupresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 2/3/17.
 */

public class CustomerWalletTopUpResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("customer_balance")
    @Expose
    public Double customerBalance;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Double getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(Double customerBalance) {
        this.customerBalance = customerBalance;
    }
}
