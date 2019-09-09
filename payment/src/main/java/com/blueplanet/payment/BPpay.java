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
        private UIConfig mUIConfig;


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

        public Builder setUIConfig(UIConfig UIConfig) {
            mUIConfig = UIConfig;
            return this;
        }

        public BPpay build() {
            BPpay bPpay = new BPpay(this.mContext);

            Config config = new Config()
                    .setEmail(this.email)
                    .setMerchantId(this.merchantId)
                    .setServiceName(this.serviceName)
                    .setPassword(this.password)
                    .setEmail(this.email);
            bPpay.setConfig(config);

            if (mUIConfig == null) {
                mUIConfig = new UIConfig.Builder(mContext).build();
            }

            bPpay.setUIConfig(mUIConfig);

            return bPpay;
        }


    }

    //Mark: BPpay start
    public static final int BPPAY_REQUEST_CODE = 41999;
    public static final String KEY_PRICE = "price";
    public static final String KEY_ORDER_ID = "order_id";

    private Config mConfig;
    private UIConfig mUIConfig;

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
        intent.putExtra("ui_config", mUIConfig);
        intent.putExtra(KEY_ORDER_ID, orderId);
        intent.putExtra(KEY_PRICE, price);
        mContext.startActivityForResult(intent, BPPAY_REQUEST_CODE);
    }

    private void setConfig(Config config) {
        mConfig = config;
    }

    public UIConfig getUIConfig() {
        return mUIConfig;
    }

    public void setUIConfig(UIConfig UIConfig) {
        mUIConfig = UIConfig;
    }
}
