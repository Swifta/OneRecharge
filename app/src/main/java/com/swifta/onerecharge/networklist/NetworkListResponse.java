package com.swifta.onerecharge.networklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moyinoluwa on 9/28/16.
 */

public class NetworkListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<String> data = new ArrayList<>();

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
    public List<String> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<String> data) {
        this.data = data;
    }

}
