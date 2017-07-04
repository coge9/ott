package com.nbcuni.test.cms.backend.tvecms.block.content;

import com.nbcuni.test.cms.backend.tvecms.block.BaseTabBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Promotional;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.elements.*;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Genre;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Status;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.utils.RegexUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Objects;


/**
 * Created by Ivan_Karnilau on 24-Jun-16.
 */
public class BasicMediaBlock extends BaseTabBlock {

    @FindBy(xpath = ".//*[@id='edit-title']")
    private TextField title;

    @FindBy(xpath = ".//*[contains(@id,'ft-carousel-cta-und-0-value')]")
    private TextField featureCarouselCta;

    @FindBy(xpath = ".//h1[contains(@class,'page-title')]")
    private Label pageTitle;

    @FindBy(xpath = ".//*[@id='edit-field-program-hero-cta-und-0-value']")
    private TextField showPageCta;

    @FindBy(xpath = ".//input[contains(@id,'ft-carousel-h')]")
    private TextField featureCarouselHeadline;

    @FindBy(xpath = ".//*[contains(@id,'template')]")
    private RadioButtonsGroup templateStyle;

    @FindBy(xpath = ".//input[contains(@id,'edit-field-subhead')]")
    private TextField subhead;

    @FindBy(xpath = ".//textarea[contains(@id,'edit-field-long-description')]")
    private TextField longDescription;

    @FindBy(id = "edit_field_genre_und_chosen")
    private MultiSelect genre;

    @FindBy(id = "edit-field-program-status-und")
    private DropDownList status;

    @FindBy(xpath = ".//*[@id='field-show-color-add-more-wrapper']//div[contains(@class,'sp-preview-inner')]")
    private WebElement showColor;

    public TextField getTitle() {
        return title;
    }

    public TextField getFeatureCarouselCta() {
        return featureCarouselCta;
    }

    public BasicMediaBlock setFeatureCarouselCta(String featureCarouselCta) {
        this.featureCarouselCta.enterText(featureCarouselCta);
        return this;
    }

    public Label getPageTitle() {
        return pageTitle;
    }

    public TextField getShowPageCta() {
        return showPageCta;
    }

    public TextField getFeatureCarouselHeadline() {
        return featureCarouselHeadline;
    }

    public BasicMediaBlock setFeatureCarouselHeadline(String featureCarouselHeadline) {
        this.featureCarouselHeadline.enterText(featureCarouselHeadline);
        return this;
    }

    public RadioButtonsGroup getTemplateStyle() {
        return templateStyle;
    }

    public TextField getSubhead() {
        return subhead;
    }

    public BasicMediaBlock setSubhead(String subhead) {
        this.subhead.enterText(subhead);
        return this;
    }

    public TextField getLongDescription() {
        return longDescription;
    }

    public BasicMediaBlock setLongDescription(String longDescription) {
        this.longDescription.enterText(longDescription);
        return this;
    }

    public MultiSelect getGenre() {
        return genre;
    }

    public BasicMediaBlock setGenre(String genreValue) {
        genre.clearSelection();
        genre.select(genreValue);
        return this;
    }

    public DropDownList getStatus() {
        return status;
    }

    public BasicMediaBlock setStatus(String status) {
        this.status.selectFromDropDown(status);
        return this;
    }

    public BasicMediaBlock fillValuesForProgram(Series series) {
        setFeatureCarouselCta(series.getPromotional().getPromotionalKicker());
        setFeatureCarouselHeadline(series.getPromotional().getPromotionalTitle());
        setGenre(series.getSeriesInfo().getGenre().getValue());
        setStatus(series.getSeriesInfo().getStatus().getValue());
        setSubhead(series.getGeneralInfo().getSubhead());
        setLongDescription(series.getGeneralInfo().getLongDescription());

        return this;
    }

    public Promotional getPromotional() {
        Promotional promotional = new Promotional();
        promotional.setPromotionalKicker(getFeatureCarouselCta().getValue().isEmpty() ? null :
                getFeatureCarouselCta().getValue());
        promotional.setPromotionalTitle(getFeatureCarouselHeadline().getValue().isEmpty() ? null :
                getFeatureCarouselHeadline().getValue());
        promotional.setPromotionalDescription(null);
        return promotional;
    }

    public Series getData() {
        Series series = new Series();
        series.getGeneralInfo().setTitle(title.getValue());
        series.setPromotional(getPromotional());
        series.getGeneralInfo().setSubhead(subhead.getValue().isEmpty() ? null : subhead.getValue());
        series.getGeneralInfo().setLongDescription(longDescription.getValue().isEmpty() ? null : longDescription.getValue());
        series.getSeriesInfo().setGenre(!genre.getSelected().isEmpty()? Genre.getValue(genre.getSelected().get(0)) : null);
        series.getSeriesInfo().setStatus(!Objects.equals(status.getSelectedValue(), "- None -") ?
                Status.getValue(status.getSelectedValue()) : null);
        return series;
    }

    public String getShowColor() {
        String style = showColor.getAttribute(HtmlAttributes.STYLE.get());
        String color = null;
        try {
            String[] rgbValues = RegexUtil.getGroup(style, ".*rgb\\((\\d{1,3}, \\d{1,3}, \\d{1,3})\\).*", 1).split(",");
            color = String.format("#%02x%02x%02x", Integer.valueOf(rgbValues[0].trim()), Integer.valueOf(rgbValues[1].trim()), Integer.valueOf(rgbValues[2].trim()));
        } catch (Exception e) {
            color = "";
        }
        return color;
    }
}
