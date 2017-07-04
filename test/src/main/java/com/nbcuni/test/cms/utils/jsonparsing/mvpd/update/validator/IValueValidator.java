package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.validator;

import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Brand;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Mvpd;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.MvpdLogoSettings;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities.MvpdFeed;

import java.util.List;

public interface IValueValidator {

    public boolean validateGeneralInformationInTheFeed(MvpdFeed expectedFeed);

    public boolean validateAllMvpdsInJson(List<Mvpd> mvpdList);

    public boolean verifyAllMvpdPresent(List<Mvpd> mvpdList);

    boolean validateSingleMvpdInJson(MvpdFeed mvpdFeed, Mvpd mvpd, Brand brand);

    boolean validateLogoFormat(MvpdFeed mvpdFeed, String mvpdId, String brandRequestorId, MvpdLogoSettings logoSettings);
}
