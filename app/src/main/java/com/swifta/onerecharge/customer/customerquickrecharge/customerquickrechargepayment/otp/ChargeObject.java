package com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargepayment.otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 2/2/17.
 */

public class ChargeObject {
    @SerializedName("otpref")
    @Expose
    public String otpref;
    @SerializedName("otp")
    @Expose
    public String otp;

    /**
     * No args constructor for use in serialization
     */
    public ChargeObject() {
    }

    /**
     * @param otpref
     * @param otp
     */
    public ChargeObject(String otpref, String otp) {
        super();
        this.otpref = otpref;
        this.otp = otp;
    }

}
