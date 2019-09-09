package com.blueplanet.payment;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

class Config implements Serializable {
    private String merchantId;
    private String serviceName;
    private String password;
    private String email;


    public String getMerchantId() {
        return merchantId;
    }

    public Config setMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Config setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Config setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Config setEmail(String email) {
        this.email = email;
        return this;
    }


}
