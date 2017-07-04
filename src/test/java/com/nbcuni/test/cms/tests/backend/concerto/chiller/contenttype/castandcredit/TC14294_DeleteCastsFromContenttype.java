package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.castandcredit;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.EventPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.PersonEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.role.RoleEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.castcredit.CastEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

/**
 *
 * @author Aliaksei_Dzmitrenka
 * Pre-condition:
 * Create Person, Role, Event
 * Step 1: Set Cast to event (Pair Person\Role)
 * Step 2: Save
 * Step 3: Make sure cast was added
 */

public class TC14294_DeleteCastsFromContenttype extends BaseAuthFlowTest {

    private Content event;
    private Content person;
    private Content role;

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
        eventPage.onCastAndCreditBlock().removeRandomCast();
        Map<PersonEntity, RoleEntity> expectedCast = eventPage.onCastAndCreditBlock().getCasts();
        eventPage.saveAsDraft();
        Map<PersonEntity, RoleEntity> actual = eventPage.onCastAndCreditBlock().getCasts();
        softAssert.assertEquals(expectedCast, actual, "Casts was not deleted", "Casts was deleted", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deletePersonTC14294() {
        rokuBackEndLayer.deleteContentType(person);
        rokuBackEndLayer.deleteContentType(role);
        rokuBackEndLayer.deleteContentType(event);
    }
}
