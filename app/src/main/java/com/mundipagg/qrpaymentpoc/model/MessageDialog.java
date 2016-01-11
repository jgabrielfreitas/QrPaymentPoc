package com.mundipagg.qrpaymentpoc.model;

import java.io.Serializable;

/**
 * Created by eduardovianna on 08/01/16.
 */
public class MessageDialog implements Serializable{

    private String message;

    public MessageDialog() {
    }

    public MessageDialog(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
