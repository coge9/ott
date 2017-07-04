package com.nbcuni.test.cms.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import java.util.*;

/**
 * Created by Ivan_Karnilau on 29-Jul-16.
 */
public class CustomSelect {
    private final WebElement element;
    private final boolean isMulti;

    public CustomSelect(WebElement element) {
        String tagName = element.getTagName();
        if (null != tagName && "select".equals(tagName.toLowerCase())) {
            this.element = element;
            String value = element.getAttribute("multiple");
            this.isMulti = value != null && !"false".equals(value);
        } else if (null != tagName && "optgroup".equals(tagName.toLowerCase())) {
            this.element = element;
            String value = element.findElement(By.xpath(".//ancestor::select")).getAttribute("multiple");
            this.isMulti = value != null && !"false".equals(value);
        } else {
            throw new UnexpectedTagNameException("select or optgroup", tagName);
        }
    }

    public boolean isMultiple() {
        return this.isMulti;
    }

    public List<WebElement> getOptions() {
        return this.element.findElements(By.tagName("option"));
    }

    public List<WebElement> getAllSelectedOptions() {
        ArrayList toReturn = new ArrayList();
        Iterator var2 = this.getOptions().iterator();

        while (var2.hasNext()) {
            WebElement option = (WebElement) var2.next();
            if (option.isSelected()) {
                toReturn.add(option);
            }
        }

        return toReturn;
    }

    public WebElement getFirstSelectedOption() {
        Iterator var1 = this.getOptions().iterator();

        WebElement option;
        do {
            if (!var1.hasNext()) {
                throw new NoSuchElementException("No options are selected");
            }

            option = (WebElement) var1.next();
        } while (!option.isSelected());

        return option;
    }

    public void selectByVisibleText(String text) {
        List options = this.element.findElements(By.xpath(".//option[normalize-space(.) = " + Quotes.escape(text) + "]"));
        boolean matched = false;

        for (Iterator subStringWithoutSpace = options.iterator(); subStringWithoutSpace.hasNext(); matched = true) {
            WebElement candidates = (WebElement) subStringWithoutSpace.next();
            this.setSelected(candidates, true);
            if (!this.isMultiple()) {
                return;
            }
        }

        if (options.isEmpty() && text.contains(" ")) {
            String subStringWithoutSpace1 = this.getLongestSubstringWithoutSpace(text);
            List candidates1;
            if ("".equals(subStringWithoutSpace1)) {
                candidates1 = this.element.findElements(By.tagName("option"));
            } else {
                candidates1 = this.element.findElements(By.xpath(".//option[contains(., " + Quotes.escape(subStringWithoutSpace1) + ")]"));
            }

            Iterator var6 = candidates1.iterator();

            while (var6.hasNext()) {
                WebElement option = (WebElement) var6.next();
                if (text.equals(option.getText())) {
                    this.setSelected(option, true);
                    if (!this.isMultiple()) {
                        return;
                    }

                    matched = true;
                }
            }
        }

        if (!matched) {
            throw new NoSuchElementException("Cannot locate element with text: " + text);
        }
    }

    private String getLongestSubstringWithoutSpace(String s) {
        String result = "";
        StringTokenizer st = new StringTokenizer(s, " ");

        while (st.hasMoreTokens()) {
            String t = st.nextToken();
            if (t.length() > result.length()) {
                result = t;
            }
        }

        return result;
    }

    public void selectByIndex(int index) {
        String match = String.valueOf(index);
        Iterator var3 = this.getOptions().iterator();

        WebElement option;
        do {
            if (!var3.hasNext()) {
                throw new NoSuchElementException("Cannot locate option with index: " + index);
            }

            option = (WebElement) var3.next();
        } while (!match.equals(option.getAttribute("index")));

        this.setSelected(option, true);
    }

    public void selectByValue(String value) {
        List options = this.element.findElements(By.xpath(".//option[@value = " + Quotes.escape(value) + "]"));
        boolean matched = false;

        for (Iterator var4 = options.iterator(); var4.hasNext(); matched = true) {
            WebElement option = (WebElement) var4.next();
            this.setSelected(option, true);
            if (!this.isMultiple()) {
                return;
            }
        }

        if (!matched) {
            throw new NoSuchElementException("Cannot locate option with value: " + value);
        }
    }

    public void deselectAll() {
        if (!this.isMultiple()) {
            throw new UnsupportedOperationException("You may only deselect all options of a multi-select");
        } else {
            Iterator var1 = this.getOptions().iterator();

            while (var1.hasNext()) {
                WebElement option = (WebElement) var1.next();
                this.setSelected(option, false);
            }

        }
    }

    public void deselectByValue(String value) {
        if (!this.isMultiple()) {
            throw new UnsupportedOperationException("You may only deselect options of a multi-select");
        } else {
            List options = this.element.findElements(By.xpath(".//option[@value = " + Quotes.escape(value) + "]"));
            boolean matched = false;

            for (Iterator var4 = options.iterator(); var4.hasNext(); matched = true) {
                WebElement option = (WebElement) var4.next();
                this.setSelected(option, false);
            }

            if (!matched) {
                throw new NoSuchElementException("Cannot locate option with value: " + value);
            }
        }
    }

    public void deselectByIndex(int index) {
        if (!this.isMultiple()) {
            throw new UnsupportedOperationException("You may only deselect options of a multi-select");
        } else {
            String match = String.valueOf(index);
            Iterator var3 = this.getOptions().iterator();

            WebElement option;
            do {
                if (!var3.hasNext()) {
                    throw new NoSuchElementException("Cannot locate option with index: " + index);
                }

                option = (WebElement) var3.next();
            } while (!match.equals(option.getAttribute("index")));

            this.setSelected(option, false);
        }
    }

    public void deselectByVisibleText(String text) {
        if (!this.isMultiple()) {
            throw new UnsupportedOperationException("You may only deselect options of a multi-select");
        } else {
            List options = this.element.findElements(By.xpath(".//option[normalize-space(.) = " + Quotes.escape(text) + "]"));
            boolean matched = false;

            for (Iterator var4 = options.iterator(); var4.hasNext(); matched = true) {
                WebElement option = (WebElement) var4.next();
                this.setSelected(option, false);
            }

            if (!matched) {
                throw new NoSuchElementException("Cannot locate element with text: " + text);
            }
        }
    }

    public List<String> getAvailableOptions() {
        List<String> values = new LinkedList<String>();
        for (WebElement option : this.getOptions()) {
            values.add(option.getText());
        }
        return values;
    }

    private void setSelected(WebElement option, boolean select) {
        boolean isSelected = option.isSelected();
        if (!isSelected && select || isSelected && !select) {
            option.click();
        }

    }
}
