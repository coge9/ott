package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info;

/**
 * Class to create Long or Medium description field
 * that has wysiwig field with ability to upload images or add existing one
 */
public class WysiwigDescription {

    Blurb blurb = new Blurb();

    public Blurb getBlurb() {
        return blurb;
    }

    public void setBlurb(Blurb blurb) {
        this.blurb = blurb;
    }

}
