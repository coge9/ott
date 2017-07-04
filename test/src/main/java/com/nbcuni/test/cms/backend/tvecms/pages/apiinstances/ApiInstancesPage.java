package com.nbcuni.test.cms.backend.tvecms.pages.apiinstances;

import com.nbcuni.test.cms.backend.tvecms.pages.ConfirmationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.APIType;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.ApiInstanceEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.factory.CreateFactoryModule;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.elements.table.Table;
import com.nbcuni.test.cms.elements.table.TableCell;
import com.nbcuni.test.cms.elements.table.TableRow;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.GlobalConstants;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Aleksandra_Lishaeva on 1/18/16.
 */
public class ApiInstancesPage extends MainRokuAdminPage {
    private static final String DISABLE_XPATH = ".//a[contains(text(),'Disable')]";
    private static final String EXPAND_BTN_XPATH = ".//*[@class='ctools-link']//a";
    @FindBy(xpath = ".//ul[@class='action-links']//a[text()='Add']")
    private Link add;
    @FindBy(id = "ctools-export-ui-list-items")
    private Table table;

    private TextField search = new TextField(webDriver, By.id("edit-search"));
    private Button apply = new Button(webDriver, By.id("ctools-export-ui-list-items-apply"));

    public ApiInstancesPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public void searchByTitle(String title) {
        search.enterText(title);
        apply.clickWithAjaxWait();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public List<String> getInstances() {
        Utilities.logInfoMessage("Get list of configured API instances");
        List<String> allInstances = table.getValuesFromColumn(TableColumns.TITLE.toString());
        List<String> availableInstances = new ArrayList<>();
        for (String instance : allInstances) {
            TableCell cell = expandOperations(instance);
            if (!cell.geCellElement().findElements(By.xpath(DISABLE_XPATH)).isEmpty()) {
                availableInstances.add(instance);
            }
            expandOperations(instance);
        }
        return availableInstances;
    }

    public InstanceEditForm clickAddNewItem() {
        add.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new InstanceEditForm(webDriver, aid);
    }

    public ConfirmationPage deleteInstance(String title) {
        Utilities.logInfoMessage("Click edit for instance: " + title);
        searchByTitle(title);
        TableCell operationsCell = expandOperations(title);
        operationsCell.clickLinkByName("Delete");
        return new ConfirmationPage(webDriver, aid);
    }

    public String createAmazonInstanceIfOnlyOnePresent() {
        List<String> instances = getNamesOfEnabledInstances(APIType.AMAZON);
        if (instances.size() == 1) {
            return createAmazonInstance(CreateFactoryModule.createAmazonApiInstance());
        } else {
            return instances.get(0);
        }
    }

    public String createAmazonInstance(ApiInstanceEntity entity) {
        InstanceEditForm instanceEditForm = clickAddNewItem();
        instanceEditForm.createApiInstance(entity);
        return entity.getTitle();
    }

    public List<String> getNamesOfEnabledInstances(APIType apiType) {
        List<TableRow> enabledRows = table.getEnabledRows();
        List<TableRow> apiInstanceTitles = table.sortByTextInColumnContains(enabledRows, "Type", apiType.getType());
        return table.getColumnValuesFromTable(apiInstanceTitles, "Title").stream()
                .filter(item -> !StringUtils.containsIgnoreCase(item, "aqa")).collect(Collectors.toList());
    }

    public TableCell expandOperations(String title) {
        TableCell operationsCell = table.getRowByTextInColumn(title, TableColumns.TITLE.toString()).getCellByColumnTitle(TableColumns.OPERATIONS.toString());
        operationsCell.getInnerElementInCell(By.xpath(EXPAND_BTN_XPATH)).click();
        pause(2);
        return operationsCell;
    }

    public boolean isIosBrandConfigured() {
        List<String> serviceInstances = getNamesOfEnabledInstances(APIType.API_SERVICE);
        List<String> amazonInstances = getNamesOfEnabledInstances(APIType.AMAZON);
        TableRow relatedAmazonInstance = table.getRowByTextInColumnContains(amazonInstances.get(0), TableColumns.SECONDARY_INSTANCE.getName());
        //Check that
        // 1) only one API instance,
        // 2) one Amazon SQS and
        // 3) Secondary instance is Amazon SQS for Development
        if (serviceInstances.size() == 1 && amazonInstances.size() == 1 && relatedAmazonInstance != null) {
            relatedAmazonInstance.setParentTable(table);
            String instanceTitle = relatedAmazonInstance.getCellByColumnTitle(TableColumns.TITLE.getName()).getCellInnerText();
            return instanceTitle.equals(GlobalConstants.DEVELOPMENT);
        }
        if (serviceInstances.isEmpty() && amazonInstances.size() == 1 && relatedAmazonInstance == null) {
            return true;
        }
        Utilities.logSevereMessageThenFail("IOS Concerto API instance isn't configured on brand");
        return false;
    }

    private enum TableColumns {
        TITLE("TITLE"), NAME("NAME"), STORAGE("STORAGE"), URL("URL"), OPERATIONS("OPERATIONS"), SECONDARY_INSTANCE("SECONDARY INSTANCE");

        private String name;

        private TableColumns(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
