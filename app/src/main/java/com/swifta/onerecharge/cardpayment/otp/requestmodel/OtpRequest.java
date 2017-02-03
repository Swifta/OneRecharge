package com.swifta.onerecharge.cardpayment.otp.requestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 2/2/17.
 */

public class OtpRequest {
    @SerializedName("chargeObject")
    @Expose
    public ChargeObject chargeObject;
    @SerializedName("paymentMethodId")
    @Expose
    public String paymentMethodId;

    /**
     * No args constructor for use in serialization
     */
    public OtpRequest() {
    }

    /**
     * @param chargeObject
     * @param paymentMethodId
     */
    public OtpRequest(ChargeObject chargeObject, String paymentMethodId) {
        super();
        this.chargeObject = chargeObject;
        this.paymentMethodId = paymentMethodId;
    }

}
