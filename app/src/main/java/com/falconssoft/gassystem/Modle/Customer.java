package com.falconssoft.gassystem.Modle;

public class Customer {

    private String counterNo;
    private String accNo;
    private String custName;
    private double lastRead;
    private double gasPressure;
    private double credet;
    private double gPrice;
    private String projectName;
    private int isPer;
    private double badalVal;
    private int custSts;

    public Customer(String counterNo, String accNo, String custName, double lastRead, double gasPressure, double credet, double gPrice, String projectName, int isPer, double badalVal, int custSts) {
        this.counterNo = counterNo;
        this.accNo = accNo;
        this.custName = custName;
        this.lastRead = lastRead;
        this.gasPressure = gasPressure;
        this.credet = credet;
        this.gPrice = gPrice;
        this.projectName = projectName;
        this.isPer = isPer;
        this.badalVal = badalVal;
        this.custSts = custSts;
    }

    public Customer() {

    }

    public String getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(String counterNo) {
        this.counterNo = counterNo;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public double getLastRead() {
        return lastRead;
    }

    public void setLastRead(double lastRead) {
        this.lastRead = lastRead;
    }

    public double getGasPressure() {
        return gasPressure;
    }

    public void setGasPressure(double gasPressure) {
        this.gasPressure = gasPressure;
    }

    public double getCredet() {
        return credet;
    }

    public void setCredet(double credet) {
        this.credet = credet;
    }

    public double getgPrice() {
        return gPrice;
    }

    public void setgPrice(double gPrice) {
        this.gPrice = gPrice;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getIsPer() {
        return isPer;
    }

    public void setIsPer(int isPer) {
        this.isPer = isPer;
    }

    public double getBadalVal() {
        return badalVal;
    }

    public void setBadalVal(double badalVal) {
        this.badalVal = badalVal;
    }

    public int getCustSts() {
        return custSts;
    }

    public void setCustSts(int custSts) {
        this.custSts = custSts;
    }
}
