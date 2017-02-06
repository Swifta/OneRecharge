package com.swifta.onerecharge.util;

import com.swifta.onerecharge.cardpayment.card.requestmodel.PaymentRequest;
import com.swifta.onerecharge.cardpayment.card.responsemodel.PaymentResponse;
import com.swifta.onerecharge.cardpayment.otp.requestmodel.OtpRequest;
import com.swifta.onerecharge.cardpayment.otp.responsemodel.OtpResponse;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by moyinoluwa on 2/6/17.
 */

public interface MfisaService {

    @POST(Url.MFISA_PAY_WITH_CARD)
    Observable<PaymentResponse> performCardTransaction(@Header("Authorization") String
                                                               authorization, @Body
                                                               PaymentRequest paymentRequest);

    @POST(Url.MFISA_AUTHORIZE_WITH_OTP)
    Observable<OtpResponse> authorizeWithOtp(@Header("Authorization") String authorization,
                                             @Body OtpRequest otpRequest);
}
