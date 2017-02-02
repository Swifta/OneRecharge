package com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargepayment.card;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 2/2/17.
 */

public class ChargeObject {
    @SerializedName("cardNo")
    @Expose
    public String cardNo;
    @SerializedName("expiryMonth")
    @Expose
    public String expiryMonth;
    @SerializedName("expiryYear")
    @Expose
    public String expiryYear;
    @SerializedName("cvv")
    @Expose
    public String cvv;
    @SerializedName("pin")
    @Expose
    public String pin;

    /**
     * No args constructor for use in serialization
     */
    public ChargeObject() {
    }

    /**
     * @param expiryYear
     * @param cardNo
     * @param pin
     * @param expiryMonth
     * @param cvv
     */
    public ChargeObject(String cardNo, String expiryMonth, String expiryYear, String cvv, String pin) {
        super();
        this.cardNo = cardNo;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
        this.pin = pin;
    }

}
