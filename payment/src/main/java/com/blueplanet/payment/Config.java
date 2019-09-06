package com.blueplanet.payment;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

class Config implements Serializable {
    private String merchantId;
    private String serviceName;
    private String password;
    private String email;
    private String title;
    private int titleColor;
    private int errorTextColor;
    private int errorBackgroundColor;
    private Drawable navigationIconDrawable;
    private int actionBarColor;
    private Drawable actionBarBackgroundDrawable;
    private boolean isActionBarShown;

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

    public String getTitle() {
        return title;
    }

    public Config setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public Config setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public int getActionBarColor() {
        return actionBarColor;
    }

    public Config setActionBarColor(int actionBarColor) {
        this.actionBarColor = actionBarColor;
        return this;
    }

    public boolean isActionBarShown() {
        return isActionBarShown;
    }

    public Config setActionBarShown(boolean actionBarShown) {
        isActionBarShown = actionBarShown;
        return this;
    }

    public int getErrorTextColor() {
        return errorTextColor;
    }

    public Config setErrorTextColor(int errorTextColor) {
        this.errorTextColor = errorTextColor;
        return this;
    }

    public int getErrorBackgroundColor() {
        return errorBackgroundColor;
    }

    public Config setErrorBackgroundColor(int errorBackgroundColor) {
        this.errorBackgroundColor = errorBackgroundColor;
        return this;
    }

    public Drawable getNavigationIconDrawable() {
        return navigationIconDrawable;
    }

    public Config setNavigationIconDrawable(Drawable navigationIconDrawable) {
        this.navigationIconDrawable = navigationIconDrawable;
        return this;
    }

    public Drawable getActionBarBackgroundDrawable() {
        return actionBarBackgroundDrawable;
    }

    public Config setActionBarBackgroundDrawable(Drawable actionBarBackgroundDrawable) {
        this.actionBarBackgroundDrawable = actionBarBackgroundDrawable;
        return this;
    }
}
