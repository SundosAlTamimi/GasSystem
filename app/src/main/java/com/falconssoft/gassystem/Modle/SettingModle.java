package com.falconssoft.gassystem.Modle;

import android.graphics.Bitmap;

public class SettingModle {

    private String ipAddress;
    private String companyName;
    private String accNo;
    private String taxNo;
    private Bitmap logo;
    private int savePrint;
    private String voucherSerial;
    private String recSerial;

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

    public void setSavePrint(int savePrint) {
        this.savePrint = savePrint;
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

    public int getSavePrint() {
        return savePrint;
    }

    public String getVoucherSerial() {
        return voucherSerial;
    }

    public void setVoucherSerial(String voucherSerial) {
        this.voucherSerial = voucherSerial;
    }

    public String getRecSerial() {
        return recSerial;
    }

    public void setRecSerial(String recSerial) {
        this.recSerial = recSerial;
    }
}
