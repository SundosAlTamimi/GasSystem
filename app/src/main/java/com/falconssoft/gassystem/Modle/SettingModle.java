package com.falconssoft.gassystem.Modle;

import android.graphics.Bitmap;

public class SettingModle {

    private String ipAddress;
    private String companyName;
    private String accNo;
    private String taxNo;
    private Bitmap logo;

    public SettingModle() {

    }

    public SettingModle(String ipAddress, String companyName, String accNo, String taxNo, Bitmap logo) {
        this.ipAddress = ipAddress;
        this.companyName = companyName;
        this.accNo = accNo;
        this.taxNo = taxNo;
        this.logo = logo;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAccNo() {
        return accNo;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public Bitmap getLogo() {
        return logo;
    }
}
