package com.swifta.onerecharge.agent.agentscheduledtransactionhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moyinoluwa on 9/26/16.
 */

public class AgentScheduledTransactionHistory {
    @SerializedName("current")
    @Expose
    private Integer current;
    @SerializedName("rowCount")
    @Expose
    private Integer rowCount;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("rows")
    @Expose
    private List<Row> rows = new ArrayList<>();
    @SerializedName("summary")
    @Expose
    private Summary summary;

    /**
     * @return The current
     */
    public Integer getCurrent() {
        return current;
    }

    /**
     * @param current The current
     */
    public void setCurrent(Integer current) {
        this.current = current;
    }

    /**
     * @return The rowCount
     */
    public Integer getRowCount() {
        return rowCount;
    }

    /**
     * @param rowCount The rowCount
     */
    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * @return The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return The rows
     */
    public List<Row> getRows() {
        return rows;
    }

    /**
     * @param rows The rows
     */
    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    /**
     * @return The summary
     */
    public Summary getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(Summary summary) {
        this.summary = summary;
    }

}
