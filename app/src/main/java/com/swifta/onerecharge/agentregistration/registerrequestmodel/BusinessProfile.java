package com.swifta.onerecharge.agentregistration.registerrequestmodel;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class BusinessProfile {

    private String companyTradingName;
    private String companyRegistrationNumber;
    private String companyTelephone;
    private String companyContactName;
    private String companyContactNumber;

    public BusinessProfile(String companyTradingName, String
            companyRegistrationNumber, String companyTelephone, String
                                   companyContactName, String companyContactNumber) {
        this.companyTradingName = companyTradingName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.companyTelephone = companyTelephone;
        this.companyContactName = companyContactName;
        this.companyContactNumber = companyContactNumber;
    }

    public String getCompanyTradingName() {
        return companyTradingName;
    }

    public String getCompanyRegistrationNumber() {
        return companyRegistrationNumber;
    }

    public String getCompanyTelephone() {
        return companyTelephone;
    }

    public String getCompanyContactName() {
        return companyContactName;
    }

    public String getCompanyContactNumber() {
        return companyContactNumber;
    }
}
