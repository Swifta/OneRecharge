package com.swifta.onerecharge.agent.agentapikey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 11/3/16.
 */

public class AgentApiKeyResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private String data;

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(String data) {
        this.data = data;
    }
}
