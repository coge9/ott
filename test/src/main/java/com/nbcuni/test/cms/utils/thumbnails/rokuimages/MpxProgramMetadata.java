package com.nbcuni.test.cms.utils.thumbnails.rokuimages;


public enum MpxProgramMetadata {

    TEST_METADATA("AQA_OTT_PROGRAM Aqa test", "CATCH UP ON SEASON 1 aqa", "WATCH LATEST EPISODE aqa"),
    OLD_METADATA("AQA_OTT_PROGRAM", "CATCH UP ON SEASON 1", "WATCH LATEST EPISODE");

    String episodeTitle;
    String headLine;
    String featureCTA;

    private MpxProgramMetadata(String episodeTitle, String headLine,
                               String featureCTA) {
        this.episodeTitle = episodeTitle;
        this.headLine = headLine;
        this.featureCTA = featureCTA;
    }

    public static MpxProgramMetadata getTestMetadata() {
        return MpxProgramMetadata.TEST_METADATA;
    }

    public static MpxProgramMetadata getOldMetadata() {
        return MpxProgramMetadata.OLD_METADATA;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getFeatureCTA() {
        return featureCTA;
    }

}
