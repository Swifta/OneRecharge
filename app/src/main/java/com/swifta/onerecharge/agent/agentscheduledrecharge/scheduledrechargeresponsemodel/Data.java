package com.swifta.onerecharge.agent.agentscheduledrecharge.scheduledrechargeresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/20/16.
 */
public class Data {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * @return The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
