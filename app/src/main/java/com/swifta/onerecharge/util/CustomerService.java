package com.swifta.onerecharge.util;

import com.swifta.onerecharge.customer.customerlogout.CustomerLogout;
import com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargerequestmodel.CustomerQuickRechargeRequest;
import com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargeresponsemodel.CustomerQuickRechargeResponse;
import com.swifta.onerecharge.customer.customerregistration.loginresponsemodel.CustomerRegistration;
import com.swifta.onerecharge.customer.customerregistration.registerresponsemodel.CustomerSignUpResponse;
import com.swifta.onerecharge.customer.customerwalletbalance.balanceresponsemodel.CustomerWalletBalanceResponse;
import com.swifta.onerecharge.customer.customerwalletbalance.balancerequestmodel.CustomerWalletBalanceRequest;
import com.swifta.onerecharge.customer.customerwallettopup.customerwallettopuprequestmodel.CustomerWalletTopUpRequest;
import com.swifta.onerecharge.customer.customerwallettopup.customerwallettopupresponsemodel.CustomerWalletTopUpResponse;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by moyinoluwa on 11/4/16.
 */

public interface CustomerService {

    @FormUrlEncoded
    @POST(Url.CUSTOMER_LOGIN_URL)
    Observable<CustomerRegistration> logCustomerIn(@Header("Authorization") String authorization, @Header("Key")
            String key, @Field("customer_email") String customerEmail, @Field
                                                           ("customer_password") String customerPassword);

    @FormUrlEncoded
    @POST(Url.CUSTOMER_SIGNUP_URL)
    Observable<CustomerSignUpResponse> signCustomerUp(@Header("Authorization") String authorization, @Header("Key") String key, @Field("customer_fullname") String customerFullname, @Field("customer_email") String customerEmail, @Field("customer_telephone") String customerTelephone, @Field("customer_password") String customerPassword);

    @POST(Url.CUSTOMER_WALLET_TOPUP_URL)
    Observable<CustomerWalletTopUpResponse> topWalletUp(@Body CustomerWalletTopUpRequest
                                                                walletTopUpRequest);

    @GET(Url.CUSTOMER_LOGOUT_URL)
    Observable<CustomerLogout> logCustomerOut(@Header("Authorization") String authorization,
                                              @Header("Key") String key, @Path(Url.EMAIL_PATH)
                                                      String email, @Path(Url.TOKEN_PATH) String token);

    @POST(Url.CUSTOMER_WALLET_QUICK_RECHARGE)
    Observable<CustomerQuickRechargeResponse> performCustomerQuickRechargeFromWallet
            (@Header("Authorization") String authorization, @Header("Key") String key, @Body
                    CustomerQuickRechargeRequest walletQuickRechargeRequest);

    @POST(Url.CUSTOMER_WALLET_BALANCE_URL)
    Observable<CustomerWalletBalanceResponse> getWalletBallance(@Body CustomerWalletBalanceRequest
                                                                walletBalanceRequest);

}
