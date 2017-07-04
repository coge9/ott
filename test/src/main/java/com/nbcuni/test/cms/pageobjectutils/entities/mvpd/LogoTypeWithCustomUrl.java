package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

public class LogoTypeWithCustomUrl {

    private LogoTypes type;
    private String url;

    public LogoTypeWithCustomUrl(LogoTypes logoType, String customUrl) {
        this.type = logoType;
        this.url = customUrl;
    }

    public LogoTypes getType() {
        return type;
    }

    public void setType(LogoTypes type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LogoTypeWithCustomUrl other = (LogoTypeWithCustomUrl) obj;
        if (type != other.type)
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LogoTypeWithCustomUrl [type=" + type + ", url=" + url + "]";
    }

}
