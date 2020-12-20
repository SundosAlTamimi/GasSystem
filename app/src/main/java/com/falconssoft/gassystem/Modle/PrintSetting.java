package com.falconssoft.gassystem.Modle;

public class PrintSetting {
    private String printType;
    private String formType;



    public PrintSetting() {
        this.printType = printType;
        this.formType = formType;
    }

    public PrintSetting(String printType, String formType) {
        this.printType = printType;
        this.formType = formType;
    }


    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }
}
