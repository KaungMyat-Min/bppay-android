package com.blueplanet.bppay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.blueplanet.payment.BPpay;


public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private BPpay mBPpay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBPpay = new BPpay.Builder(this)
                .setEmail("your_email")
                .setMerchantId("your_merchant_id")
                .setServiceName("your_service_name")
                .setPassword("your_password")
                .build();


        mButton = findViewById(R.id.btn_payment);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBPpay.pay("testing_order_bp_lib" + Math.random(), 12000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BPpay.BPPAY_REQUEST_CODE) {
            //todo: check backend server for payment status
        }
    }
}
