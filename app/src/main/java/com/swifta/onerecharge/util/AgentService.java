package com.swifta.onerecharge.util;

import com.swifta.onerecharge.agentregistration.AgentRegistration;
import com.swifta.onerecharge.resetagentpassword.AgentPassword;
import com.swifta.onerecharge.resetagentpassword.RequestPasswordData;
import com.swifta.onerecharge.util.Url;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by moyinoluwa on 8/22/16.
 */
public interface AgentService {
    @GET(Url.AGENT_LOGIN_URL)
    Observable<AgentRegistration> logAgentIn(@Path(Url.EMAIL_PATH) String email,
                                             @Path(Url.PASSWORD_PATH) String password);

    @POST(Url.AGENT_CHANGE_PASSWORD_URL)
    Observable<AgentPassword> changePassword(@Body RequestPasswordData
                                                     passwordData);
}
