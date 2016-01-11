package com.mundipagg.qrpaymentpoc.activities.view;

import android.content.Context;
import android.os.Message;

import com.mundipagg.qrpaymentpoc.model.MessageDialog;

/**
 * Created by eduardovianna on 08/01/16.
 */
public interface MainView {

    public void openQrReader();
    public void toogleLoadIndicator();
    public void changeTextValue(String text);
    public void displayMessageDialog(MessageDialog messageDialog);
    public Context getContext();
}
