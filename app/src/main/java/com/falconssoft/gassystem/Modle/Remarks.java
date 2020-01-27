package com.falconssoft.gassystem.Modle;

public class Remarks {
    private String title;
    private String body;

    public Remarks(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Remarks() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
