package com.swifta.onerecharge.agent.agentregistration.registerrequestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class Data {

    @SerializedName("surname")
    @Expose
    public String surname;
    @SerializedName("other_names")
    @Expose
    public String otherNames;
    @SerializedName("email_address")
    @Expose
    public String emailAddress;
    @SerializedName("personal_phone_number")
    @Expose
    public String personalPhoneNumber;
    @SerializedName("alternate_phone_number")
    @Expose
    public String alternatePhoneNumber;
    @SerializedName("agent_type")
    @Expose
    public String agentType;
    @SerializedName("agent_class")
    @Expose
    public String agentClass;
    @SerializedName("referral")
    @Expose
    public String referral;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("region")
    @Expose
    public String region;
    @SerializedName("agree_terms")
    @Expose
    public Integer agreeTerms;
    @SerializedName("uploads")
    @Expose
    public Uploads uploads;
    @SerializedName("business_profile")
    @Expose
    public BusinessProfile businessProfile;

    /**
     * No args constructor for use in serialization
     */
    public Data() {
    }

    /**
     * @param region
     * @param agreeTerms
     * @param alternatePhoneNumber
     * @param referral
     * @param personalPhoneNumber
     * @param agentType
     * @param emailAddress
     * @param surname
     * @param agentClass
     * @param uploads
     * @param businessProfile
     * @param otherNames
     * @param country
     */
    public Data(String surname, String otherNames, String emailAddress, String personalPhoneNumber, String alternatePhoneNumber, String agentType, String agentClass, String referral, String country, String region, Integer agreeTerms, Uploads uploads, BusinessProfile businessProfile) {
        this.surname = surname;
        this.otherNames = otherNames;
        this.emailAddress = emailAddress;
        this.personalPhoneNumber = personalPhoneNumber;
        this.alternatePhoneNumber = alternatePhoneNumber;
        this.agentType = agentType;
        this.agentClass = agentClass;
        this.referral = referral;
        this.country = country;
        this.region = region;
        this.agreeTerms = agreeTerms;
        this.uploads = uploads;
        this.businessProfile = businessProfile;
    }

    @Override
    public String toString() {
        return surname + "\n" + otherNames + "\n" + emailAddress + "\n" + personalPhoneNumber +
                "\n" + alternatePhoneNumber + "\n" + agentType + "\n" + agentClass + "\n" +
                referral + "\n" + country + "\n" + region + "\n" + agreeTerms + "\n" + uploads
                .getIdentification() + "\n" + uploads.getProofOfAddress() + "\n" + uploads.getCac
                () + "\n" + businessProfile.getCompanyTradingName() + "\n" + businessProfile
                .getCompanyRegistrationNumber() + businessProfile.getCompanyTelephone() + "\n" +
                businessProfile.getCompanyContactName() + "\n" + businessProfile.getCompanyContactNumber();
    }
}
