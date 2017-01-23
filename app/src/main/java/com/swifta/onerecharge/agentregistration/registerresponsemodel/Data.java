package com.swifta.onerecharge.agentregistration.registerresponsemodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class Data {
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("other_names")
    @Expose
    private String otherNames;
    @SerializedName("email_address")
    @Expose
    private String emailAddress;
    @SerializedName("personal_phone_number")
    @Expose
    private String personalPhoneNumber;
    @SerializedName("alternate_phone_number")
    @Expose
    private String alternatePhoneNumber;
    @SerializedName("agent_type")
    @Expose
    private String agentType;
    @SerializedName("agent_class")
    @Expose
    private String agentClass;
    @SerializedName("referral")
    @Expose
    private String referral;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("is_ussd")
    @Expose
    private Boolean isUssd;
    @SerializedName("widget")
    @Expose
    private Widget widget;
    @SerializedName("customers")
    @Expose
    private List<Object> customers = null;
    @SerializedName("transactions")
    @Expose
    private List<Object> transactions = null;
    @SerializedName("changed_password")
    @Expose
    private Boolean changedPassword;
    @SerializedName("approval_log")
    @Expose
    private List<Object> approvalLog = null;
    @SerializedName("approved")
    @Expose
    private Boolean approved;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("api_access_log")
    @Expose
    private List<Object> apiAccessLog = null;
    @SerializedName("settlement_log")
    @Expose
    private List<Object> settlementLog = null;
    @SerializedName("topups")
    @Expose
    private List<Object> topups = null;
    @SerializedName("wallet")
    @Expose
    private Wallet wallet;
    @SerializedName("rules_log")
    @Expose
    private List<Object> rulesLog = null;
    @SerializedName("rules_limits")
    @Expose
    private RulesLimits rulesLimits;
    @SerializedName("uploads")
    @Expose
    private Uploads uploads;

    private Integer code;

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPersonalPhoneNumber() {
        return personalPhoneNumber;
    }

    public void setPersonalPhoneNumber(String personalPhoneNumber) {
        this.personalPhoneNumber = personalPhoneNumber;
    }

    public String getAlternatePhoneNumber() {
        return alternatePhoneNumber;
    }

    public void setAlternatePhoneNumber(String alternatePhoneNumber) {
        this.alternatePhoneNumber = alternatePhoneNumber;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getAgentClass() {
        return agentClass;
    }

    public void setAgentClass(String agentClass) {
        this.agentClass = agentClass;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsUssd() {
        return isUssd;
    }

    public void setIsUssd(Boolean isUssd) {
        this.isUssd = isUssd;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public List<Object> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Object> customers) {
        this.customers = customers;
    }

    public List<Object> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Object> transactions) {
        this.transactions = transactions;
    }

    public Boolean getChangedPassword() {
        return changedPassword;
    }

    public void setChangedPassword(Boolean changedPassword) {
        this.changedPassword = changedPassword;
    }

    public List<Object> getApprovalLog() {
        return approvalLog;
    }

    public void setApprovalLog(List<Object> approvalLog) {
        this.approvalLog = approvalLog;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Object> getApiAccessLog() {
        return apiAccessLog;
    }

    public void setApiAccessLog(List<Object> apiAccessLog) {
        this.apiAccessLog = apiAccessLog;
    }

    public List<Object> getSettlementLog() {
        return settlementLog;
    }

    public void setSettlementLog(List<Object> settlementLog) {
        this.settlementLog = settlementLog;
    }

    public List<Object> getTopups() {
        return topups;
    }

    public void setTopups(List<Object> topups) {
        this.topups = topups;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public List<Object> getRulesLog() {
        return rulesLog;
    }

    public void setRulesLog(List<Object> rulesLog) {
        this.rulesLog = rulesLog;
    }

    public RulesLimits getRulesLimits() {
        return rulesLimits;
    }

    public void setRulesLimits(RulesLimits rulesLimits) {
        this.rulesLimits = rulesLimits;
    }

    public Uploads getUploads() {
        return uploads;
    }

    public void setUploads(Uploads uploads) {
        this.uploads = uploads;
    }


}
