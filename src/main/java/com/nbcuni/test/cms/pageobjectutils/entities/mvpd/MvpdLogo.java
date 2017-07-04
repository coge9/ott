package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;


public class MvpdLogo {

    private LogoTypes type;
    private String path;
    private String fileName;
    private String stringType;

    public MvpdLogo() {
        super();
    }

    public MvpdLogo(final LogoTypes type, final String path) {
        super();
        this.type = type;
        this.path = path;
        this.stringType = type.get();
    }

    @Override
    public String toString() {
        return "Logo [type=" + type + ", path=" + path + "]";
    }

    public LogoTypes getType() {
        return type;
    }

    public void setType(final LogoTypes type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStringType() {
        return stringType;
    }

    public void setStringType(String stringType) {
        this.stringType = stringType;
    }
}