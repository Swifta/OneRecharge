package com.swifta.onerecharge.agent.agentregistration.registerrequestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class Uploads {

    @SerializedName("identification")
    @Expose
    public String identification;
    @SerializedName("proof_of_address")
    @Expose
    public String proofOfAddress;
    @SerializedName("cac")
    @Expose
    public String cac;

    /**
     * No args constructor for use in serialization
     *
     */
    public Uploads() {
    }

    /**
     *
     * @param cac
     * @param proofOfAddress
     * @param identification
     */
    public Uploads(String identification, String proofOfAddress, String cac) {
        super();
        this.identification = identification;
        this.proofOfAddress = proofOfAddress;
        this.cac = cac;
    }

    public String getIdentification() {
        return identification;
    }

    public String getProofOfAddress() {
        return proofOfAddress;
    }

    public String getCac() {
        return cac;
    }
}
