package com.blueplanet.payment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public final class PaymentActivity extends AppCompatActivity {
    private static final String PAYMENT_URL = "https://bppay.blueplanet.com.mm/payment";

    //------------- view fields start-------------

    private Toolbar mToolbar;
    private TextView mErrorText;
    private View mContainerView;
    private ProgressBar mProgressBar;
    private WebView webView;
    private View errorView;
    private boolean isError;

    //------------- view fields end-------------

    //============= variable start ==============
    private Config mConfig;
    private UIConfig mUIConfig;
    private int price;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bppay_payment_activity);


        price = getIntent().getIntExtra(BPpay.KEY_PRICE, 500);
        orderId = getIntent().getStringExtra(BPpay.KEY_ORDER_ID);
        mConfig = (Config) getIntent().getSerializableExtra("config");
        mUIConfig = (UIConfig) getIntent().getSerializableExtra("ui_config");

        bindViews();
        updateTheme();

        if (mUIConfig.isActionBarShown()) {
            if (mUIConfig.getTitle() != null && !mUIConfig.getTitle().equals("")) {
                mToolbar.setTitle(mUIConfig.getTitle());
            }

            setSupportActionBar(mToolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PaymentActivity.this.finish();
                }
            });
        } else {
            mToolbar.setVisibility(View.GONE);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                setSupportActionBar(mToolbar);
            }
        }

        customizeWebViewSetting(webView);

        WebViewClient webViewClient = getWebViewClient();

        webView.setWebViewClient(webViewClient);

        String formData = getPaymentFormData(orderId, price + "");
        webView.postUrl(PAYMENT_URL, formData.getBytes());

    }

    @Override
    public void onBackPressed() {
        reportToCallerActivity(Activity.RESULT_OK, price, orderId);
        super.onBackPressed();
    }

    private void customizeWebViewSetting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLoadWithOverviewMode(true);//enforce zoom out
            webSettings.setUseWideViewPort(true);//enable mobile view
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
        }

    }

    private WebViewClient getWebViewClient() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("kbzpay://")) {
                    if (isAppInstalled("com.kbzbank.kpaycustomer")) {
                        webView.goBack();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        reportToCallerActivity(Activity.RESULT_OK, price, orderId);
                        PaymentActivity.this.finish();
                    } else {
                        Toast.makeText(PaymentActivity.this, "Please install KBZPay Application.", Toast.LENGTH_LONG).show();
                        webView.loadUrl("https://www.kbzpay.com/personal/en");
                    }
                }
                if (url.contains("https://bppay.blueplanet.com.mm/")) {
                    onBackPressed();
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                String message = "";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_INVALID:
                        message = "SSL connection is invalid.";
                        break;

                }
                if (!message.equals("")) {
                    isError = true;
                }
                Toast.makeText(PaymentActivity.this, message, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (request.isForMainFrame() && error != null) {
                        mErrorText.setText(R.string.bp_pay_connection_error);
                        errorView.setVisibility(View.VISIBLE);
                        webView.setVisibility(View.GONE);
                        isError = true;
                    }
                } else {
                    super.onReceivedError(view, request, error);
                }

                /*if (!NetworkUtility.isNetworkAvailable(PaymentActivity.this)) {
                    mErrorText.setText(R.string.connection_error);
                    errorView.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                }*/
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (errorCode != WebViewClient.ERROR_UNSUPPORTED_SCHEME && errorCode != WebViewClient.ERROR_HOST_LOOKUP) {
                    mErrorText.setText(R.string.bp_pay_connection_error);
                    errorView.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                    isError = true;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (url.equalsIgnoreCase("https://bppay.blueplanet.com.mm/")) {
                        /*shouldOverrideUrlLoading is not called for POST methods.
                         Example: for MPU payment.
                         Either payment success or fail, MPU redirects to bppay's home page with POST method.
                         */
                    onBackPressed();
                }

                mProgressBar.setVisibility(View.VISIBLE);
                errorView.setVisibility(View.GONE);
                webView.setVisibility(View.GONE);
                isError = false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                mProgressBar.setVisibility(View.GONE);
                if (!isError) {
                    errorView.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    private void reportToCallerActivity(int result, int price, String orderId) {
        Intent intent = new Intent();
        intent.putExtra(BPpay.KEY_PRICE, price);
        intent.putExtra(BPpay.KEY_ORDER_ID, orderId);

        setResult(result, intent);
    }

    private void bindViews() {
        mContainerView = findViewById(R.id.webview_container);
        mErrorText = findViewById(R.id.webview_error_text);
        mToolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.webview);
        errorView = findViewById(R.id.webview_error);
        mProgressBar = findViewById(R.id.progressbar);
    }

    private void updateTheme() {
        if (mUIConfig.getActionBarColor() != 0) {
            mToolbar.setBackgroundColor(mUIConfig.getActionBarColor());
        }

        if (mUIConfig.getTitleColor() != 0) {
            mToolbar.setTitleTextColor(mUIConfig.getTitleColor());
        }

        if (mUIConfig.getErrorBackgroundColor() != 0) {
            mContainerView.setBackgroundColor(mUIConfig.getErrorBackgroundColor());
        }

        if (mUIConfig.getErrorTextColor() != 0) {
            mErrorText.setTextColor(mUIConfig.getErrorTextColor());
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && mUIConfig.getActionBarBackgroundDrawable() != null) {
            mToolbar.setBackground(mUIConfig.getActionBarBackgroundDrawable());
        }

        if (mUIConfig.getNavigationIcon() != null) {
            mToolbar.setNavigationIcon(mUIConfig.getNavigationIcon());
        }
    }

    private boolean isAppInstalled(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    private String getPaymentFormData(String orderId, String price) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("merchant_id=")
                    .append(encode(mConfig.getMerchantId()))
                    .append("&service_name=")
                    .append(encode(mConfig.getServiceName()))
                    .append("&password=")
                    .append(encode(mConfig.getPassword()))
                    .append("&email=")
                    .append(encode(mConfig.getEmail()))
                    .append("&order_id=")
                    .append(encode(orderId))
                    .append("&amount=")
                    .append(encode(price));


        } catch (UnsupportedEncodingException e) {

        }
        return stringBuilder.toString();
    }

    private String encode(String data) throws UnsupportedEncodingException {
        return URLEncoder.encode(data, "UTF-8");
    }
}
