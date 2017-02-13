package com.swifta.onerecharge.customer.customerwalletbalance.balancerequestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 2/13/17.
 */

public class CustomerWalletBalanceRequest {
    @SerializedName("customer_token")
    @Expose
    private String customerToken;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;

    public CustomerWalletBalanceRequest(String customerToken, String customerEmail) {
        this.customerToken = customerToken;
        this.customerEmail = customerEmail;
    }
}
