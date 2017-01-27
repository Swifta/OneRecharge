package com.swifta.onerecharge.customer.customerregistration.loginresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 11/4/16.
 */

public class LinkedAgent {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("country")
    @Expose
    private String country;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
