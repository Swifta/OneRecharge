package com.swifta.onerecharge.util;

import com.swifta.onerecharge.agent.agentapikey.AgentApiKeyResponse;
import com.swifta.onerecharge.agent.agentdashboard.AgentSummary;
import com.swifta.onerecharge.agent.agentlogout.AgentLogout;
import com.swifta.onerecharge.agent.agentquickrecharge.quickrechargerequestmodel.QuickRechargeRequest;
import com.swifta.onerecharge.agent.agentquickrecharge.quickrechargeresponsemodel.QuickRechargeResponse;
import com.swifta.onerecharge.agent.agentquicktransactionhistory.AgentQuickTransactionHistory;
import com.swifta.onerecharge.agent.agentregistration.loginresponsemodel.AgentRegistration;
import com.swifta.onerecharge.agent.agentregistration.registerrequestmodel.AgentSignUpBody;
import com.swifta.onerecharge.agent.agentregistration.registerresponsemodel.AgentSignUpResponse;
import com.swifta.onerecharge.agent.agentscheduledrecharge.scheduledrechargerequestmodel.ScheduledRechargeRequest;
import com.swifta.onerecharge.agent.agentscheduledrecharge.scheduledrechargeresponsemodel.ScheduledRechargeResponse;
import com.swifta.onerecharge.agent.agentscheduledtransactionhistory.AgentScheduledTransactionHistory;
import com.swifta.onerecharge.networklist.NetworkListResponse;
import com.swifta.onerecharge.agent.resetagentpassword.AgentPassword;
import com.swifta.onerecharge.agent.resetagentpassword.RequestPasswordData;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by moyinoluwa on 8/22/16.
 */
public interface AgentService {
    @GET(Url.AGENT_LOGIN_URL)
    Observable<AgentRegistration> logAgentIn(@Path(Url.EMAIL_PATH) String email,
                                             @Path(Url.PASSWORD_PATH) String password);

    @POST(Url.AGENT_SIGNUP_URL)
    Observable<AgentSignUpResponse> registerNewAgent(@Body AgentSignUpBody signUpBody);

    @PUT(Url.AGENT_CHANGE_PASSWORD_URL)
    Observable<AgentPassword> changePassword(@Body RequestPasswordData passwordData);

    @GET(Url.AGENT_SUMMARY_URL)
    Observable<AgentSummary> getSummary(@Path(Url.EMAIL_PATH) String email, @Path(Url.TOKEN_PATH)
            String token);

    @GET(Url.AGENT_NETWORKS)
    Observable<NetworkListResponse> getNetworkList();

    @GET(Url.AGENT_API_KEY)
    Observable<AgentApiKeyResponse> getAgentApiKey(@Path(Url.EMAIL_PATH) String email, @Path(Url
            .TOKEN_PATH) String token);

    @POST(Url.AGENT_QUICK_RECHARGE)
    Observable<QuickRechargeResponse> performAgentQuickRecharge(@Header("Authorization") String
                                                                        authorization, @Header("Key") String key, @Body QuickRechargeRequest quickRechargeRequest);

    @POST(Url.AGENT_SCHEDULED_RECHARGE)
    Observable<ScheduledRechargeResponse> performAgentScheduledRecharge(@Header("Authorization") String authorization, @Header("Key") String key, @Body ScheduledRechargeRequest scheduledRechargeRequest);

    @GET(Url.AGENT_QUICK_TRANSACTION_HISTORY)
    Observable<AgentQuickTransactionHistory> getQuickTransactionHistory(@Path(Url.NETWORK) String network, @Path(Url.RECIPIENT) String recipient, @Path(Url.GATEWAY) String gateway, @Path(Url
            .STATUS) String status, @Path(Url.DATE_FROM) String date_from, @Path(Url.DATE_TO)
                                                                                String date_to,
                                                                        @Path(Url.AGENT_EMAIL)
                                                                                String email,
                                                                        @Query(Url.CURRENT) int
                                                                                current, @Query
                                                                                (Url.ROW_COUNT) int
                                                                                rowCount, @Query
                                                                                (Url.SEARCH_PHRASE)
                                                                                String searchPhrase);

    @GET(Url.AGENT_SCHEDULED_TRANSACTION_HISTORY)
    Observable<AgentScheduledTransactionHistory> getScheduledTransactionHistory(@Path(Url.NETWORK) String network, @Path(Url.RECIPIENT) String recipient, @Path(Url.GATEWAY) String gateway, @Path(Url
            .STATUS) String status, @Path(Url.DATE_FROM) String date_from, @Path(Url.DATE_TO)
                                                                                        String date_to,
                                                                                @Path(Url.AGENT_EMAIL)
                                                                                        String email,
                                                                                @Query(Url.CURRENT) int
                                                                                        current, @Query
                                                                                        (Url.ROW_COUNT) int
                                                                                        rowCount, @Query
                                                                                        (Url.SEARCH_PHRASE)
                                                                                        String searchPhrase);

    @GET(Url.AGENT_LOGOUT_URL)
    Observable<AgentLogout> logAgentOut(@Path(Url.EMAIL_PATH) String email, @Path(Url.TOKEN_PATH)
            String token);
}
