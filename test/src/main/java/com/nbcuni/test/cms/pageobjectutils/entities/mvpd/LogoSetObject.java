package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

import java.util.ArrayList;
import java.util.List;

public class LogoSetObject {

    private String name;
    private List<String> logoTypes;
    private List<LogoTypeObject> logoTypesObjects;

    public LogoSetObject() {
        super();
    }

    public LogoSetObject(String name, List<String> logoTypes) {
        super();
        this.name = name;
        this.logoTypes = logoTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLogoTypes() {
        return logoTypes;
    }

    public void setLogoTypes(List<String> logoTypes) {
        this.logoTypes = logoTypes;
    }

    public List<LogoTypeObject> getLogoTypesObjects() {
        return logoTypesObjects;
    }

    public void setLogoTypesObjects(List<LogoTypeObject> logoTypesObjects) {
        this.logoTypesObjects = logoTypesObjects;
        logoTypes = new ArrayList<String>();
        for (LogoTypeObject logoTypeObject : logoTypesObjects) {
            logoTypes.add(logoTypeObject.getName());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogoSetObject that = (LogoSetObject) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return logoTypes != null ? logoTypes.equals(that.logoTypes) : that.logoTypes == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (logoTypes != null ? logoTypes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LogoSetObject{" +
                "name='" + name + '\'' +
                ", logoTypes=" + logoTypes +
                '}';
    }
}
