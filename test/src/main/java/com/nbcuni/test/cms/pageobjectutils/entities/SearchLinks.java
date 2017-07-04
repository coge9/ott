package com.nbcuni.test.cms.pageobjectutils.entities;

public class SearchLinks {
    private String name;
    private String status;

    public SearchLinks(final String name, final String status) {
        super();
        this.name = name;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Providers [name=" + name + ", status=" + status + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLogin() {
        return status;
    }

    public void setLogin(final String login) {
        this.status = login;
    }
}
