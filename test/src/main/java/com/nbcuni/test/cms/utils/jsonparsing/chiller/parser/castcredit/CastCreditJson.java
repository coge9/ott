package com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.castcredit;

import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ChannelReferencesJson;

import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 6/1/16.
 */
public class CastCreditJson extends AbstractEntity implements Cloneable {

    private String uuid;
    private String itemType;
    private int revision;
    private List<CastEntity> cast;
    private ChannelReferencesJson program;

    public CastCreditJson(Content content) {
        this.uuid = "";
        this.revision = 0;
        this.itemType = ItemTypes.CAST_CREDIT.getItemType();
        this.cast = content.getCastAndCredit();
        this.program = new ChannelReferencesJson().getObject(content.getAssociations().getChannelReferenceAssociations().getChannelReference());
    }

    public List<CastEntity> getCast() {
        return cast;
    }

    public void setCast(List<CastEntity> cast) {
        this.cast = cast;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ChannelReferencesJson getProgram() {
        return program;
    }

    public void setProgram(ChannelReferencesJson program) {
        this.program = program;
    }
}
