package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities;

import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.MvpdErrorMessage;
import com.nbcuni.test.webdriver.Utilities;

import java.util.*;

public class MvpdFeed {

    private String brand;
    private String filePath;
    private String adobePassEndPoint;
    private List<MvpdErrorMessage> globalErrorMessages;
    private Map<String, MvpdFromJson> mvpds = new HashMap<String, MvpdFromJson>();
    private Random random = new Random();
    private boolean isLogotypeFilter = false;
    private String logoTypefilterValue;

    public MvpdFeed() {
        mvpds = new HashMap<String, MvpdFromJson>();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAdobePassEndPoint() {
        return adobePassEndPoint;
    }

    public void setAdobePassEndPoint(String adobePassEndPoint) {
        this.adobePassEndPoint = adobePassEndPoint;
    }

    public boolean isLogotypeFilter() {
        return isLogotypeFilter;
    }

    public void setLogotypeFilter(boolean isLogotypeFilter) {
        this.isLogotypeFilter = isLogotypeFilter;
    }

    public String getLogoTypefilterValue() {
        return logoTypefilterValue;
    }

    public void setLogoTypefilterValue(String logoTypefilterValue) {
        this.logoTypefilterValue = logoTypefilterValue;
    }

    public List<MvpdErrorMessage> getGlobalErrorMessages() {
        return globalErrorMessages;
    }

    public void setGlobalErrorMessages(
            List<MvpdErrorMessage> globalErrorMessages) {
        this.globalErrorMessages = globalErrorMessages;
    }

    public void addErrorMessage(MvpdErrorMessage errorMessage) {
        if (globalErrorMessages != null) {
            globalErrorMessages.add(errorMessage);
        }
    }

    public Map<String, MvpdFromJson> getMvpds() {
        return mvpds;
    }

    public void setMvpds(Map<String, MvpdFromJson> mvpds) {
        this.mvpds = mvpds;
    }

    public void addMvpd(MvpdFromJson mvpd) {
        if (mvpds != null) {
            mvpds.put(mvpd.getId(), mvpd);
        }
    }

    public MvpdFromJson getMvpdFromFeedById(String id) {
        return mvpds.get(id);
    }

    public void removeMvpdFromFeedById(String id) {
        if (mvpds.get(id) != null) {
            mvpds.remove(id);
        }
    }

    public MvpdFromJson getRandomMvpdFromFeed() {
        if (!mvpds.isEmpty()) {
            int index = random.nextInt(mvpds.size());
            String id = (String) mvpds.keySet().toArray()[index];
            MvpdFromJson mvpd = mvpds.get(id);
            return mvpd;
        }
        return null;
    }

    public MvpdFromJson getMvpdFromFeedByName(String name) {
        for (MvpdFromJson item : mvpds.values()) {
            if ((item.getTitle() != null && item.getTitle().equalsIgnoreCase(
                    name))
                    || (item.getDisplayName() != null && item.getDisplayName()
                    .equalsIgnoreCase(name))) {
                return item;
            }
        }
        return null;
    }

    public boolean compateWithAnotherFeed(MvpdFeed otherFeed) {
        Boolean status = true;
        Set<String> currentIds = new HashSet<String>(mvpds.keySet());
        Set<String> otherIds = new HashSet<String>(otherFeed.getMvpds().keySet());
        for (String id : otherIds) {
            if (!currentIds.contains(id)) {
                status = false;
                Utilities.logSevereMessage("MvpdsFeed miss " + id + " mvpd");
            }
            currentIds.remove(id);
        }
        for (String id : currentIds) {
            Utilities.logSevereMessage("MvpdsFeed has extra " + id + " mvpd");
        }
        if (!currentIds.isEmpty()) {
            status = false;
        }
        return status;
    }

    public int getNumberOfMvpdsInTheFeed() {
        return mvpds.size();
    }

}
