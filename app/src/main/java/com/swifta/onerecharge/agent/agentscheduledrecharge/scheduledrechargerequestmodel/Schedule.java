package com.swifta.onerecharge.agent.agentscheduledrecharge.scheduledrechargerequestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/20/16.
 */
public class Schedule {
    @SerializedName("amount")
    @Expose
    public Integer amount;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("time")
    @Expose
    public String time;

    public Schedule(Integer amount, String date, String time) {
        this.amount = amount;
        this.date = date;
        this.time = time;
    }
}
