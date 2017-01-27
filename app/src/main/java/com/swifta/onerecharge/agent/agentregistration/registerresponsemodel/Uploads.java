package com.swifta.onerecharge.agent.agentregistration.registerresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 1/23/17.
 */

public class Uploads {
    @SerializedName("identification")
    @Expose
    private String identification;
    @SerializedName("proof_of_address")
    @Expose
    private String proofOfAddress;
    @SerializedName("cac")
    @Expose
    private String cac;

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getProofOfAddress() {
        return proofOfAddress;
    }

    public void setProofOfAddress(String proofOfAddress) {
        this.proofOfAddress = proofOfAddress;
    }

    public String getCac() {
        return cac;
    }

    public void setCac(String cac) {
        this.cac = cac;
    }

}
