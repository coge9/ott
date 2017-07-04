package com.nbcuni.test.cms.backend.theplatform;

import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SikuliUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import java.util.Map;

public class MPXMetadata {
    private final SikuliUtil util;
    private final String imagePath;

    public MPXMetadata(final Config config) {
        util = new SikuliUtil(config, new Screen());
        imagePath = config.getPathToImages();
    }

    public void giveFocusToMediaItem() {
        Utilities.logInfoMessage("Click the 'Earth icon' on the isSearch result row to give focus to the media item");
        util.waitForImgPresent(imagePath + "Media/Title_Txb.png");
        util.click(imagePath + "Media/Title_Txb.png");
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            Utilities.logSevereMessage(e.getLocalizedMessage());
        }
    }

    public void updateEpisodeData(final Map<String, String> newData) {
        enterTitle(newData.get("Title"));
        enterDescription(newData.get("Description"));
        enterCategories("Series/" + newData.get("Categories"));
        enterAdvertisingGenre(newData.get("Advertising genre"));
        setAirDate(newData.get("Airdate"));
        enterSeason(newData.get("Season"));
        enterEpisode(newData.get("Episode #"));
        enterFullEpisode(newData.get("FullEpisode"));
        selectEntitlement(newData.get("Entitlement"));
        selectDayPart(newData.get("Day Part"));
        // enterProgram(newData.get("Program"));
        // enterSeries(newData.get("Series"));
        // selectEntitlement(newData.getBrandName("Entitlement"));

        openPoliciesTab();
        setAvailableDate(newData.get("Available date"));
        setExpirationDate(newData.get("Expiration date"));

        clickSaveBtn(true);
    }

    public void updateRokuEpisodeData(final Map<String, String> newData) {

        enterTitle(newData.get("Title"));
        enterDescription(newData.get("Description"));
        enterCategories(newData.get("Categories"));
        enterAdvertisingGenre(newData.get("Advertising genre"));
        setAirDate(newData.get("Airdate"));
        enterSeason(newData.get("Season #"));
        enterEpisode(newData.get("Episode #"));
        enterFullEpisode(newData.get("FullEpisode"));
        selectEntitlement(newData.get("Entitlement"));
        setTuneInTime(newData.get("TuneInTime"));
        setShortDescription(newData.get("Short Description"));
        setProgrammingType(newData.get("ProgrammingType"));
        selectDayPart(newData.get("Day Part"));
        openPoliciesTab();
        setAvailableDate(newData.get("Available date"));
        setExpirationDate(newData.get("Expiration date"));
        clickSaveBtn(true);
    }

    public void updatePartuallyEpisodeData(final Map<String, String> newData) {
        // enterTitle(newData.getBrandName("Title"));
        enterDescription(newData.get("Description"));
        enterCategories("Series/" + newData.get("Categories"));
        enterAdvertisingGenre(newData.get("Advertising genre"));
        setAirDate(newData.get("Airdate"));
        enterSeason(newData.get("Season"));
        enterEpisode(newData.get("Episode #"));
        enterFullEpisode(newData.get("FullEpisode"));
        selectEntitlement(newData.get("Entitlement"));

        openPoliciesTab();
        setAvailableDate(newData.get("Available date"));
        setExpirationDate(newData.get("Expiration date"));
        clickSaveBtn(true);
    }

    public void setAvailableExpirationDate(String available, String expiration) {
        openPoliciesTab();
        setAvailableDate(available);
        setExpirationDate(expiration);
        clickSaveBtn(true);
    }

    public void enterTitle(final String mediaTitle) {
        if (mediaTitle != null) {
            Utilities.logInfoMessage("Enter '" + mediaTitle + "' in the 'Title' text box");
            util.scrollDownForImgPresent(imagePath + "Media/Title_Txb.png");
            final Pattern pattern = new Pattern(imagePath
                    + "Media/Title_Txb.png").targetOffset(5, 15);
            util.clickInRegion(pattern);
            util.type(mediaTitle);
        }
    }

    public void enterDescription(final String txt) {
        if (txt != null) {
            Utilities.logInfoMessage("Enter '" + txt + "' in the 'Description' text box");
            util.scrollDownForImgPresent(imagePath
                    + "Media/Description_Txb.png");
            final Pattern pattern = new Pattern(imagePath
                    + "Media/Description_Txb.png").targetOffset(5, 15);
            util.clickInRegion(pattern);
            util.type(txt);
        }
    }

    public void enterCategories(final String txt) {
        if (txt != null) {
            Utilities.logInfoMessage("Enter '" + txt + "' in the 'Categories' text box");
            util.scrollDownForImgPresent(imagePath + "Media/Categories_Txb.png");
            final Pattern pattern = new Pattern(imagePath
                    + "Media/Categories_Txb.png").targetOffset(5, 15);
            util.clickInRegion(pattern);
            util.type(txt);
        }
    }

    public void enterSeason(final String txt) {
        if (txt != null) {
            Utilities.logInfoMessage("Enter '" + txt + "' in the 'Season #' text box");
            util.scrollDownForImgPresent(imagePath + "Media/Season_Txb.png");
            util.scroll("Down", 10);
            final Pattern pattern = new Pattern(imagePath
                    + "Media/Season_Txb.png").targetOffset(5, 15);
            util.clickInRegion(pattern);
            util.type(txt);
        }
    }

    public void enterEpisode(final String txt) {
        if (txt != null) {
            Utilities.logInfoMessage("Enter '" + txt + "' in the 'Episode #' text box");
            util.scrollDownForImgPresent(imagePath + "Media/Episode_Txb.png");
            final Pattern pattern = new Pattern(imagePath
                    + "Media/Episode_Txb.png").targetOffset(5, 15);
            util.clickInRegion(pattern);
            util.type(txt);
        }
    }

    public void enterFullEpisode(final String fullEpisode) {
        if (fullEpisode != null) {
            Utilities.logInfoMessage("Set 'Full episode' state to " + fullEpisode);
            util.scrollDownForImgPresent(imagePath + "Media/FullEpisode.png");
            util.scroll("Down", 25);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
            }
            final Pattern pattern = new Pattern(imagePath
                    + "Media/FullEpisode.png").targetOffset(-10, 0);
            util.clickInRegion(pattern);
        }
    }

    public void enterProgram(final String program) {
        if (program != null) {
            Utilities.logInfoMessage("Set 'Program' state to " + program);
            util.scrollDownForImgPresent(imagePath + "Media/Program_Txt.png");
            util.scroll("Down", 25);
            final Pattern pattern = new Pattern(imagePath
                    + "Media/Program_Txt.png").targetOffset(5, 15);
            util.clickInRegion(pattern);
            util.type(program);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
            }
            if (program.equals("face off")) {
                util.click(imagePath + "Media/Face_Program_option.png");
            } else {
                util.click(imagePath + "Media/CNBC_Program_option.png");
            }
        }
    }

    public void enterSeries(final String series) {
        if (series != null) {
            Utilities.logInfoMessage("Set 'Series' state to " + series);
            util.scrollDownForImgPresent(imagePath + "Media/Series_Txt.png");
            final Pattern seriesPattern = new Pattern(imagePath
                    + "Media/Series_Txt.png").targetOffset(5, 5);
            util.clickInRegion(seriesPattern);
            util.type(series);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
            }
            if (series.equals("face off")) {
                util.click(imagePath + "Media/Face_Series_option.png");
            } else {
                util.click(imagePath + "Media/CNBC_Series_option.png");
            }
        }
    }

    public void selectEntitlement(final String entitlement) {
        if (entitlement != null) {
            Utilities.logInfoMessage("Set 'Entitlement' state to " + entitlement);
            util.scrollDownForImgPresent(imagePath
                    + "Media/Entitlement_Txt.png");
            // util.scroll("Down", 10);
            Pattern pattern = new Pattern(imagePath
                    + "Media/Entitlement_Txt.png").targetOffset(5, 15);
            util.clickInRegion(pattern);

            if (entitlement.equalsIgnoreCase("auth")) {
                util.click(imagePath + "Media/EntitlementAuth_Value.png");
            }
            if (entitlement.equalsIgnoreCase("free")) {
                util.click(imagePath + "Media/free.png");
                /*
                 * pattern = new Pattern(imagePath + "Media/free.png");
                 * util.clickInRegion(pattern);
                 */
            }
        }
    }

    public void setTuneInTime(final String tuneIntime) {
        if (tuneIntime != null) {
            Utilities.logInfoMessage("Set 'Tune in Time' state to " + tuneIntime);
            util.scrollDownForImgPresent(imagePath + "Media/TuneInTime_Txt.png");
            final Pattern pattern = new Pattern(imagePath
                    + "Media/TuneInTime_Txt.png").targetOffset(5, 15);
            util.clickInRegion(pattern);
            util.type(tuneIntime);
        }
    }

    public void setShortDescription(final String shortDescription) {
        if (shortDescription != null) {
            Utilities.logInfoMessage("Set 'Short Description' state to " + shortDescription);
            util.scrollDownForImgPresent(imagePath
                    + "Media/ShortDescription_Txt.png");
            final Pattern pattern = new Pattern(imagePath
                    + "Media/ShortDescription_Txt.png").targetOffset(5, 15);
            util.clickInRegion(pattern);
            util.type(shortDescription);
        }
    }

    public void setProgrammingType(final String programmingType) {
        if (programmingType != null) {
            Utilities.logInfoMessage("Set 'Short Description' state to " + programmingType);
            util.scrollDownForImgPresent(imagePath
                    + "Media/ProgrammingType.png");
            util.scroll("Down", 3);
            Pattern pattern = new Pattern(imagePath
                    + "Media/ProgrammingType.png").targetOffset(5, 15);
            util.clickInRegion(pattern);
            try {
                Thread.sleep(10000);
            } catch (final InterruptedException e) {
                Utilities.logSevereMessage(e.getLocalizedMessage());
            }
            if (programmingType.equalsIgnoreCase("Commentary")) {
                util.click(imagePath + "Media/ProgrammingType_Commentary.png");
            }
            if (programmingType.equalsIgnoreCase("Concert")) {
                util.click(imagePath + "Media/ProgrammingType_Concert.png");
            }
        }
    }

    public void selectDayPart(final String dayPart) {
        if (dayPart != null) {
            Utilities.logInfoMessage("Set 'Day Part' state to " + dayPart);
            util.scrollDownForImgPresent(imagePath + "Media/DayPart_Txt.png");
            util.scroll("Down", 1);
            Pattern pattern = new Pattern(imagePath + "Media/DayPart_Txt.png")
                    .targetOffset(5, 15);
            util.clickInRegion(pattern);
            try {
                Thread.sleep(500);
            } catch (final InterruptedException e) {
                Utilities.logSevereMessage(e.getLocalizedMessage());
            }
            if (dayPart.equalsIgnoreCase("latenight")) {
                util.click(imagePath + "Media/Latenight_Option.png");
            }
            if (dayPart.equalsIgnoreCase("primetime")) {
                util.click(imagePath + "Media/PrimeTime_Option.png");
                /*
                 * pattern = new Pattern(imagePath + "Media/free.png");
                 * util.clickInRegion(pattern);
                 */
            }
        }

    }

    public void enterAdvertisingGenre(final String genre) {
        if (genre != null) {
            Utilities.logInfoMessage("Set 'Advertising Genre' to " + genre);
            util.scrollDownForImgPresent(imagePath
                    + "Media/AdvertisingGenre_Txt.png");
            final Pattern pattern = new Pattern(imagePath
                    + "Media/AdvertisingGenre_Txt.png").targetOffset(5, 15);
            util.clickInRegion(pattern);
            if (genre.equalsIgnoreCase("Animation")) {
                util.click(imagePath + "Media/Drama_Genre_Option.png");
            } else {
                util.click(imagePath + "Media/Comedy_Genre_Option.png");
            }
        }
    }

    public void setEntitlements(final String txt) {
        if (txt != null) {
            Utilities.logInfoMessage("Set data in the 'Entitlements' drop-down");
            util.scrollDownForImgPresent(imagePath
                    + "Media/ExpirationDate_Txb.png");
            final Pattern pattern = new Pattern(imagePath
                    + "Media/ExpirationDate_Txb.png").targetOffset(5, 15);
            util.clickInRegion(pattern);
            util.type(txt);
        }
    }

    public void clickSaveBtn(final Boolean withPause) {
        Utilities.logInfoMessage("Click the 'Save' button");
        util.waitForImgPresent(imagePath + "Common/Save_Btn.png");
        util.click(imagePath + "Common/Save_Btn.png");
        util.waitForImgNotPresent(imagePath + "Common/Spinner.png");
        if (withPause) {
            try {
                Thread.sleep(10000);
            } catch (final InterruptedException e) {
                Utilities.logSevereMessage(e.getLocalizedMessage());
            }
        }
    }

    public void setAirDate(final String text) {
        if (text != null) {
            Utilities.logInfoMessage("Get date in the 'Air Date' text box");
            util.scrollDownForImgPresent(imagePath + "Media/AirDate_Txb.png");
            util.scroll("Down", 7);
            final Region regionBelow = util.getRegionBelow(new Pattern(
                    imagePath + "Media/AirDate_Txb.png"), 11);
            util.clickInRegion(regionBelow);
            util.type(text);
        }
    }

    public void openPoliciesTab() {
        Utilities.logInfoMessage("Open 'Policies' tab");
        util.waitForImgPresent(imagePath + "Media/Policies_Tab.png");
        util.click(imagePath + "Media/Policies_Tab.png");
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            Utilities.logSevereMessage(e.getLocalizedMessage());
        }
    }

    public String getAvailableDate() {
        Utilities.logInfoMessage("Get date in the 'Available Date' text box");
        util.scrollDownForImgPresent(imagePath + "Media/AirDate_Txb.png");
        final Pattern pattern = new Pattern(imagePath + "Media/AirDate_Txb.png")
                .targetOffset(0, 10);
        util.clickInRegion(pattern);
        return util.getTextInRegion(pattern);
    }

    public void setAvailableDate(final String txt) {
        if (txt != null) {
            Utilities.logInfoMessage("Get date in the 'Available Date' text box");
            util.scrollDownForImgPresent(imagePath
                    + "Media/AvailableDate_Txb.png");
            try {
                Thread.sleep(10000);
            } catch (final InterruptedException e) {
                Utilities.logSevereMessage(e.getLocalizedMessage());
            }

            final Pattern pattern = new Pattern(imagePath
                    + "Media/AvailableDate_Txb.png").targetOffset(5, 15);
            util.clickInRegion(pattern);
            util.type(txt);
        }
    }

    public String getExpirationDate() {
        Utilities.logInfoMessage("Get date in the 'Expiration Date' text box");
        util.scrollDownForImgPresent(imagePath + "Media/ExpirationDate_Txb.png");
        final Pattern pattern = new Pattern(imagePath
                + "Media/ExpirationDate_Txb.png").targetOffset(0, 10);
        util.clickInRegion(pattern);
        return util.getTextInRegion(pattern);
    }

    public void setExpirationDate(final String txt) {
        if (txt != null) {
            Utilities.logInfoMessage("Get date in the 'Expiration Date' text box");
            util.scrollDownForImgPresent(imagePath
                    + "Media/ExpirationDate_Txb.png");
            final Pattern pattern = new Pattern(imagePath
                    + "Media/ExpirationDate_Txb.png").targetOffset(5, 15);
            util.clickInRegion(pattern);
            util.type(txt);
        }
    }

    public boolean isFullEpisode() {
        Utilities.logInfoMessage("Is this Full episode");
        util.scrollDownForImgPresent(imagePath + "Media/FullEpisode.png");
        util.scroll("Down", 10);
        final boolean isFullEpisode = util.isImagePresent(imagePath
                + "Media/FullEpisode_On_Cbx.png");
        System.out.println("!!!!Is full : " + isFullEpisode);
        return isFullEpisode;
    }

    public boolean isFreeEpisode() {
        Utilities.logInfoMessage("Is this Episode has Free entitlement");
        util.scrollDownForImgPresent(imagePath + "Media/Entitlement_Txt.png");
        util.scroll("Down", 10);
        final boolean isFreeEpisode = util.isImagePresent(imagePath
                + "Media/Entitlement_Free_Slct.png");
        System.out.println("!!!Is free : " + isFreeEpisode);
        return isFreeEpisode;
    }

    public void openHistoryTab() {
        Utilities.logInfoMessage("Open 'History' tab");
        util.waitForImgPresent(imagePath + "Media/History_Tab.png");
        util.click(imagePath + "Media/History_Tab.png");
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            Utilities.logSevereMessage(e.getLocalizedMessage());
        }
    }

    public MPXFilesTab openFilesArea() {
        Utilities.logInfoMessage("Open 'Files' area");
        util.click(imagePath + "Common/Files.png");
        try {
            Thread.sleep(2000);
        } catch (final InterruptedException e) {
            Utilities.logSevereMessage(e.getLocalizedMessage());
        }
        return new MPXFilesTab();
    }

    public void setExternalId(String externalId) {
        Utilities.logInfoMessage("Set external ID field to " + externalId);
        util.scrollDownForImgPresent(imagePath + "Media/ExternalID_Txt.png");
        final Pattern pattern = new Pattern(imagePath
                + "Media/ExternalID_Txt.png").targetOffset(5, 15);
        util.clickInRegion(pattern);
        util.type(externalId);
    }
}