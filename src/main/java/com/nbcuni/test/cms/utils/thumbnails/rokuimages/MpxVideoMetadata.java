package com.nbcuni.test.cms.utils.thumbnails.rokuimages;


public enum MpxVideoMetadata {

    TEST_METADATA("AQA_OTT_VIDEO Aqa test", "AQA_OTT_PROGRAM Aqa test", "CATCH UP ON SEASON 1 aqa", "WATCH LATEST EPISODE aqa", new Integer(7), new Integer(13)),
    OLD_METADATA("AQA_OTT_VIDEO", "AQA_OTT_PROGRAM", "CATCH UP ON SEASON 1", "WATCH LATEST EPISODE", new Integer(2), new Integer(5));

    String episodeTitle;
    String relatedShowTitle;
    String headLine;
    String featureCTA;
    Integer season;
    Integer episode;

    private MpxVideoMetadata(String episodeTitle, String relatedShowTitle, String headLine,
                             String featureCTA, Integer season,
                             Integer episode) {
        this.episodeTitle = episodeTitle;
        this.relatedShowTitle = relatedShowTitle;
        this.headLine = headLine;
        this.featureCTA = featureCTA;
        this.season = season;
        this.episode = episode;
    }

    public static MpxVideoMetadata getTestMetadata() {
        return MpxVideoMetadata.TEST_METADATA;
    }

    public static MpxVideoMetadata getOldMetadata() {
        return MpxVideoMetadata.OLD_METADATA;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public Integer getSeason() {
        return season;
    }

    public Integer getEpisode() {
        return episode;
    }

    public String getRelatedShowTitle() {
        return relatedShowTitle;
    }

    public void setRelatedShowTitle(String relatedShowTitle) {
        this.relatedShowTitle = relatedShowTitle;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getFeatureCTA() {
        return featureCTA;
    }

}
