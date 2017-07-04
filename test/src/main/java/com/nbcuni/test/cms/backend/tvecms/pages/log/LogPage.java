package com.nbcuni.test.cms.backend.tvecms.pages.log;

import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.pageobjectutils.tvecms.LogResponseInfo;
import com.nbcuni.test.cms.pageobjectutils.tvecms.LogType;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.ActionsUtil;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LogPage extends Page {

    public static final String SELECT_TYPE_XPATH = ".//*[@id='edit-type']";
    public static final String FILTER_BUTTON_XPATH = ".//*[@id='edit-submit']";
    public static final String RESET_BUTTON_XPATH = ".//*[@id='edit-reset']";
    public static final String TABLE_XPATH = ".//*[@id='admin-dblog']";
    public static final String TYPE_COLUMN_XPATH = "./td[@class='type']";
    public static final String DATE_COLUMN_XPATH = "./td[@class='date']";
    public static final String MESSAGE_COLUMN_XPATH = "./td[4]";
    public static final String USER_COLUMN_XPATH = "./td[5]";
    public static final String FILTER_SECTION_XPATH = ".//*[@id='edit-filters']";
    @FindBy(xpath = ".//*[@id='admin-dblog']")
    private Table table;
    private WebDriverUtil util;

    public LogPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
        util = WebDriverUtil.getInstance(webDriver);
        expandFilterSection();
    }

    public void expandFilterSection() {
        final String attribute = webDriver.getAttribute(FILTER_SECTION_XPATH, HtmlAttributes.CLASS.get());
        if (attribute.contains("collapsed")) {
            WebElement section = webDriver.findElement(By.xpath(FILTER_SECTION_XPATH));
            section.findElement(By.xpath(".//a")).click();
        }
    }

    public void selectType(LogType... type) {
        List<String> types = new ArrayList<>();
        for (LogType t : type) {
            types.add(t.getName());
        }
        Utilities.logInfoMessage("Select filter " + type);
        ActionsUtil.perform(webDriver).selectFromMultiselect(SELECT_TYPE_XPATH, types);
    }

    public void clickFilter() {
        webDriver.click(FILTER_BUTTON_XPATH);
    }

    public List<String> getListMessages() {
        return table.getValuesFromColumn(4);
    }

    public List<String> getListMessages(String title) {
        List<String> messages = new ArrayList<>();
        List<String> allMessages = table.getValuesFromColumn(4);
        for (String m : allMessages) {
            if (m.contains(title)) {
                messages.add(m);
            }
        }
        return messages;

    }

    public MessageParser getInstance(String message) {
        return new MessageParser(message);
    }

    public MessageParser getInstance(List<String> message) {
        return new MessageParser(message);
    }

    public String getDate(int line) {
        return table.getCellTextByRowIndex(line, 3);
    }

    public String getAuthor(int line) {
        return table.getCellTextByRowIndex(line, 5);
    }

    public String getType(int line) {
        return table.getCellTextByRowIndex(line, 2);
    }

    @Override
    public List<String> verifyPage() {
        Utilities.logInfoMessage("Verify page " + getPageUrl() + " for missed elements");
        final ArrayList<String> missedElements = new ArrayList<String>();
        final Class<?> thisClass = this.getClass();
        final Field[] fields = thisClass.getDeclaredFields();
        for (final Field field : fields) {
            try {
                if (field.getType().equals(String.class) && field.getName().contains("_XPATH")) {
                    final String fieldLocator = field.get(this).toString();
                    if (!webDriver.isVisible(String.format(fieldLocator))) {
                        missedElements.add("Element:  " + field.getName() + " , Locator: " + fieldLocator);
                        missedElements.trimToSize();
                    }
                }
            } catch (final IllegalArgumentException e) {
                Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
            } catch (final IllegalAccessException e) {
                Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
            }
        }
        return missedElements;
    }

    public class MessageParser {
        private Map<LogResponseInfo, String> response = new HashMap<>();
        private String MESSAGE = null;
        private List<String> MESSAGES;

        public MessageParser(String message) {
            setResponse(message);
        }

        public MessageParser(List<String> messages) {
            setResponse(messages);
        }

        public Map<LogResponseInfo, String> getResponse() {
            Utilities.logInfoMessage("Parse Message in Response");
            String[] messages = MESSAGE.split("\\n");
            for (String line : messages) {
                if (line.contains(LogResponseInfo.REQUEST_STATUS.getName())) {
                    line = line.substring(line.indexOf(":") + 1);
                    response.put(LogResponseInfo.REQUEST_STATUS, line);
                } else if (line.contains(LogResponseInfo.HTTP_CODE.getName())) {
                    line = line.substring(line.indexOf(":") + 1);
                    response.put(LogResponseInfo.HTTP_CODE, line);
                } else if (line.contains(LogResponseInfo.ERROR_MESSAGE.getName())) {
                    line = line.substring(line.indexOf(":") + 1);
                    response.put(LogResponseInfo.ERROR_MESSAGE, line);
                } else if (line.contains(LogResponseInfo.STATUS_MESSAGE.getName())) {
                    line = line.substring(line.indexOf(":") + 1);
                    response.put(LogResponseInfo.STATUS_MESSAGE, line);
                } else if (line.contains(LogResponseInfo.RESPONSE_DATA.getName())) {
                    line = line.substring(line.indexOf(":") + 1);
                    response.put(LogResponseInfo.RESPONSE_DATA, line);
                } else response.put(LogResponseInfo.MESSAGE_RESPONSE, line);
            }

            return response;
        }

        private void setResponse(List<String> messages) {
            this.MESSAGES = messages;
        }

        private void setResponse(String message) {
            this.MESSAGE = message;
        }
    }
}
