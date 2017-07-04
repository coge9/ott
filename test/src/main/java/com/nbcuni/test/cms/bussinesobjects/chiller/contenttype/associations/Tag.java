package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations;

import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTerm;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.TaxonomyType;

/**
 * @author Aliaksei_Dzmitrenka
 */

public class Tag extends TaxonomyTerm {

    private String tag;
    private String tagUuid;

    public Tag() {
        taxonomyType = TaxonomyType.TAGS;
    }

    public Tag(String title) {
        this();
        setTag(title);
    }

    public String getTag() {
        return getTitle();
    }

    public void setTag(String title) {
        setTitle(title);
    }

    public String getTagUuid() {
        return tagUuid;
    }

    public void setTagUuid(String tagUuid) {
        this.tagUuid = tagUuid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tag other = (Tag) obj;
        if (tag == null) {
            if (other.tag != null)
                return false;
        } else if (!tag.equals(other.tag))
            return false;
        if (tagUuid == null) {
            if (other.tagUuid != null)
                return false;
        } else if (!tagUuid.equals(other.tagUuid))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Tag [tag=" + getTag() + "]";
    }

}
