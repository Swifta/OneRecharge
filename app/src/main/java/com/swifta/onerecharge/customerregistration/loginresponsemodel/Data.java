package com.swifta.onerecharge.customerregistration.loginresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 11/4/16.
 */

public class Data {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("profile")
    @Expose
    private Profile profile;

    /**
     * @return The token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return The profile
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * @param profile The profile
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
