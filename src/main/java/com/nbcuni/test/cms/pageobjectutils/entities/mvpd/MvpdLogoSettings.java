package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

import java.util.ArrayList;
import java.util.List;

public class MvpdLogoSettings {

    private List<MvpdLogoSettingItem> mvpdLogoSetings;

    public MvpdLogoSettings() {
    }

    public MvpdLogoSettings(List<MvpdLogoSettingItem> mvpdLogoSetings) {
        this.mvpdLogoSetings = mvpdLogoSetings;
    }

    public List<MvpdLogoSettingItem> getMvpdLogoSetings() {
        return mvpdLogoSetings;
    }

    public void setMvpdLogoSetings(List<MvpdLogoSettingItem> mvpdLogoSetings) {
        this.mvpdLogoSetings = mvpdLogoSetings;
    }

    public List<MvpdLogoSettingItem> getItemsByPlatform(Platform platform) {
        List<MvpdLogoSettingItem> toReturn = new ArrayList<MvpdLogoSettingItem>();
        for (MvpdLogoSettingItem item : mvpdLogoSetings) {
            if (item.getPlatform().equals(platform)) {
                toReturn.add(item);
            }
        }
        return toReturn;
    }

    public List<MvpdLogoSettingItem> getItemsByBrand(String brand) {
        List<MvpdLogoSettingItem> toReturn = new ArrayList<MvpdLogoSettingItem>();
        for (MvpdLogoSettingItem item : mvpdLogoSetings) {
            if (item.getBrandName().equals(brand)) {
                toReturn.add(item);
            }
        }
        return toReturn;
    }

    public MvpdLogoSettingItem getItemByPlatformAndSection(Platform platform, SelectLogoSection selectLogoSection) {
        for (MvpdLogoSettingItem toReturn : getItemsByPlatform(platform)) {
            if (toReturn.getSectionName().equals(selectLogoSection)) {
                return toReturn;
            }
        }
        throw new RuntimeException("Havent logo item with section: [" + selectLogoSection + "].");
    }

    public MvpdLogoSettingItem getItemByPlatformSectionBrand(Platform platform, SelectLogoSection selectLogoSection, String brand) {
        for (MvpdLogoSettingItem toReturn : getItemsByBrand(brand)) {
            if (toReturn.getSectionName().equals(selectLogoSection) && toReturn.getPlatform().equals(platform)) {
                return toReturn;
            }
        }
        throw new RuntimeException("Havent logo item with section: [" + selectLogoSection + "].");
    }

    public void setBrandIdToAllItems(String brand) {
        for (MvpdLogoSettingItem item : mvpdLogoSetings) {
            item.setBrandName(brand);
        }
    }
}
