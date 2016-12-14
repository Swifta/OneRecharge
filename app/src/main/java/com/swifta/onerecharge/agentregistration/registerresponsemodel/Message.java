package com.swifta.onerecharge.agentregistration.registerresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 9/28/16.
 */

public class Message {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("index")
    @Expose
    private Integer index;
    @SerializedName("errmsg")
    @Expose
    private String errmsg;
    @SerializedName("op")
    @Expose
    private Op op;

    /**
     *
     * @return
     * The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The index
     */
    public Integer getIndex() {
        return index;
    }

    /**
     *
     * @param index
     * The index
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     *
     * @return
     * The errmsg
     */
    public String getErrmsg() {
        return errmsg;
    }

    /**
     *
     * @param errmsg
     * The errmsg
     */
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    /**
     *
     * @return
     * The op
     */
    public Op getOp() {
        return op;
    }

    /**
     *
     * @param op
     * The op
     */
    public void setOp(Op op) {
        this.op = op;
    }


}
