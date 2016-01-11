package com.mundipagg.qrpaymentpoc.model;

import java.io.Serializable;

/**
 * Created by eduardovianna on 08/01/16.
 */
public class Payment implements Serializable {

    private String token;
    private String buyerKey;
    private String creditCardKey;
    private int installmentCount;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBuyerKey() {
        return buyerKey;
    }

    public void setBuyerKey(String buyerKey) {
        this.buyerKey = buyerKey;
    }

    public String getCreditCardKey() {
        return creditCardKey;
    }

    public void setCreditCardKey(String creditCardKey) {
        this.creditCardKey = creditCardKey;
    }

    public int getInstallmentCount() {
        return installmentCount;
    }

    public void setInstallmentCount(int installmentCount) {
        this.installmentCount = installmentCount;
    }
}

