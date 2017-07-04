package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dzianis_Kulesh on 8/16/2016.
 */
public class WhitelistGroup {

    String name;
    String targetStageDate;
    String targetProdDate;
    List<String> mvpds = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetStageDate() {
        return targetStageDate;
    }

    public void setTargetStageDate(String targetStageDate) {
        this.targetStageDate = targetStageDate;
    }

    public String getTargetProdDate() {
        return targetProdDate;
    }

    public void setTargetProdDate(String targetProdDate) {
        this.targetProdDate = targetProdDate;
    }

    public List<String> getMvpds() {
        return mvpds;
    }

    public void setMvpds(List<String> mvpds) {
        this.mvpds = mvpds;
    }

    public void addMvpd(String name) {
        mvpds.add(name);
    }

    public void clearMvpds() {
        mvpds.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WhitelistGroup that = (WhitelistGroup) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (targetStageDate != null ? !targetStageDate.equals(that.targetStageDate) : that.targetStageDate != null)
            return false;
        if (targetProdDate != null ? !targetProdDate.equals(that.targetProdDate) : that.targetProdDate != null)
            return false;
        return mvpds != null ? mvpds.equals(that.mvpds) : that.mvpds == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (targetStageDate != null ? targetStageDate.hashCode() : 0);
        result = 31 * result + (targetProdDate != null ? targetProdDate.hashCode() : 0);
        result = 31 * result + (mvpds != null ? mvpds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WhitelistGroup{" +
                "name='" + name + '\'' +
                ", targetStageDate='" + targetStageDate + '\'' +
                ", targetProdDate='" + targetProdDate + '\'' +
                ", mvpds=" + mvpds +
                '}';
    }
}
