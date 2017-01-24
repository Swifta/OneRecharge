package com.swifta.onerecharge.agentregistration.registerrequestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class AgentSignUpBody {

    @SerializedName("data")
    @Expose
    public Data data;

    /**
     * No args constructor for use in serialization
     */
    public AgentSignUpBody() {
    }

    /**
     * @param data
     */
    public AgentSignUpBody(Data data) {
        this.data = data;
    }

}