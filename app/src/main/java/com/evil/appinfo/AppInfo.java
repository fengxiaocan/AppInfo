package com.evil.appinfo;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private String versionName;
    private String sha256;
    private Drawable icon;
    private String appName;
    private String packname;
    private String md5;
    private String sha1;
    private boolean isSysApp = true;

    public boolean isSysApp() {
        return isSysApp;
    }

    public void setSysApp(boolean sysApp) {
        isSysApp = sysApp;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    @Override
    public String toString() {
        return "appName=" +
               appName +
               "\r\n" +
               "packageName=" +
               packname +
               "\r\n" +
               "MD5=" +
               md5 +
               "\r\n" +
               "SHA1=" +
               sha1 +
               "\r\n" +
               "SHA256=" +
               sha256;
    }
}
