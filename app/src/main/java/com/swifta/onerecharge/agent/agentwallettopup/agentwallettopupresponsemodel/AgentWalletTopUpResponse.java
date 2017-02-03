package com.swifta.onerecharge.agent.agentwallettopup.agentwallettopupresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 2/3/17.
 */

public class AgentWalletTopUpResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
