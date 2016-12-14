package com.swifta.onerecharge.resetagentpassword;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moyinoluwa on 8/26/16.
 */
public class RequestPasswordData {

    @SerializedName("user_id")
    String userId;

    @SerializedName("token")
    String token;

    @SerializedName("old_password")
    String oldPassword;

    @SerializedName("new_password")
    String newPassword;


    public RequestPasswordData(String userId, String token, String
            oldPassword, String newPassword) {
        this.userId = userId;
        this.token = token;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}