package com.swifta.onerecharge.agentregistration.registerrequestmodel;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class Data {

    private String surname;
    private String otherNames;
    private String emailAddress;
    private String personalPhoneNumber;
    private String alternatePhoneNumber;
    private String agentType;
    private String agentClass;
    private String referral;
    private String country;
    private String region;
    private Integer agreeTerms;
    private Uploads uploads;
    private BusinessProfile businessProfile;

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
