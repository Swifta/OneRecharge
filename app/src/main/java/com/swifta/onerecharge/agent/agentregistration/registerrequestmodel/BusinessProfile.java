package com.swifta.onerecharge.agent.agentregistration.registerrequestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class BusinessProfile {

    @SerializedName("company_trading_name")
    @Expose
    public String companyTradingName;
    @SerializedName("company_registration_number")
    @Expose
    public String companyRegistrationNumber;
    @SerializedName("company_telephone")
    @Expose
    public String companyTelephone;
    @SerializedName("company_contact_name")
    @Expose
    public String companyContactName;
    @SerializedName("company_contact_number")
    @Expose
    public String companyContactNumber;

    /**
     * No args constructor for use in serialization
     */
    public BusinessProfile() {
    }

    /**
     * @param companyContactName
     * @param companyTradingName
     * @param companyContactNumber
     * @param companyTelephone
     * @param companyRegistrationNumber
     */
    public BusinessProfile(String companyTradingName, String companyRegistrationNumber, String companyTelephone, String companyContactName, String companyContactNumber) {
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
