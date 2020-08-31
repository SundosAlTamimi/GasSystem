package com.falconssoft.gassystem.Modle;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class VoucherModle {

//    http://localhost:8082/SaveInvoice?JSONSTR={"INV":[{"COUNTERNO":"10115","CUSTOMERNAME":"Mohammad Alamawi",
//    "LASTREADER":"10","ACCNO":"10115222","GASPRESSURE":"1.3","GPRICE":"580","PRJECTNAME":"AAAAA","MO3AMEL":"1",
//    "CURRENTREADER":"12","CCOST":"123","CCOSTVAL":"456","SERV":"4","REQVALUE":"123456","READERDATE":"01/08/2020",
//    "INVTYPE":"1","INVNO":"111","NETVAL":"1256","TAXVAL":"120","GRET":"0","REMARKS":"afsdfsdfsdfsg ryerysdgd",
//    "ESTEHLAK":"1242","CREDIT":"0","IS_POST":"0","IS_PER":"1","BDLVAL":"15"}]}

    private String counterNo;
    private String customerName;
    private String lastReader;//القراءه السابقه
    private String accNo;// الحساب
    private String gasPressure;
    private String gasPrice;
    private String projectName;
    private String prameter;//معامل
    private String currentReader;
    private String CCost;//الاستهلاك
    private String cCostVal;//
    private String service;//بدل خدمه
    private String reQalValue;//القيمه المطلوبه
    private String readerDate;//تاريخ القر
    private String invoiceType;//501
    private String invoiceNo;//رقم الفاتوره
    private String netValue;//الصافي
    private String taxValue;//الضريبه
    private String Gret;//اعادة غاز
    private String remarks;

    private String consumption;//استهلاك الفتره
    private String credit;//رصيد سابق
    private String isPost;//0
    private String isPer;//0
    private String allowance;//0
    private String isExport;
    private String serial;
    private String status;

    public VoucherModle() {

    }

    public VoucherModle(String counterNo, String customerName, String lastReader, String accNo, String gasPressure, String gasPrice,
                        String projectName, String prameter, String currentReader, String CCost, String cCostVal, String service,
                        String reQalValue, String readerDate, String invoiceType, String invoiceNo, String netValue, String taxValue,
                        String gret, String remarks, String consumption, String credit, String isPost, String isPer, String allowance,
                        String isExport, String serial, String status) {
        this.counterNo = counterNo;
        this.customerName = customerName;
        this.lastReader = lastReader;
        this.accNo = accNo;
        this.gasPressure = gasPressure;
        this.gasPrice = gasPrice;
        this.projectName = projectName;
        this.prameter = prameter;
        this.currentReader = currentReader;
        this.CCost = CCost;
        this.cCostVal = cCostVal;
        this.service = service;
        this.reQalValue = reQalValue;
        this.readerDate = readerDate;
        this.invoiceType = invoiceType;
        this.invoiceNo = invoiceNo;
        this.netValue = netValue;
        this.taxValue = taxValue;
        this.Gret = gret;
        this.remarks = remarks;
        this.consumption = consumption;
        this.credit = credit;
        this.isPost = isPost;
        this.isPer = isPer;
        this.allowance = allowance;
        this.isExport = isExport;
        this.serial = serial;
        this.status = status;
    }

    public String getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(String counterNo) {
        this.counterNo = counterNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLastReader() {
        return lastReader;
    }

    public void setLastReader(String lastReader) {
        this.lastReader = lastReader;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getGasPressure() {
        return gasPressure;
    }

    public void setGasPressure(String gasPressure) {
        this.gasPressure = gasPressure;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPrameter() {
        return prameter;
    }

    public void setPrameter(String prameter) {
        this.prameter = prameter;
    }

    public String getCurrentReader() {
        return currentReader;
    }

    public void setCurrentReader(String currentReader) {
        this.currentReader = currentReader;
    }

    public String getCCost() {
        return CCost;
    }

    public void setCCost(String CCost) {
        this.CCost = CCost;
    }

    public String getcCostVal() {
        return cCostVal;
    }

    public void setcCostVal(String cCostVal) {
        this.cCostVal = cCostVal;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getReQalValue() {
        return reQalValue;
    }

    public void setReQalValue(String reQalValue) {
        this.reQalValue = reQalValue;
    }

    public String getReaderDate() {
        return readerDate;
    }

    public void setReaderDate(String readerDate) {
        this.readerDate = readerDate;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getNetValue() {
        return netValue;
    }

    public void setNetValue(String netValue) {
        this.netValue = netValue;
    }

    public String getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(String taxValue) {
        this.taxValue = taxValue;
    }

    public String getGret() {
        return Gret;
    }

    public void setGret(String gret) {
        Gret = gret;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getIsPost() {
        return isPost;
    }

    public void setIsPost(String isPost) {
        this.isPost = isPost;
    }

    public String getIsPer() {
        return isPer;
    }

    public void setIsPer(String isPer) {
        this.isPer = isPer;
    }

    public String getAllowance() {
        return allowance;
    }

    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }

    public String getIsExport() {
        return isExport;
    }

    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }

    public String getSerial() {
        return serial;
    }


    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONObject getJSONObjectVoucher() { // for server
        JSONObject obj = new JSONObject();
        try {

            //    http://localhost:8082/SaveInvoice?JSONSTR={"INV":[{"COUNTERNO":"10115","CUSTOMERNAME":"Mohammad Alamawi",
//    "LASTREADER":"10","ACCNO":"10115222","GASPRESSURE":"1.3","GPRICE":"580","PRJECTNAME":"AAAAA","MO3AMEL":"1",
//    "CURRENTREADER":"12","CCOST":"123","CCOSTVAL":"456","SERV":"4","REQVALUE":"123456","READERDATE":"01/08/2020",
//    "INVTYPE":"1","INVNO":"111","NETVAL":"1256","TAXVAL":"120","GRET":"0","REMARKS":"afsdfsdfsdfsg ryerysdgd",
//    "ESTEHLAK":"1242","CREDIT":"0","IS_POST":"0","IS_PER":"1","BDLVAL":"15"}]}


            obj.put("COUNTERNO", counterNo);
            obj.put("CUSTOMERNAME", customerName);
            obj.put("LASTREADER", lastReader);
            obj.put("ACCNO", accNo);
            obj.put("GASPRESSURE", gasPressure);
            obj.put("GPRICE",gasPrice );
            obj.put("PRJECTNAME", projectName);
            obj.put("MO3AMEL", prameter);

            obj.put("CURRENTREADER", currentReader);
            obj.put("CCOST", CCost);
            obj.put("CCOSTVAL", cCostVal);
            obj.put("SERV", service);
            obj.put("REQVALUE", reQalValue);
            obj.put("READERDATE",readerDate );

            obj.put("INVTYPE", invoiceType);
            obj.put("INVNO", invoiceNo);
            obj.put("NETVAL", netValue);
            obj.put("TAXVAL", taxValue);
            obj.put("GRET", Gret);
            obj.put("REMARKS",remarks );


            obj.put("ESTEHLAK", consumption);
            obj.put("CREDIT", credit);


            obj.put("IS_POST", isPost);
            obj.put("IS_PER", isPer);
            obj.put("BDLVAL", allowance);


        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }

}
