package com.nbcuni.test.cms.pageobjectutils.mpx;

public enum ProgramSeriesType {

    FACE_OFF("16715666"), CNBC("18907876");


    private String name;

    ProgramSeriesType(final String contentTypeName) {
        name = contentTypeName;
    }

    public String get() {
        return name;
    }
}