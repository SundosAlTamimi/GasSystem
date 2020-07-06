package com.falconssoft.gassystem.Modle;

public class Voucher {

    private String counterNo;
    private String custName;
    private double previousRead;
    private double currentRead;
    private double consuming;
    private double consumingValue;
    private double previousPalance;
    private double badalGas;
    private double badalService;
    private double serviceNoTax;
    private double net;
    private double tax;
    private double currentConsuming;
    private double lastValue;
    private String note;

    public Voucher(String counterNo, String custName, double previousRead, double currentRead, double consuming, double consumingValue,
                   double previousPalance, double badalGas, double badalService, double serviceNoTax, double net, double tax, double currentConsuming, double lastValue,
                   String note) {

        this.counterNo = counterNo;
        this.custName = custName;
        this.previousRead = previousRead;
        this.currentRead = currentRead;
        this.consuming = consuming;
        this.consumingValue = consumingValue;
        this.previousPalance = previousPalance;
        this.badalGas = badalGas;
        this.badalService = badalService;
        this.serviceNoTax = serviceNoTax;
        this.net = net;
        this.tax = tax;
        this.currentConsuming = currentConsuming;
        this.lastValue = lastValue;
        this.note = note;
    }

    public String getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(String counterNo) {
        this.counterNo = counterNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public double getPreviousRead() {
        return previousRead;
    }

    public void setPreviousRead(double previousRead) {
        this.previousRead = previousRead;
    }

    public double getCurrentRead() {
        return currentRead;
    }

    public void setCurrentRead(double currentRead) {
        this.currentRead = currentRead;
    }

    public double getConsuming() {
        return consuming;
    }

    public void setConsuming(double consuming) {
        this.consuming = consuming;
    }

    public double getConsumingValue() {
        return consumingValue;
    }

    public void setConsumingValue(double consumingValue) {
        this.consumingValue = consumingValue;
    }

    public double getPreviousPalance() {
        return previousPalance;
    }

    public void setPreviousPalance(double previousPalance) {
        this.previousPalance = previousPalance;
    }

    public double getBadalGas() {
        return badalGas;
    }

    public void setBadalGas(double badalGas) {
        this.badalGas = badalGas;
    }

    public double getBadalService() {
        return badalService;
    }

    public void setBadalService(double badalService) {
        this.badalService = badalService;
    }

    public double getServiceNoTax() {
        return serviceNoTax;
    }

    public void setServiceNoTax(double serviceNoTax) {
        this.serviceNoTax = serviceNoTax;
    }

    public double getNet() {
        return net;
    }

    public void setNet(double net) {
        this.net = net;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getCurrentConsuming() {
        return currentConsuming;
    }

    public void setCurrentConsuming(double currentConsuming) {
        this.currentConsuming = currentConsuming;
    }

    public double getLastValue() {
        return lastValue;
    }

    public void setLastValue(double lastValue) {
        this.lastValue = lastValue;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
