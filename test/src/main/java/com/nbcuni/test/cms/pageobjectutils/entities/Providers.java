package com.nbcuni.test.cms.pageobjectutils.entities;

public class Providers {
    private String name;
    private String login;
    private String password;

    public Providers(final String name, final String login, final String password) {
        super();
        this.name = name;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Providers [name=" + name + ", login=" + login + ", password=" + password + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}