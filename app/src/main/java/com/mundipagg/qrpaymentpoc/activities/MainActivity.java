package com.mundipagg.qrpaymentpoc.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.mundipagg.qrpaymentpoc.R;
import com.mundipagg.qrpaymentpoc.activities.view.MainView;
import com.mundipagg.qrpaymentpoc.model.MessageDialog;
import com.mundipagg.qrpaymentpoc.presenter.QrPresenter;
import com.mundipagg.qrpaymentpoc.presenter.QrPresenterImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    private QRCodeReaderView mydecoderview;
    public static final int READ_QRCODE_REQUEST = 0x1;

    @Bind(R.id.result_text)
    TextView textView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private QrPresenter qrPresenter;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        qrPresenter = new QrPresenterImpl(this);
        builder = new AlertDialog.Builder(this);
        mDialog = new ProgressDialog(this);

        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.fab)
    public void fabButtonCliked(){
        qrPresenter.floatButtonClicked();
    }

    @Override
    public void openQrReader() {
        Intent intent = new Intent(this, QrCodeReader.class);
        startActivityForResult(intent, MainActivity.READ_QRCODE_REQUEST);
    }

    @Override
    public void toogleLoadIndicator() {
        if(!mDialog.isShowing()) {
            mDialog.setMessage("Carregando...");
            mDialog.setCancelable(false);
            mDialog.show();
        }else{
            mDialog.dismiss();
        }
    }

    @Override
    public void changeTextValue(String text) {
        textView.setText(text);
    }

    @Override
    public void displayMessageDialog(MessageDialog messageDialog) {
        builder.setMessage(messageDialog.getMessage()).setPositiveButton(R.string.dialog_confirm_btn, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }



    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == READ_QRCODE_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(QrCodeReader.READ_QRCODE_RESPONSE)) {
                    String code = data.getStringExtra(QrCodeReader.READ_QRCODE_RESPONSE);
                    changeTextValue(code);

                    qrPresenter.initiatePaymentCycle(code);
                }
            }
        }
    }

}
