package com.swifta.onerecharge.agentregistration.registerrequestmodel;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class Uploads {

    private String identification;
    private String proofOfAddress;
    private String cac;

    public Uploads(String identification, String proofOfAddress, String cac) {
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
