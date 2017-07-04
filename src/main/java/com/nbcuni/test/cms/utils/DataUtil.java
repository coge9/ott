package com.nbcuni.test.cms.utils;

import com.nbcuni.test.cms.pageobjectutils.entities.content.ContentFormat;
import com.nbcuni.test.cms.pageobjectutils.tve.BrandNames;
import com.nbcuni.test.webdriver.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataUtil {
    public static final SimpleDateFormat MM_DD_YYYY = new SimpleDateFormat("MM/dd/yyyy");
    public static final SimpleDateFormat MM_YYYY = new SimpleDateFormat("MM/yyyy");

    public static String convertDate(final String date, final int field, final int amount) {
        try {
            return convertDate(MM_DD_YYYY.parse(date), field, amount);
        } catch (final ParseException e) {
            Utilities.logSevereMessage(e.getLocalizedMessage());
        }
        return "";
    }

    public static String convertDate(final Date date, final int field, final int amount) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        return MM_DD_YYYY.format(cal.getTime());
    }

    public static String convertDate(final Date date, final int field, final int amount, String format) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        SimpleDateFormat MMM_dd_YYYY = new SimpleDateFormat(format, Locale.ENGLISH);
        return MMM_dd_YYYY.format(cal.getTime());
    }

    public static Calendar convertStringToCalendar(String dateS, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format, Locale.ENGLISH).parse(dateS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTime(date);
        return cal;
    }

    public static String convertStringFormatToStringFormat(String dateS, String templateFrom, String templateTo) {
        String dateString = null;
        try {
            Date date = new SimpleDateFormat(templateFrom).parse(dateS);

            dateString = new SimpleDateFormat(templateTo).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static Calendar convertDateToCalendar(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Map<String, String> updateMPXEpisodeData(final Map<String, String> oldMPXData, final String newTitle) {
        final Map<String, String> newMPXData = new HashMap<String, String>();
        String newMPXEntitlements = null;
        newMPXData.put("Title", newTitle);
        newMPXData.put("Description", "Description of " + newTitle);
        if (oldMPXData.get("Episode #").isEmpty()) {
            newMPXData.put("Episode #", String.valueOf(1));
        } else {
            newMPXData.put("Episode #", String.valueOf((Integer.parseInt(oldMPXData.get("Episode #")) + 1)));
        }
        if (oldMPXData.get("Categories").equalsIgnoreCase("The Face")) {
            newMPXData.put("Categories", "Glee");
        } else {
            newMPXData.put("Categories", "The Face");
        }
        newMPXData.put("FullEpisode",
                String.valueOf(!Boolean.parseBoolean(oldMPXData.get("FullEpisode").toLowerCase())));
        if (oldMPXData.get("Entitlement").equals("auth")) {
            newMPXData.put("Entitlement", "free");
        }
        if (oldMPXData.get("Entitlement").equals("free")) {
            newMPXData.put("Entitlement", "auth");
        }

     /*   if (oldMPXData.get("Program").equals(ProgramSeriesType.FACE_OFF.get())) {
            newMPXData.put("Program", "60 Minutes on CNBC");
        } else {
            newMPXData.put("Program", "face off");
        }

        if (oldMPXData.get("Series").equals(ProgramSeriesType.FACE_OFF.get())) {
            newMPXData.put("Series", "60 Minutes on CNBC");
        }else{
            newMPXData.put("Series", "face off");
        }*/
        // newMPXData.put("Entitlement", "auth");
        if (oldMPXData.get("Airdate").isEmpty()) {
            oldMPXData.put("Airdate", "03/31/2015");
        }
        if (oldMPXData.get("Available date").isEmpty()) {
            oldMPXData.put("Available date", "03/31/2015");
        }
        if (oldMPXData.get("Expiration date").isEmpty()) {
            oldMPXData.put("Expiration date", "04/31/2015");
        }
        newMPXData.put("Airdate", convertDate(oldMPXData.get("Airdate"), Calendar.MONTH, -1));
        newMPXData.put("Available date", convertDate(oldMPXData.get("Available date"), Calendar.MONTH, -1));
        newMPXData.put("Expiration date", convertDate(oldMPXData.get("Expiration date"), Calendar.MONTH, 1));
        newMPXData.put("Show name", oldMPXData.get("Show name"));

        if (oldMPXData.get("Day Part").equalsIgnoreCase("Primetime")) {
            newMPXData.put("Day Part", "Latenight");
        } else {
            newMPXData.put("Day Part", "Primetime");
        }

        if (oldMPXData.get("Advertising genre").equalsIgnoreCase("Animation")) {
            newMPXData.put("Advertising genre", "Action and Adventure");
        } else {
            newMPXData.put("Advertising genre", "Animation");
        }
        if (oldMPXData.get("Season").isEmpty()) {
            newMPXData.put("Season", String.valueOf(1));
        } else {
            newMPXData.put("Season", String.valueOf((Integer.parseInt(oldMPXData.get("Season")) + 1)));
        }
        return newMPXData;
    }

    public static Map<String, String> updateMPXEpisodeDataRoku(final Map<String, String> oldMPXData, final String newTitle) {
        final Map<String, String> newMPXData = new HashMap<String, String>();
        String newMPXEntitlements = null;
        newMPXData.put("Title", newTitle);
        newMPXData.put("Short Description", "Short Description of " + newTitle);
        if (oldMPXData.get("Advertising genre").equalsIgnoreCase("Animation")) {
            newMPXData.put("Advertising genre", "Action and Adventure");
        } else {
            newMPXData.put("Advertising genre", "Animation");
        }
        if (oldMPXData.get("Episode #").isEmpty()) {
            newMPXData.put("Episode #", String.valueOf(1));
        } else {
            newMPXData.put("Episode #", String.valueOf((Integer.parseInt(oldMPXData.get("Episode #")) + 1)));
        }
        if (oldMPXData.get("Season #").isEmpty()) {
            newMPXData.put("Season #", String.valueOf(1));
        } else {
            newMPXData.put("Season #", String.valueOf((Integer.parseInt(oldMPXData.get("Season #")) + 1)));
        }
        newMPXData.put("TuneInTime", "Tune In Time of " + newTitle);
        if (oldMPXData.get("Entitlement").equals("auth")) {
            newMPXData.put("Entitlement", "free");
        } else {
            newMPXData.put("Entitlement", "auth");
        }

        newMPXData.put("ExternalID", oldMPXData.get("ExternalID"));
        if (oldMPXData.get("FullEpisode").equalsIgnoreCase("0")) {
            newMPXData.put("FullEpisode", "1");
        } else {
            newMPXData.put("FullEpisode", "0");
        }
        if (oldMPXData.get("ProgrammingType").equalsIgnoreCase("Commentary")) {
            newMPXData.put("ProgrammingType", "Concert");
        } else {
            newMPXData.put("ProgrammingType", "Commentary");
        }
        if (oldMPXData.get("Day Part").equalsIgnoreCase("Primetime")) {
            newMPXData.put("Day Part", "Latenight");
        } else {
            newMPXData.put("Day Part", "Primetime");
        }
        if (oldMPXData.get("Series").equalsIgnoreCase("Suits")) {
            newMPXData.put("Series", "Royal Pains");
        } else {
            newMPXData.put("Series", "Suits");
        }

        if (oldMPXData.get("Categories").equalsIgnoreCase("Series/Suits")) {
            newMPXData.put("Categories", "Series/Royal Pains");
        } else {
            newMPXData.put("Categories", "Series/Suits");
        }
        newMPXData.put("VideoLength", oldMPXData.get("VideoLength"));
        newMPXData.put("ID", oldMPXData.get("ID"));
        newMPXData.put("GUID", oldMPXData.get("GUID"));
        newMPXData.put("Description", "Description of " + newTitle);
        if (oldMPXData.get("Airdate").isEmpty()) {
            oldMPXData.put("Airdate", "03/31/2016");
        }
        if (oldMPXData.get("Available date").isEmpty()) {
            oldMPXData.put("Available date", "03/31/2016");
        }
        if (oldMPXData.get("Expiration date").isEmpty()) {
            oldMPXData.put("Expiration date", "04/31/2016");
        }
        newMPXData.put("Airdate", convertDate(oldMPXData.get("Airdate"), Calendar.MONTH, -1));
        newMPXData.put("Available date", convertDate(oldMPXData.get("Available date"), Calendar.MONTH, -1));
        newMPXData.put("Expiration date", convertDate(oldMPXData.get("Expiration date"), Calendar.YEAR, 1));
        return newMPXData;
    }

    public static Map<String, String> createMPXEpisodeData(final String newTitle) {
        Random rand = new Random();
        int wholePart = rand.nextInt(10) + 1;
        final Map<String, String> newMPXData = new HashMap<String, String>();
        String newMPXEntitlements = null;
        final String newDescription = SimpleUtils.getRandomString(10);
        newMPXData.put("Title", newTitle);
        newMPXData.put("Description", "Description of " + newTitle + newDescription);
        newMPXData.put("Episode #", String.valueOf(wholePart));
        newMPXData.put("Categories", "Glee");
        newMPXData.put("FullEpisode",
                String.valueOf(true));
        newMPXData.put("Entitlement", "auth");
        newMPXData.put("Airdate", convertDate("08/13/2013", Calendar.MONTH, -1));
        newMPXData.put("Available date", convertDate("08/13/2013", Calendar.MONTH, -1));
        newMPXData.put("Expiration date", convertDate("07/12/2025", Calendar.MONTH, 1));
        // newMPXData.put("Show name", "");
        //newMPXData.put("Advertising genre", "Animation");
        newMPXData.put("Advertising genre", "Action and Adventure");
        newMPXData.put("Season", String.valueOf(wholePart + 1));
        return newMPXData;
    }

    public static String convertDateToMonthYearFormat(final String date) {
        try {
            return MM_YYYY.format(MM_DD_YYYY.parse(date));
        } catch (final ParseException e) {
            Utilities.logSevereMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public static String convertPreviewUrl(String episodePostAirFullEpisode, String postAirFullEpisodeID, String... brand) {
        episodePostAirFullEpisode = episodePostAirFullEpisode.replaceAll("((?=\\w*\\S)\\W)", "");
        episodePostAirFullEpisode = episodePostAirFullEpisode.replace("\\s?", " ");
        String previewUrl = "/full-episode/" + episodePostAirFullEpisode.toLowerCase().replace(" ", "-") + "/" + postAirFullEpisodeID;
        if (brand.length != 0 && (brand[0].equals(BrandNames.TELEMUNDO.getBrandName()) || brand[0].equals(BrandNames.NBCUNIVERSO.getBrandName()))) {
            previewUrl = previewUrl.replace("/full-episode/", "/full-episodes/");
        }
        return previewUrl;
    }

    public static String convertPreviewUrl(String episodePostAirFullEpisode, String postAirFullEpisodeID, ContentFormat format, String... brand) {
        episodePostAirFullEpisode = episodePostAirFullEpisode.replaceAll("((?=\\w*\\S)\\W)", "");
        episodePostAirFullEpisode = episodePostAirFullEpisode.replace("\\s?", " ");
        String previewUrl = null;
        switch (format) {
            case FULLEPISODIC:
                previewUrl = "/full-episode/" + episodePostAirFullEpisode.toLowerCase().replace(" ", "-") + "/" + postAirFullEpisodeID;
                if (brand.length != 0 && (brand[0].equals(BrandNames.TELEMUNDO.getBrandName()) || brand[0].equals(BrandNames.NBCUNIVERSO.getBrandName()))) {
                    previewUrl = previewUrl.replace("/full-episode/", "/full-episodes/");
                }
                break;
            case SHORTFORM:
                previewUrl = "/clip/" + episodePostAirFullEpisode.toLowerCase().replace(" ", "-") + "/" + postAirFullEpisodeID;
        }
        return previewUrl;
    }

    public static String convertShowUrl(String showPostAir, String... brand) {
        showPostAir = showPostAir.replaceAll("((?=\\w*\\S)\\W)", "");
        showPostAir = showPostAir.replace("\\s?", " ");
        String showURL = "/" + showPostAir.toLowerCase().replaceAll(" ", "-");

        if (brand.length != 0 && (brand[0].equals(BrandNames.TELEMUNDO.getBrandName()) || brand[0].equals(BrandNames.NBCUNIVERSO.getBrandName()))) {
            showURL = showURL.replace(showURL, "/en" + showURL);
        }
        return showURL;
    }

    public static String[] splitByWhiteSpace(String word) {
        String[] massive = word.split("\\s+");
        return massive;
    }

}
