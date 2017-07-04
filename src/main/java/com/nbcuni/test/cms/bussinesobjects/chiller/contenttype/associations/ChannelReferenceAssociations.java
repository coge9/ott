package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by Ivan_Karnilau on 22-Apr-16.
 */
public class ChannelReferenceAssociations {

    private ChannelReference channelReference = new ChannelReference();

    public ChannelReference getChannelReference() {
        return channelReference;
    }

    public ChannelReferenceAssociations setChannelReference(ChannelReference channelReference) {
        this.channelReference = channelReference;
        return this;
    }

    public boolean isObjectNull() {
        return channelReference.isObjectNull();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("channelReference", channelReference)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChannelReferenceAssociations that = (ChannelReferenceAssociations) o;
        return Objects.equal(channelReference, that.channelReference);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(channelReference);
    }
}
