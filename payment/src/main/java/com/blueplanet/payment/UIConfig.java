package com.blueplanet.payment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class UIConfig implements Serializable {

    public static class Builder {
        private Context mContext;
        private String title;
        private int titleColor;
        private int errorTextColor;
        private int errorBackgroundColor;
        private Drawable navigationIconDrawable;
        private int actionBarColor;
        private Drawable actionBarBackgroundDrawable;
        private boolean isActionBarShown = true;

        public Builder(Context context) {
            mContext = context;
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


        public UIConfig build() {

            UIConfig uiConfig = new UIConfig();

            uiConfig.title = title;
            uiConfig.titleColor = titleColor;
            uiConfig.actionBarColor = actionBarColor;
            uiConfig.isActionBarShown = isActionBarShown;
            uiConfig.errorTextColor = errorTextColor;
            uiConfig.errorBackgroundColor = errorBackgroundColor;
            uiConfig.navigationIconDrawable = navigationIconDrawable;
            uiConfig.actionBarBackgroundDrawable = actionBarBackgroundDrawable;

            return uiConfig;
        }

        private int getColor(int resId) {
            return mContext.getResources().getColor(resId);
        }

        private Drawable getDrawable(int resId) {
            return mContext.getResources().getDrawable(resId);
        }
    }

    private UIConfig() {

    }

    private String title;
    private int titleColor;
    private int errorTextColor;
    private int errorBackgroundColor;
    private Drawable navigationIconDrawable;
    private int actionBarColor;
    private Drawable actionBarBackgroundDrawable;
    private boolean isActionBarShown;

    public String getTitle() {
        return title;
    }

    public UIConfig setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public UIConfig setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public int getErrorTextColor() {
        return errorTextColor;
    }

    public UIConfig setErrorTextColor(int errorTextColor) {
        this.errorTextColor = errorTextColor;
        return this;
    }

    public int getErrorBackgroundColor() {
        return errorBackgroundColor;
    }

    public UIConfig setErrorBackgroundColor(int errorBackgroundColor) {
        this.errorBackgroundColor = errorBackgroundColor;
        return this;
    }

    public Drawable getNavigationIcon() {
        return navigationIconDrawable;
    }

    public UIConfig setNavigationIcon(Drawable navigationIconDrawable) {
        this.navigationIconDrawable = navigationIconDrawable;
        return this;
    }

    public int getActionBarColor() {
        return actionBarColor;
    }

    public UIConfig setActionBarColor(int actionBarColor) {
        this.actionBarColor = actionBarColor;
        return this;
    }

    public Drawable getActionBarBackgroundDrawable() {
        return actionBarBackgroundDrawable;
    }

    public UIConfig setActionBarBackgroundDrawable(Drawable actionBarBackgroundDrawable) {
        this.actionBarBackgroundDrawable = actionBarBackgroundDrawable;
        return this;
    }

    public boolean isActionBarShown() {
        return isActionBarShown;
    }

    public UIConfig setActionBarShown(boolean actionBarShown) {
        isActionBarShown = actionBarShown;
        return this;
    }
}
