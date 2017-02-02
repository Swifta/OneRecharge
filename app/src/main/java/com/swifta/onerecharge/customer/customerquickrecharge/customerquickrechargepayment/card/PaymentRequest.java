package com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargepayment.card;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 2/2/17.
 */

public class PaymentRequest {
    @SerializedName("chargeObject")
    @Expose
    public ChargeObject chargeObject;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("paymentMethodId")
    @Expose
    public String paymentMethodId;
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("referenceId")
    @Expose
    public String referenceId;

    /**
     * No args constructor for use in serialization
     *
     */
    public PaymentRequest() {
    }

    /**
     *
     * @param amount
     * @param chargeObject
     * @param referenceId
     * @param paymentMethodId
     * @param currency
     */
    public PaymentRequest(ChargeObject chargeObject, String amount, String paymentMethodId,
                          String currency, String referenceId) {
        super();
        this.chargeObject = chargeObject;
        this.amount = amount;
        this.paymentMethodId = paymentMethodId;
        this.currency = currency;
        this.referenceId = referenceId;
    }
}
