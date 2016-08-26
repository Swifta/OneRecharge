package com.swifta.onerecharge.resetagentpassword;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by moyinoluwa on 8/26/16.
 */
public class AgentPassword {

    private Integer status;
    private Data data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
    public Data getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Data data) {
        this.data = data;
    }
}