package com.blueplanet.payment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class BPpay {

    //Mark: BPpay start
    public static final class Builder {
        private final Activity mContext;
        private String merchantId;
        private String serviceName;
        private String password;
        private String email;
        private String title = "Payment";
        private int titleColor = Color.WHITE;
        private int actionBarColor;
        private boolean isActionBarShown = true;
        private int errorTextColor;
        private int errorBackgroundColor;
        private Drawable navigationIconDrawable;
        private Drawable actionBarBackgroundDrawable;

        public Builder(Activity context) {
            mContext = context;
        }

        public Builder setMerchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public Builder setServiceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitleColor(int resId) {
            this.titleColor = getColor(resId);
            return this;
        }

        public Builder setActionBarColor(int resId) {
            this.actionBarColor = getColor(resId);
            return this;
        }

        public Builder setActionBarShown(boolean actionBarShown) {
            isActionBarShown = actionBarShown;
            return this;
        }

        public Builder setErrorTextColor(int resId) {
            this.errorTextColor = getColor(resId);
            return this;
        }

        public Builder setErrorBackgroundColor(int resId) {
            this.errorBackgroundColor = getColor(resId);
            return this;
        }

        public Builder setNavigationIconDrawable(int resId) {
            this.navigationIconDrawable = getDrawable(resId);
            return this;
        }

        public Builder setActionBarBackgroundDrawable(int resId) {
            this.actionBarBackgroundDrawable = getDrawable(resId);
            return this;
        }

        public Builder setNavigationIconDrawable(Drawable drawable) {
            this.navigationIconDrawable = drawable;
            return this;
        }

        public Builder setActionBarBackgroundDrawable(Drawable drawable) {
            this.actionBarBackgroundDrawable = drawable;
            return this;
        }

        public BPpay build() {
            BPpay bPpay = new BPpay(this.mContext);
            if (actionBarColor == 0) {
                actionBarColor = getColor(R.color.colorPrimary);
            }
            Config config = new Config()
                    .setEmail(this.email)
                    .setMerchantId(this.merchantId)
                    .setServiceName(this.serviceName)
                    .setPassword(this.password)
                    .setEmail(this.email)
                    .setTitle(this.title)
                    .setTitleColor(this.titleColor)
                    .setActionBarColor(this.actionBarColor)
                    .setActionBarShown(this.isActionBarShown)
                    .setErrorTextColor(this.errorTextColor)
                    .setErrorBackgroundColor(this.errorBackgroundColor)
                    .setNavigationIconDrawable(this.navigationIconDrawable)
                    .setActionBarBackgroundDrawable(this.actionBarBackgroundDrawable);

            bPpay.setConfig(config);

            return bPpay;
        }


        private int getColor(int resId) {
            return mContext.getResources().getColor(resId);
        }

        private Drawable getDrawable(int resId) {
            return mContext.getResources().getDrawable(resId);
        }

    }

    //Mark: BPpay start
    public static final int BPPAY_REQUEST_CODE = 41999;
    public static final String KEY_PRICE = "price";
    public static final String KEY_ORDER_ID = "order_id";

    private Config mConfig;

    private final Activity mContext;

    private BPpay(Activity context) {
        mContext = context;
    }

    public void pay(String orderId, int price) {
        startActivityForResult(orderId, price);
    }

    private void startActivityForResult(String orderId, int price) {
        Intent intent = new Intent(mContext, PaymentActivity.class);
        intent.putExtra("config", mConfig);
        intent.putExtra(KEY_ORDER_ID, orderId);
        intent.putExtra(KEY_PRICE, price);
        mContext.startActivityForResult(intent, BPPAY_REQUEST_CODE);
    }

    private void setConfig(Config config) {
        mConfig = config;
    }
}
