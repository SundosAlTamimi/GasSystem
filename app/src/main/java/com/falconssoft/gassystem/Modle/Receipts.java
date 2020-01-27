package com.falconssoft.gassystem.Modle;

public class Receipts {

    private String receiptNo;
    private String custName;
    private String projectName;
    private String credit;
    private String accNo;
    private String counterNo;
    private double value;
    private String note;

    public String getReceiptNo() {
        return receiptNo;
    }

    public Receipts(String receiptNo, String custName, String projectName, String credit, String accNo, String counterNo, double value, String note) {
        this.receiptNo = receiptNo;
        this.custName = custName;
        this.projectName = projectName;
        this.credit = credit;
        this.accNo = accNo;
        this.counterNo = counterNo;
        this.value = value;
        this.note = note;


    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(String counterNo) {
        this.counterNo = counterNo;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
