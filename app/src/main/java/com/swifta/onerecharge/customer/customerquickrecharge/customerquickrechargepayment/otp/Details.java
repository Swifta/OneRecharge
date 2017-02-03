package com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargepayment.otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 2/3/17.
 */

public class Details {
    @SerializedName("mfisaTxnId")
    @Expose
    private String mfisaTxnId;

    public String getMfisaTxnId() {
        return mfisaTxnId;
    }

    public void setMfisaTxnId(String mfisaTxnId) {
        this.mfisaTxnId = mfisaTxnId;
    }
}
