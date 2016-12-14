package com.swifta.onerecharge.agentregistration.registerresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moyinoluwa on 9/28/16.
 */

public class Op {
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
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("customers")
    @Expose
    private List<Object> customers = new ArrayList<>();
    @SerializedName("transactions")
    @Expose
    private List<Object> transactions = new ArrayList<Object>();
    @SerializedName("changed_password")
    @Expose
    private Boolean changedPassword;
    @SerializedName("approval_log")
    @Expose
    private List<Object> approvalLog = new ArrayList<>();
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
    private List<Object> apiAccessLog = new ArrayList<Object>();
    @SerializedName("settlement_log")
    @Expose
    private List<Object> settlementLog = new ArrayList<>();
    @SerializedName("topups")
    @Expose
    private List<Object> topups = new ArrayList<Object>();
    @SerializedName("wallet")
    @Expose
    private Wallet wallet;
    @SerializedName("rules_log")
    @Expose
    private List<Object> rulesLog = new ArrayList<>();
    @SerializedName("rules_limits")
    @Expose
    private RulesLimits rulesLimits;
    @SerializedName("uploads")
    @Expose
    private Uploads uploads;
    @SerializedName("__v")
    @Expose
    private Integer v;

    /**
     * @return The surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname The surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return The otherNames
     */
    public String getOtherNames() {
        return otherNames;
    }

    /**
     * @param otherNames The other_names
     */
    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    /**
     * @return The emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress The email_address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return The personalPhoneNumber
     */
    public String getPersonalPhoneNumber() {
        return personalPhoneNumber;
    }

    /**
     * @param personalPhoneNumber The personal_phone_number
     */
    public void setPersonalPhoneNumber(String personalPhoneNumber) {
        this.personalPhoneNumber = personalPhoneNumber;
    }

    /**
     * @return The alternatePhoneNumber
     */
    public String getAlternatePhoneNumber() {
        return alternatePhoneNumber;
    }

    /**
     * @param alternatePhoneNumber The alternate_phone_number
     */
    public void setAlternatePhoneNumber(String alternatePhoneNumber) {
        this.alternatePhoneNumber = alternatePhoneNumber;
    }

    /**
     * @return The agentType
     */
    public String getAgentType() {
        return agentType;
    }

    /**
     * @param agentType The agent_type
     */
    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    /**
     * @return The agentClass
     */
    public String getAgentClass() {
        return agentClass;
    }

    /**
     * @param agentClass The agent_class
     */
    public void setAgentClass(String agentClass) {
        this.agentClass = agentClass;
    }

    /**
     * @return The referral
     */
    public String getReferral() {
        return referral;
    }

    /**
     * @param referral The referral
     */
    public void setReferral(String referral) {
        this.referral = referral;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The customers
     */
    public List<Object> getCustomers() {
        return customers;
    }

    /**
     * @param customers The customers
     */
    public void setCustomers(List<Object> customers) {
        this.customers = customers;
    }

    /**
     * @return The transactions
     */
    public List<Object> getTransactions() {
        return transactions;
    }

    /**
     * @param transactions The transactions
     */
    public void setTransactions(List<Object> transactions) {
        this.transactions = transactions;
    }

    /**
     * @return The changedPassword
     */
    public Boolean getChangedPassword() {
        return changedPassword;
    }

    /**
     * @param changedPassword The changed_password
     */
    public void setChangedPassword(Boolean changedPassword) {
        this.changedPassword = changedPassword;
    }

    /**
     * @return The approvalLog
     */
    public List<Object> getApprovalLog() {
        return approvalLog;
    }

    /**
     * @param approvalLog The approval_log
     */
    public void setApprovalLog(List<Object> approvalLog) {
        this.approvalLog = approvalLog;
    }

    /**
     * @return The approved
     */
    public Boolean getApproved() {
        return approved;
    }

    /**
     * @param approved The approved
     */
    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    /**
     * @return The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return The source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return The apiAccessLog
     */
    public List<Object> getApiAccessLog() {
        return apiAccessLog;
    }

    /**
     * @param apiAccessLog The api_access_log
     */
    public void setApiAccessLog(List<Object> apiAccessLog) {
        this.apiAccessLog = apiAccessLog;
    }

    /**
     * @return The settlementLog
     */
    public List<Object> getSettlementLog() {
        return settlementLog;
    }

    /**
     * @param settlementLog The settlement_log
     */
    public void setSettlementLog(List<Object> settlementLog) {
        this.settlementLog = settlementLog;
    }

    /**
     * @return The topups
     */
    public List<Object> getTopups() {
        return topups;
    }

    /**
     * @param topups The topups
     */
    public void setTopups(List<Object> topups) {
        this.topups = topups;
    }

    /**
     * @return The wallet
     */
    public Wallet getWallet() {
        return wallet;
    }

    /**
     * @param wallet The wallet
     */
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    /**
     * @return The rulesLog
     */
    public List<Object> getRulesLog() {
        return rulesLog;
    }

    /**
     * @param rulesLog The rules_log
     */
    public void setRulesLog(List<Object> rulesLog) {
        this.rulesLog = rulesLog;
    }

    /**
     * @return The rulesLimits
     */
    public RulesLimits getRulesLimits() {
        return rulesLimits;
    }

    /**
     * @param rulesLimits The rules_limits
     */
    public void setRulesLimits(RulesLimits rulesLimits) {
        this.rulesLimits = rulesLimits;
    }

    /**
     * @return The uploads
     */
    public Uploads getUploads() {
        return uploads;
    }

    /**
     * @param uploads The uploads
     */
    public void setUploads(Uploads uploads) {
        this.uploads = uploads;
    }

    /**
     * @return The v
     */
    public Integer getV() {
        return v;
    }

    /**
     * @param v The __v
     */
    public void setV(Integer v) {
        this.v = v;
    }
}
