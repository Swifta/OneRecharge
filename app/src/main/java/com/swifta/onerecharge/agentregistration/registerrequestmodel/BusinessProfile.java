package com.swifta.onerecharge.agentregistration.registerrequestmodel;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class BusinessProfile {

    public String companyTradingName;
    public String companyRegistrationNumber;
    public String companyTelephone;
    public String companyContactName;
    public String companyContactNumber;

    public BusinessProfile(String companyTradingName, String
            companyRegistrationNumber, String companyTelephone, String
                                   companyContactName, String companyContactNumber) {
        this.companyTradingName = companyTradingName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.companyTelephone = companyTelephone;
        this.companyContactName = companyContactName;
        this.companyContactNumber = companyContactNumber;
    }
}
