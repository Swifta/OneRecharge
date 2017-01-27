package com.swifta.onerecharge.customer.customerregistration.registerresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moyinoluwa on 11/4/16.
 */

public class Data {
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("customer_fullname")
    @Expose
    private String customerFullname;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("customer_telephone")
    @Expose
    private String customerTelephone;
    @SerializedName("customer_password")
    @Expose
    private String customerPassword;
    @SerializedName("my_country")
    @Expose
    private String myCountry;
    @SerializedName("linked_agent")
    @Expose
    private String linkedAgent;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("transactions")
    @Expose
    private List<Object> transactions = new ArrayList<Object>();
    @SerializedName("topups")
    @Expose
    private List<Object> topups = new ArrayList<Object>();
    @SerializedName("wallet")
    @Expose
    private Wallet wallet;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("keys")
    @Expose
    private List<Object> keys = new ArrayList<Object>();
    @SerializedName("contact")
    @Expose
    private List<Object> contact = new ArrayList<Object>();
    @SerializedName("reg_source")
    @Expose
    private String regSource;
    @SerializedName("customer_status")
    @Expose
    private Boolean customerStatus;
    @SerializedName("customer_type")
    @Expose
    private Integer customerType;
    @SerializedName("customer_created")
    @Expose
    private String customerCreated;

    /**
     *
     * @return
     * The v
     */
    public Integer getV() {
        return v;
    }

    /**
     *
     * @param v
     * The __v
     */
    public void setV(Integer v) {
        this.v = v;
    }

    /**
     *
     * @return
     * The customerFullname
     */
    public String getCustomerFullname() {
        return customerFullname;
    }

    /**
     *
     * @param customerFullname
     * The customer_fullname
     */
    public void setCustomerFullname(String customerFullname) {
        this.customerFullname = customerFullname;
    }

    /**
     *
     * @return
     * The customerEmail
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     *
     * @param customerEmail
     * The customer_email
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     *
     * @return
     * The customerTelephone
     */
    public String getCustomerTelephone() {
        return customerTelephone;
    }

    /**
     *
     * @param customerTelephone
     * The customer_telephone
     */
    public void setCustomerTelephone(String customerTelephone) {
        this.customerTelephone = customerTelephone;
    }

    /**
     *
     * @return
     * The customerPassword
     */
    public String getCustomerPassword() {
        return customerPassword;
    }

    /**
     *
     * @param customerPassword
     * The customer_password
     */
    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    /**
     *
     * @return
     * The myCountry
     */
    public String getMyCountry() {
        return myCountry;
    }

    /**
     *
     * @param myCountry
     * The my_country
     */
    public void setMyCountry(String myCountry) {
        this.myCountry = myCountry;
    }

    /**
     *
     * @return
     * The linkedAgent
     */
    public String getLinkedAgent() {
        return linkedAgent;
    }

    /**
     *
     * @param linkedAgent
     * The linked_agent
     */
    public void setLinkedAgent(String linkedAgent) {
        this.linkedAgent = linkedAgent;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The transactions
     */
    public List<Object> getTransactions() {
        return transactions;
    }

    /**
     *
     * @param transactions
     * The transactions
     */
    public void setTransactions(List<Object> transactions) {
        this.transactions = transactions;
    }

    /**
     *
     * @return
     * The topups
     */
    public List<Object> getTopups() {
        return topups;
    }

    /**
     *
     * @param topups
     * The topups
     */
    public void setTopups(List<Object> topups) {
        this.topups = topups;
    }

    /**
     *
     * @return
     * The wallet
     */
    public Wallet getWallet() {
        return wallet;
    }

    /**
     *
     * @param wallet
     * The wallet
     */
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    /**
     *
     * @return
     * The active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     *
     * @param active
     * The active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     *
     * @return
     * The keys
     */
    public List<Object> getKeys() {
        return keys;
    }

    /**
     *
     * @param keys
     * The keys
     */
    public void setKeys(List<Object> keys) {
        this.keys = keys;
    }

    /**
     *
     * @return
     * The contact
     */
    public List<Object> getContact() {
        return contact;
    }

    /**
     *
     * @param contact
     * The contact
     */
    public void setContact(List<Object> contact) {
        this.contact = contact;
    }

    /**
     *
     * @return
     * The regSource
     */
    public String getRegSource() {
        return regSource;
    }

    /**
     *
     * @param regSource
     * The reg_source
     */
    public void setRegSource(String regSource) {
        this.regSource = regSource;
    }

    /**
     *
     * @return
     * The customerStatus
     */
    public Boolean getCustomerStatus() {
        return customerStatus;
    }

    /**
     *
     * @param customerStatus
     * The customer_status
     */
    public void setCustomerStatus(Boolean customerStatus) {
        this.customerStatus = customerStatus;
    }

    /**
     *
     * @return
     * The customerType
     */
    public Integer getCustomerType() {
        return customerType;
    }

    /**
     *
     * @param customerType
     * The customer_type
     */
    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    /**
     *
     * @return
     * The customerCreated
     */
    public String getCustomerCreated() {
        return customerCreated;
    }

    /**
     *
     * @param customerCreated
     * The customer_created
     */
    public void setCustomerCreated(String customerCreated) {
        this.customerCreated = customerCreated;
    }

}
