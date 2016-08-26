package com.swifta.onerecharge.resetagentpassword;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moyinoluwa on 8/26/16.
 */
public class Data {

    private Integer code;
    private String message;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}