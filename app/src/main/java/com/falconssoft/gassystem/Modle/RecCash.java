package com.falconssoft.gassystem.Modle;

public class RecCash {

    private String RECNO;
    private String ACCNAME;
    private String ACCNO;
    private String CASH;
    private String REMARKS;
    private String RECDATE;
    private String IS_POST;
    private String PRJNAME;


    public RecCash() {

    }

    public RecCash(String RECNO, String ACCNAME, String ACCNO, String CASH, String REMARKS, String RECDATE, String IS_POST, String PRJNAME) {
        this.RECNO = RECNO;
        this.ACCNAME = ACCNAME;
        this.ACCNO = ACCNO;
        this.CASH = CASH;
        this.REMARKS = REMARKS;
        this.RECDATE = RECDATE;
        this.IS_POST = IS_POST;
        this.PRJNAME = PRJNAME;
    }

    public String getRECNO() {
        return RECNO;
    }

    public void setRECNO(String RECNO) {
        this.RECNO = RECNO;
    }

    public String getACCNAME() {
        return ACCNAME;
    }

    public void setACCNAME(String ACCNAME) {
        this.ACCNAME = ACCNAME;
    }

    public String getACCNO() {
        return ACCNO;
    }

    public void setACCNO(String ACCNO) {
        this.ACCNO = ACCNO;
    }

    public String getCASH() {
        return CASH;
    }

    public void setCASH(String CASH) {
        this.CASH = CASH;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public String getRECDATE() {
        return RECDATE;
    }

    public void setRECDATE(String RECDATE) {
        this.RECDATE = RECDATE;
    }

    public String getIS_POST() {
        return IS_POST;
    }

    public void setIS_POST(String IS_POST) {
        this.IS_POST = IS_POST;
    }

    public String getPRJNAME() {
        return PRJNAME;
    }

    public void setPRJNAME(String PRJNAME) {
        this.PRJNAME = PRJNAME;
    }
}
