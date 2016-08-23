package com.swifta.onerecharge.agentregistration;

import com.swifta.onerecharge.util.Url;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by moyinoluwa on 8/22/16.
 */
public interface AgentRegistrationService {
    @GET(Url.AGENT_LOGIN_URL)
    Observable<AgentRegistration> logAgentIn(@Path(Url.EMAIL_PATH) String email,
                                             @Path(Url.PASSWORD_PATH) String password);
}
