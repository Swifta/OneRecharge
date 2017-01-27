package com.swifta.onerecharge.customer.customerregistration.loginresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 11/4/16.
 */

public class Profile {

    @SerializedName("_id")
    @Expose
    private String id;
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
    private Object customerPassword;
    @SerializedName("linked_agent")
    @Expose
    private LinkedAgent linkedAgent;
    @SerializedName("wallet")
    @Expose
    private Wallet wallet;
    @SerializedName("customer_status")
    @Expose
    private Boolean customerStatus;
    @SerializedName("customer_created")
    @Expose
    private String customerCreated;

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
     * @return The customerFullname
     */
    public String getCustomerFullname() {
        return customerFullname;
    }

    /**
     * @param customerFullname The customer_fullname
     */
    public void setCustomerFullname(String customerFullname) {
        this.customerFullname = customerFullname;
    }

    /**
     * @return The customerEmail
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * @param customerEmail The customer_email
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * @return The customerTelephone
     */
    public String getCustomerTelephone() {
        return customerTelephone;
    }

    /**
     * @param customerTelephone The customer_telephone
     */
    public void setCustomerTelephone(String customerTelephone) {
        this.customerTelephone = customerTelephone;
    }

    /**
     * @return The customerPassword
     */
    public Object getCustomerPassword() {
        return customerPassword;
    }

    /**
     * @param customerPassword The customer_password
     */
    public void setCustomerPassword(Object customerPassword) {
        this.customerPassword = customerPassword;
    }

    /**
     * @return The linkedAgent
     */
    public LinkedAgent getLinkedAgent() {
        return linkedAgent;
    }

    /**
     * @param linkedAgent The linked_agent
     */
    public void setLinkedAgent(LinkedAgent linkedAgent) {
        this.linkedAgent = linkedAgent;
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
     * @return The customerStatus
     */
    public Boolean getCustomerStatus() {
        return customerStatus;
    }

    /**
     * @param customerStatus The customer_status
     */
    public void setCustomerStatus(Boolean customerStatus) {
        this.customerStatus = customerStatus;
    }

    /**
     * @return The customerCreated
     */
    public String getCustomerCreated() {
        return customerCreated;
    }

    /**
     * @param customerCreated The customer_created
     */
    public void setCustomerCreated(String customerCreated) {
        this.customerCreated = customerCreated;
    }

}
