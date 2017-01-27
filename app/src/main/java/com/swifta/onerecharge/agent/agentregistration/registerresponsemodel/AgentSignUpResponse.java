package com.swifta.onerecharge.agent.agentregistration.registerresponsemodel;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class AgentSignUpResponse {

    public Integer status;
    public Data data;

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
