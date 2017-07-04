package com.nbcuni.test.cms.backend.chiller.block.contenttype.castcredit;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.PersonEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.role.RoleEntity;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.castcredit.CastEntity;
import com.nbcuni.test.cms.utils.webdriver.ActionsUtil;
import org.openqa.selenium.support.FindBy;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author Aliaksei_Dzmitrenka
 */

public class CastAndCredit extends AbstractContainer {

    @FindBy(xpath = ".//*[contains(@id,'field-cast-and-credit-values')]/tbody/tr")
    private List<Cast> castBlock;

    @FindBy(xpath = ".//*[contains(@id,'field-cast-and-credit-und-add-more')]")
    private Button addAnotherItem;

    @FindBy(xpath = ".//tr[contains(@class, 'draggable')]//a[@href='#']")
    private List<Link> dragItem;

    public void fillCast(Map<PersonEntity, RoleEntity> map) {
        for (Entry<PersonEntity, RoleEntity> entry : map.entrySet()) {
            int size = castBlock.size();
            castBlock.get(size - 1).selectPerson(entry.getKey().getMetadataInfo().getFirstName());
            castBlock.get(size - 1).selectRole(entry.getValue().getMetadataInfo().getFirstName());
            addAnotherItem();
        }
    }

    public void fillCast(List<CastEntity> castAndCredits) {
        for (int i = 0; i < castAndCredits.size(); i++) {
            if (i > 0) {
                addAnotherItem();
            }
            castBlock.get(i).selectPerson(castAndCredits.get(i).getPerson());
            castBlock.get(i).selectRole(castAndCredits.get(i).getRole());

        }
    }

    /**
     * The method to get list of 'CastEntity' objects that set on UI
     * @return list of Cast and credit object "CastEntity"
     */
    public List<CastEntity> getCast() {
        List<CastEntity> castAndCredits = new ArrayList<>();
        for (int i = 0; i < castBlock.size(); i++) {
            CastEntity castEntity = new CastEntity();
            castEntity.setPerson(castBlock.get(i).getPerson().trim());
            castEntity.setRole(castBlock.get(i).getRole().trim());
            castAndCredits.add(castEntity);
        }
        return castAndCredits;
    }

    /**
     * The method read Cast and Credit from UI and set to map
     * @return map of objects where Person as key and Roles as value
     */
    public Map<PersonEntity, RoleEntity> getCasts() {
        Map<PersonEntity, RoleEntity> cast = new HashMap<PersonEntity, RoleEntity>();
        for (int i = 0; i < castBlock.size(); i++) {
            PersonEntity personEntityForAdd = new PersonEntity();
            personEntityForAdd.getMetadataInfo().setFirstName(castBlock.get(i).getPerson());
            RoleEntity roleEntityForAdd = new RoleEntity();
            roleEntityForAdd.getMetadataInfo().setFirstName(castBlock.get(i).getRole());
            cast.put(personEntityForAdd, roleEntityForAdd);
        }
        return cast;
    }

    public void addAnotherItem() {
        addAnotherItem.clickWithAjaxWait();
    }

    public void removeRandomCast() {
        int size = castBlock.size();
        castBlock.get(new Random().nextInt(size)).removeItem();
    }

    public void dragAndDropFirstToLast() {
        ActionsUtil.perform(getWebDriver()).dragAndDrop(dragItem.get(1).element(), dragItem.get(dragItem.size() - 1).element());
    }

}
