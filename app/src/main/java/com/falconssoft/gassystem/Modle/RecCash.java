package com.falconssoft.gassystem.Modle;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class RecCash {
//http://localhost:8082/SaveRECCASH?JSONSTR={"REC":[{"RECNO":"123","ACCNAME":"ALAMAWI","ACCNO":"10115222",
// "CASH":"150","REMARKS":"NOTE","RECDATE":"01/01/2020","IS_POST":"0","PRJNAME":"AAMMMAAMMAANNN"}]}
    private String resNo;
    private String accName;
    private String accNo;
    private String cash;
    private String remarks;
    private String recDate;
    private String is_Post;
    private String projectName;
    private String isExport;
    private String serial;
    private String counterNo;
    private String lastBalance;
    private String oldCash;
    private String oldRemark;
    private String status;

    public RecCash() {

    }

    public RecCash(String resNo, String accName, String accNo, String cash, String remarks, String recDate, String is_Post,
                   String projectName, String isExport, String serial, String counterNo, String lastBalance, String oldCash,
                   String oldRemark, String status) {
        this.resNo = resNo;
        this.accName = accName;
        this.accNo = accNo;
        this.cash = cash;
        this.remarks = remarks;
        this.recDate = recDate;
        this.is_Post = is_Post;
        this.projectName = projectName;
        this.isExport = isExport;
        this.serial = serial;
        this.counterNo = counterNo;
        this.lastBalance = lastBalance;
        this.oldCash = oldCash;
        this.oldRemark = oldRemark;
        this.status = status;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRecDate() {
        return recDate;
    }

    public void setRecDate(String recDate) {
        this.recDate = recDate;
    }

    public String getIs_Post() {
        return is_Post;
    }

    public void setIs_Post(String is_Post) {
        this.is_Post = is_Post;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(String counterNo) {
        this.counterNo = counterNo;
    }

    public String getLastBalance() {
        return lastBalance;
    }

    public void setLastBalance(String lastBalance) {
        this.lastBalance = lastBalance;
    }

    public String getOldCash() {
        return oldCash;
    }

    public void setOldCash(String oldCash) {
        this.oldCash = oldCash;
    }

    public String getOldRemark() {
        return oldRemark;
    }

    public void setOldRemark(String oldRemark) {
        this.oldRemark = oldRemark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONObject getJSONObjectRecCash() { // for server
        JSONObject obj = new JSONObject();
        try {
//http://localhost:8082/SaveRECCASH?JSONSTR={"REC":[{"RECNO":"123","ACCNAME":"ALAMAWI","ACCNO":"10115222",
// "CASH":"150","REMARKS":"NOTE","RECDATE":"01/01/2020","IS_POST":"0","PRJNAME":"AAMMMAAMMAANNN"}]}
            obj.put("RECNO", resNo);
            obj.put("ACCNAME", accName);
            obj.put("ACCNO", accNo);
            obj.put("CASH", cash);
            obj.put("REMARKS", remarks);
            obj.put("RECDATE",recDate );
            obj.put("IS_POST", is_Post);
            obj.put("PRJNAME", projectName);



        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }

}
