package com.mundipagg.qrpaymentpoc.service;

import com.mundipagg.qrpaymentpoc.model.Payment;
import com.mundipagg.qrpaymentpoc.model.PaymentResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.Call;

/**
 * Created by eduardovianna on 07/01/16.
 */
public interface PaymentService {

    @Headers("Content-Type: application/json")
    @POST("/app-pay")
    Call<PaymentResponse> makePayment(@Body Payment payment);
}
