package com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargepayment.card;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 2/2/17.
 */

public class Details {
    @SerializedName("otpref")
    @Expose
    private String otpref;

    public String getOtpref() {
        return otpref;
    }

    public void setOtpref(String otpref) {
        this.otpref = otpref;
    }
}
