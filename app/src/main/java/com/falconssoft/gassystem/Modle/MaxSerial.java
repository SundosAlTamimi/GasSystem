package com.falconssoft.gassystem.Modle;

public class MaxSerial {
    private  String SerialMax;
    private  String colomMax;
    public MaxSerial() {

    }

    public MaxSerial(String serialMax, String colomMax) {
        SerialMax = serialMax;
        this.colomMax = colomMax;
    }

    public String getSerialMax() {
        return SerialMax;
    }

    public void setSerialMax(String serialMax) {
        SerialMax = serialMax;
    }

    public String getColomMax() {
        return colomMax;
    }

    public void setColomMax(String colomMax) {
        this.colomMax = colomMax;
    }
}
