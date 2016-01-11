package com.mundipagg.qrpaymentpoc.presenter;

import com.mundipagg.qrpaymentpoc.activities.view.MainView;
import com.mundipagg.qrpaymentpoc.model.MessageDialog;
import com.mundipagg.qrpaymentpoc.model.Payment;
import com.mundipagg.qrpaymentpoc.model.PaymentResponse;
import com.mundipagg.qrpaymentpoc.service.HttpRepository;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by eduardovianna on 08/01/16.
 */
public class QrPresenterImpl implements QrPresenter {

    private HttpRepository httpRepository;
    private MainView mainView;

    private CompositeSubscription subscriptions;

    public QrPresenterImpl(MainView mainView) {
        this.httpRepository = new HttpRepository(mainView.getContext());
        this.mainView = mainView;
        subscriptions = new CompositeSubscription();

        subscriptions.add(httpRepository.subject.subscribe(new Action1<Object>() {
            @Override
            public void call(Object event) {

                QrPresenterImpl.this.mainView.toogleLoadIndicator();

                if (event instanceof PaymentResponse) {
                    QrPresenterImpl.this.mainView.displayMessageDialog(new MessageDialog("Pagamento efetuado com sucesso!"));

                } else if (event instanceof MessageDialog) {

                    //display message dialog
                    QrPresenterImpl.this.mainView.displayMessageDialog((MessageDialog) event);
                }
            }
        }));
    }

    @Override
    public void floatButtonClicked() {
        mainView.openQrReader();
    }

    @Override
    public void initiatePaymentCycle(String code) {

        mainView.toogleLoadIndicator();
        Payment payment = new Payment();
        payment.setToken(code);
        payment.setBuyerKey("1FBA14D4-842E-46CA-86B7-259F094B2561");
        payment.setCreditCardKey("D866101B-80E0-4D4F-A1E0-7F6EC351615C");
        payment.setInstallmentCount(1);

        httpRepository.makPayment(payment);
    }

}
