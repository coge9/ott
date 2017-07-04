package com.nbcuni.test.cms.utils;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Chars;
import com.nbcuni.test.webdriver.Utilities;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.TestException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleUtils {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String VOWELS = "AEIOUY";
    private static final String SPL_CHARS = "!@#$%^&*_=+-/:;?±§<>~`";
    private static Random rnd = new Random();

    public static String getRandomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static String getRandomVowel() {
        StringBuilder sb = new StringBuilder();
        sb.append(VOWELS.charAt(rnd.nextInt(VOWELS.length())));
        return sb.toString();
    }

    public static String getRandomStringWithRandomLength(int maxLength) {
        int len = rnd.nextInt(maxLength + 3);
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    /**
     * Returns a random number between min and max, inclusive.
     * @param minValue Minimum value
     * @param maxValue Maximum value
     * @return Integer between min and max, inclusive.
     */
    public static int randomNumber(int minValue, int maxValue) {
        Random randomValue = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return randomValue.nextInt((maxValue - minValue) + 1) + minValue;
    }

    public static Integer getRandomInt(int len) {
        return rnd.nextInt(len);
    }

    public static Integer getRandomIntInRange(int min, int max) {
        return rnd.nextInt(max - min) + min;
    }

    public static Integer getRandomInRangeWithLength(int len, int min, int max) {
        int value = 0;
        if (len < max) {
            max = len;
        }
        return value = getRandomIntInRange(min, max);
    }

    public static Boolean getRandomBoolean() {
        return rnd.nextBoolean();
    }

    public static String getRandomCaseSensitiveString(String word) {

        StringBuilder sb = new StringBuilder(word.length());
        boolean apperCase;
        String[] result = word.split("");
        for (int i = 0; i <= word.length(); i++) {
            apperCase = rnd.nextBoolean();
            if (apperCase) {
                sb.append(result[i].toUpperCase());
            } else {
                sb.append(result[i].toLowerCase());
            }
        }
        return sb.toString();
    }

    public static String encodeStringToHTML(String parametr) {
        String encodedParameter = null;
        try {
            encodedParameter = URLEncoder.encode(parametr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Utilities.logSevereMessage("Unable to encode String");
            throw new TestException("String " + parametr + " can't be encoded");
        }
        return encodedParameter;
    }

    public static String convertToSha256(String value) {
        return Hashing.sha256().hashString(value, Charsets.UTF_8).toString();
    }

    public static String getMd5(String value) {
        return Hashing.md5().hashString(value, Charsets.UTF_8).toString();
    }

    public static String getImagePath(String name) {
        Utilities.logInfoMessage("Get File of image");
        File image = new File(Config.getInstance().getPathToImages() + name);
        return image.getAbsolutePath();
    }

    public static String getTextFromUrl(String url) {
        URL website;
        StringBuilder response = new StringBuilder();
        try {
            website = new URL(url);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();
        } catch (MalformedURLException e) {
            Utilities.logInfoMessage("Wrong URL recieved");
            return null;
        } catch (IOException e) {
            Utilities.logSevereMessage("Error during getting text from URL");
            return null;
        }
        return response.toString().trim();
    }

    public static String getCharactersFromString(String line) {

        StringBuilder numbers = new StringBuilder();
        Pattern p = Pattern.compile("\\D+");
        Matcher m = p.matcher(line);
        while (m.find()) {
            numbers.append(m.group());
        }
        return numbers.toString();
    }

    public static String getNumbersFromString(String line) {
        StringBuilder numbers = new StringBuilder();
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(line);
        while (m.find()) {
            numbers.append(m.group());
        }

        return numbers.toString();
    }

    public static String getTextFromUrl(String url, String userAgent) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).header("Content-Type", "text/html;charset=utf-8")
                    .userAgent(userAgent).get();
            doc = doc.normalise();
        } catch (IOException e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
            e.printStackTrace();
        }

        return doc.html();
    }

    public static String getSpecialCharacters(final int length) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < length; i++) {
            b.append(SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length())));
        }
        return b.toString();
    }

    public static String getWithoutSpecialCharacters(String line) {
        List<Character> specHaracters = Chars.asList(SPL_CHARS.toCharArray());
        StringBuilder b = new StringBuilder();
        char[] symbols = line.toCharArray();
        for (char symbol : symbols)
            if (!specHaracters.contains(symbol)) {
                b.append(symbol);
            }
        return b.toString();
    }

    public static String replaceSpecialCharactersByUndercore(String line) {
        List<Character> specHaracters = Chars.asList(SPL_CHARS.toCharArray());
        StringBuilder b = new StringBuilder();
        char[] symbols = line.toCharArray();
        for (char symbol : symbols)
            if (!specHaracters.contains(symbol)) {
                b.append(symbol);
            } else {
                b.append("_");
            }
        return b.toString();
    }

    public static String cutSpecialCharacters(String line) {
        List<Character> specHaracters = Chars.asList(SPL_CHARS.toCharArray());
        StringBuilder b = new StringBuilder();
        char[] symbols = line.toCharArray();
        for (char symbol : symbols)
            if (!specHaracters.contains(symbol)) {
                b.append(symbol);
            } else {
                b.append("");
            }
        return b.toString();
    }

    public static List<String> getBigListStringsWitchNotExistInSmallList(List<String> bigList, List<String> smallList) {
        List<String> differences = new ArrayList<>();
        Boolean contains = false;
        for (String longValue : bigList) {
            contains = false;
            for (String shortValue : smallList) {
                if (longValue.toUpperCase().contains(shortValue.toUpperCase())) {
                    contains = true;
                }
            }
            if (!contains) {
                differences.add(longValue);
            }
        }
        return differences;
    }
}
