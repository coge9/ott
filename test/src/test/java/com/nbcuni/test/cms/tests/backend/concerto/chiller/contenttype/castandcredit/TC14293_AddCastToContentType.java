package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.castandcredit;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.EventPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.castcredit.CastEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 *
 * @author Aliaksei_Dzmitrenka
 * Pre-condition:
 * Create Person, Role, Event
 * Step 1: Set Cast to event (Pair Person\Role)
 * Step 2: Save
 * Step 3: Make sure cast was added
 */

public class TC14293_AddCastToContentType extends BaseAuthFlowTest {

    private Content event;
    private Content person;
    private Content role;
    private List<CastEntity> expectedCast;
    private List<CastEntity> actualCast;

    @Autowired
    @Qualifier("defaultEvent")
    private ContentTypeCreationStrategy eventCreationStrategy;

    @Autowired
    @Qualifier("defaultPerson")
    private ContentTypeCreationStrategy personCreationStrategy;

    @Autowired
    @Qualifier("defaultRole")
    private ContentTypeCreationStrategy roleCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        event = eventCreationStrategy.createContentType();
        person = personCreationStrategy.createContentType();
        role = roleCreationStrategy.createContentType();
        event.getCastAndCredit().add(new CastEntity().setPerson(person.getTitle()).setRole(role.getTitle()));
    }

    @Test(groups = {"cast"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationMediaGallery(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(person);
        rokuBackEndLayer.createContentType(role);
        EventPage eventPage = (EventPage) rokuBackEndLayer.createContentType(event);
        actualCast = eventPage.onCastAndCreditBlock().getCast();
        expectedCast = event.getCastAndCredit();
        softAssert.assertEquals(expectedCast, actualCast, "Casts was not added", "Casts was added");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePersonTC14293() {
        rokuBackEndLayer.deleteContentType(person);
        rokuBackEndLayer.deleteContentType(role);
        rokuBackEndLayer.deleteContentType(event);
    }
}
