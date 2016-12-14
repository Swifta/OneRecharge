package com.swifta.onerecharge.agentregistration.registerresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/8/16.
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

    /**
     * @return The identification
     */
    public String getIdentification() {
        return identification;
    }

    /**
     * @param identification The identification
     */
    public void setIdentification(String identification) {
        this.identification = identification;
    }

    /**
     * @return The proofOfAddress
     */
    public String getProofOfAddress() {
        return proofOfAddress;
    }

    /**
     * @param proofOfAddress The proof_of_address
     */
    public void setProofOfAddress(String proofOfAddress) {
        this.proofOfAddress = proofOfAddress;
    }

    /**
     * @return The cac
     */
    public String getCac() {
        return cac;
    }

    /**
     * @param cac The cac
     */
    public void setCac(String cac) {
        this.cac = cac;
    }

}
