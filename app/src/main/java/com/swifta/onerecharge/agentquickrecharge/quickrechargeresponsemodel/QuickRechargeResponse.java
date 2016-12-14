package com.swifta.onerecharge.agentquickrecharge.quickrechargeresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/19/16.
 */
public class QuickRechargeResponse {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("data")
    @Expose
    public Data data;

    public Integer getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }
}
