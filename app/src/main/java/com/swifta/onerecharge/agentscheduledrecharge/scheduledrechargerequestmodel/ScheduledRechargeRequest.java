package com.swifta.onerecharge.agentscheduledrecharge.scheduledrechargerequestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moyinoluwa on 9/20/16.
 */
public class ScheduledRechargeRequest {

    @SerializedName("recipient")
    @Expose
    public String recipient;
    @SerializedName("network")
    @Expose
    public String network;
    @SerializedName("total")
    @Expose
    public Integer total;
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("schedule")
    @Expose
    public List<Schedule> schedule = new ArrayList<>();

    public ScheduledRechargeRequest(String recipient, String network, Integer
            total, String customerId, List<Schedule> schedule) {

        this.recipient = recipient;
        this.network = network;
        this.total = total;
        this.customerId = customerId;
        this.schedule = schedule;
    }
}
