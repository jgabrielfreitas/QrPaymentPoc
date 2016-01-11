package com.mundipagg.qrpaymentpoc.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.mundipagg.qrpaymentpoc.BuildConfig;
import com.mundipagg.qrpaymentpoc.R;
import com.mundipagg.qrpaymentpoc.model.MessageDialog;
import com.mundipagg.qrpaymentpoc.model.Payment;
import com.mundipagg.qrpaymentpoc.model.PaymentResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by eduardovianna on 08/01/16.
 */
public class HttpRepository {

    Retrofit retrofit;
    Context mContext;

    public Subject<Object, Object> subject = new SerializedSubject<>(PublishSubject.create());

    private RequestReponser requestReponser;


    public static final int NETWORK_NOT_AVAIABLE = -1;
    public static final int SERVER_NOT_AVAIABLE = 0;

    public HttpRepository(Context context) {

        this.mContext = context;
        retrofit = getAdapter(BuildConfig.SERVICE_URL);
    }

    public Retrofit getAdapter(String endpoint) {

        if (endpoint != null && !endpoint.trim().equals("")) {

            OkHttpClient httpClient = new OkHttpClient();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.interceptors().add(interceptor);

            retrofit = new Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(endpoint)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit;
        } else {
            return null;
        }
    }

    public void makPayment(Payment payment) {

        if (isOnline()) {
            PaymentService paymentService = retrofit.create(PaymentService.class);

            Call<PaymentResponse> call = paymentService.makePayment(payment);
            call.enqueue(new Callback<PaymentResponse>() {

                @Override
                public void onResponse(Response<PaymentResponse> response, Retrofit retrofit) {


                    if (response.isSuccess()) {

//                                if (billsModel.getBills().size() == 0) {
//                                    EventBus.getDefault().post(mContext.getString(R.string.error_message_empty_list));
//                                } else {
//
//                                    EventBus.getDefault().post(billsModel);
//                                }

                        subject.onNext(new PaymentResponse());
                    } else {
                        sendErrorMessageForStatusCode(response.code());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ResponseAPI:", "ERROR IN REQUEST");
                    t.printStackTrace();

                    if (t.getMessage() != null)
                        Log.e("ResponseAPI:", t.getMessage());
                }
            });

        } else {
            sendErrorMessageForStatusCode(NETWORK_NOT_AVAIABLE);
        }

    }

    private void sendErrorMessageForStatusCode(int status) {
        if (subject.hasObservers())
            subject.onNext(getErrorMessageForStatus(status));
    }

    public MessageDialog getErrorMessageForStatus(int status) {
        String errorMessage = null;

        if (status >= 400 && status <= 499) {
            errorMessage = mContext.getString(R.string.error_message_400);
        } else if (status >= 500 && status <= 599) {
            errorMessage = mContext.getString(R.string.error_message_generic);
        } else if (status == NETWORK_NOT_AVAIABLE) {
            errorMessage = mContext.getString(R.string.error_message_network_unavaiable);
        } else if (status == SERVER_NOT_AVAIABLE) {
            errorMessage = mContext.getString(R.string.error_message_generic);
        } else {
            errorMessage = mContext.getString(R.string.error_message_generic);
        }

        return new MessageDialog(errorMessage);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
