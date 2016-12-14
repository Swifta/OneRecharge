package com.swifta.onerecharge.agentregistration.registerrequestmodel;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class Data {

    public String surname;
    public String otherNames;
    public String emailAddress;
    public String personalPhoneNumber;
    public String alternatePhoneNumber;
    public String agentType;
    public String agentClass;
    public String referral;
    public Integer agreeTerms;
    public Uploads uploads;
    public BusinessProfile businessProfile;

    public Data(String surname, String otherNames, String emailAddress,
                String personalPhoneNumber, String alternatePhoneNumber,
                String agentType, String agentClass, String referral, Integer
                agreeTerms, Uploads uploads, BusinessProfile businessProfile) {

        this.surname = surname;
        this.otherNames = otherNames;
        this.emailAddress = emailAddress;
        this.personalPhoneNumber = personalPhoneNumber;
        this.alternatePhoneNumber = alternatePhoneNumber;
        this.agentType = agentType;
        this.agentClass = agentClass;
        this.referral = referral;
        this.agreeTerms = agreeTerms;
        this.uploads = uploads;
        this.businessProfile = businessProfile;
    }

}
