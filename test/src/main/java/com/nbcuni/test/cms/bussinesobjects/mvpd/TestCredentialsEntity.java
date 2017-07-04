package com.nbcuni.test.cms.bussinesobjects.mvpd;

import com.nbcuni.test.cms.utils.SimpleUtils;

/**
 * Created by Dzianis_Kulesh on 9/14/2016.
 */
public class TestCredentialsEntity {

    private String testCase;
    private String email;
    private String password;


    public String getTestCase() {
        return testCase;
    }

    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean areAllValuesEmpty() {
        boolean status = true;
        status = status && testCase == null ? true : testCase.isEmpty();
        status = status && email == null ? true : email.isEmpty();
        status = status && email == null ? true : email.isEmpty();
        return status;
    }

    public void updateValuesWithRandomStrings() {
        if (testCase != null) {
            testCase += SimpleUtils.getRandomString(SimpleUtils.randomNumber(10, 15));
        }
        if (email != null) {
            email += SimpleUtils.getRandomString(SimpleUtils.randomNumber(10, 15));
        }
        if (password != null) {
            password += SimpleUtils.getRandomString(SimpleUtils.randomNumber(10, 15));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestCredentialsEntity that = (TestCredentialsEntity) o;

        if (testCase != null ? !testCase.equals(that.testCase) : that.testCase != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;

    }

    @Override
    public int hashCode() {
        int result = testCase != null ? testCase.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TestCredentialsEntity{" +
                "testCase='" + testCase + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
