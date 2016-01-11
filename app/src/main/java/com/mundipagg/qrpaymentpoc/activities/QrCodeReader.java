package com.mundipagg.qrpaymentpoc.activities;


import butterknife.Bind;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.mundipagg.qrpaymentpoc.R;

import butterknife.ButterKnife;

/**
 * Created by eduardovianna on 07/01/16.
 */
public class QrCodeReader extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    @Bind(R.id.qrdecoderview)
    QRCodeReaderView mydecoderview;

    static final String READ_QRCODE_RESPONSE = "READ_QRCODE_RESPONSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_view);
        ButterKnife.bind(this);

        mydecoderview.setOnQRCodeReadListener(this);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        Intent result = new Intent();
        result.putExtra(QrCodeReader.READ_QRCODE_RESPONSE, text);

        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }

}
