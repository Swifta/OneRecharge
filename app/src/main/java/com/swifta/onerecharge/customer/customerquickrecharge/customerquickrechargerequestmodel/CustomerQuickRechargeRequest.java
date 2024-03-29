package com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargerequestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 2/2/17.
 */

public class CustomerQuickRechargeRequest {

    @SerializedName("recipient")
    @Expose
    private String recipient;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("network")
    @Expose
    private String network;
    @SerializedName("isCardTransaction")
    @Expose
    private Boolean isCardTransaction;
    @SerializedName("email")
    @Expose
    private String email;

    public CustomerQuickRechargeRequest(String recipient, Integer amount, String network,
                                        Boolean isCardTransaction, String email) {
        this.recipient = recipient;
        this.amount = amount;
        this.network = network;
        this.isCardTransaction = isCardTransaction;
        this.email = email;
    }
}
