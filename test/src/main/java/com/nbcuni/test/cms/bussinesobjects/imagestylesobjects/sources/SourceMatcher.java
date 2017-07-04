package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources;


import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.androidsources.*;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.appletvsources.*;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.firetvsources.*;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.iossources.ProgramIOS1600x900;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.iossources.ProgramIOS1965x1108;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.rokusources.*;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.roqusqssources.ProgramRokuSqs1973x918;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.roqusqssources.ProgramRokuSqs2560x1440Small;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.roqusqssources.ProgramRokuSqs2560x1440SmallEnd;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.roqusqssources.ProgramRokuSqsLogo;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.xboxonesources.ProgramXboxone1600x900;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.xboxonesources.ProgramXboxone2560x1440;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.videosource.VideoOriginalImage;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.videosource.VideoPortrait720x960;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.videosource.VideoThreeTileImage;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Aliaksei_Dzmitrenka on 2/9/2017.
 */
public enum SourceMatcher {

    Program1TileShowPageSource("1 Tile Show Page Source", "field_1_tile_show_page_source", Program1TileShowPageSource.class, ContentType.TVE_PROGRAM, CmsPlatforms.ROKU),
    Program1TileSource("1 Tile source", "field_1_tile_source", Program1TileSource.class, ContentType.TVE_PROGRAM, CmsPlatforms.ROKU),
    Program3TileProgram3Source("3 Tile Program 3 Source", "field_3_tile_program_3_source", Program3TileProgram3Source.class, ContentType.TVE_PROGRAM, CmsPlatforms.ROKU),
    Program3TileSource("3 Tile source", "field_3_tile_source", Program3TileSource.class, ContentType.TVE_PROGRAM, CmsPlatforms.ROKU),
    ProgramRokuLogo("Program Logo", "field_program_logo", ProgramRokuLogo.class, ContentType.TVE_PROGRAM, CmsPlatforms.ROKU),

    VideoOriginalImage("Original image", "field_mpx_original_image", VideoOriginalImage.class, ContentType.TVE_VIDEO, null),
    VideoPortrait720x960("video_portrait_720_960", "field_video_portrait_720_960", VideoPortrait720x960.class, ContentType.TVE_VIDEO, null),
    Video3TileProgramSource("3 Tile Program source", "field_video_parent_series", VideoThreeTileImage.class, ContentType.TVE_VIDEO, null),

    // Roku SQS sources
    ProgramRokuSqsLogo("Roku Logo", "field_program_roku_logo", ProgramRokuSqsLogo.class, ContentType.TVE_PROGRAM, CmsPlatforms.ROKU_SQS),
    ProgramRoku1973x918("Roku 1973x918", "field_program_roku_1973x918", ProgramRokuSqs1973x918.class, ContentType.TVE_PROGRAM, CmsPlatforms.ROKU_SQS),
    ProgramRoku2560x1440SmallEnd("Roku 2560x1440 Small-End", "field_program_roku_2560x1440", ProgramRokuSqs2560x1440SmallEnd.class, ContentType.TVE_PROGRAM, CmsPlatforms.ROKU_SQS),
    ProgramRoku2560x1440Small("Roku 2560x1440 Small", "field_program_roku_2560x1440_v2", ProgramRokuSqs2560x1440Small.class, ContentType.TVE_PROGRAM, CmsPlatforms.ROKU_SQS),

    ProgramLogo("Program Logo", "field_program_logo_1", ProgramLogo.class, ContentType.TVE_PROGRAM, CmsPlatforms.ANDROID),
    ProgramAndroid2560x1440("Android 2560X1440", "field_android_2560x1440", ProgramAndroid2560x1440.class, ContentType.TVE_PROGRAM, CmsPlatforms.ANDROID),
    ProgramAndroid680x1176("Android 680x1176", "field_android_680x1176", ProgramAndroid680x1176.class, ContentType.TVE_PROGRAM, CmsPlatforms.ANDROID),
    ProgramAndroid2088x1576("Android 2088x1576", "field_android_2088x1576", ProgramAndroid2088x1576.class, ContentType.TVE_PROGRAM, CmsPlatforms.ANDROID),
    ProgramAndroid1440x2560("Android 1440x2560", "field_android_1440x2560", ProgramAndroid1440x2560.class, ContentType.TVE_PROGRAM, CmsPlatforms.ANDROID),
    ProgramAndroid1200x1200("Android 1200x1200", "field_android_1200x1200", ProgramAndroid1200x1200.class, ContentType.TVE_PROGRAM, CmsPlatforms.ANDROID),

    ProgramIOS1600x900("iOS 1600x900", "field_ios_1600x900", ProgramIOS1600x900.class, ContentType.TVE_PROGRAM, CmsPlatforms.IOS),
    ProgramIOS1965x1108("iOS 1965x1108", "field_ios_1965x1108", ProgramIOS1965x1108.class, ContentType.TVE_PROGRAM, CmsPlatforms.IOS),

    ProgramAppleTV1920x1080("AppleTV 1920x1080", "field_program_appletv_1920x1080", ProgramAppleTV1920x1080.class, ContentType.TVE_PROGRAM, CmsPlatforms.APPLETV),
    ProgramAppleTV1920x486("AppleTV 1920x486", "field_program_appletv_1920x486", ProgramAppleTV1920x486.class, ContentType.TVE_PROGRAM, CmsPlatforms.APPLETV),
    ProgramAppleTV1704x440("AppleTV 1704x440", "field_program_appletv_1704x440", ProgramAppleTV1704x440.class, ContentType.TVE_PROGRAM, CmsPlatforms.APPLETV),
    ProgramAppleTV1600x900("AppleTV 1600x900", "field_program_appletv_1600x900", ProgramAppleTV1600x900.class, ContentType.TVE_PROGRAM, CmsPlatforms.APPLETV),
    ProgramAppleTV771x292("AppleTV 771x292", "field_program_appletv_771x292", ProgramAppleTV771x292.class, ContentType.TVE_PROGRAM, CmsPlatforms.APPLETV),
    ProgramAppleTV600x600("AppleTV 600x600", "field_program_appletv_600x600", ProgramAppleTV600x600.class, ContentType.TVE_PROGRAM, CmsPlatforms.APPLETV),

    ProgramFireTV1600x900("FireTV 1600x900", "field_program_firetv_1600x900", ProgramFireTV1600x900.class, ContentType.TVE_PROGRAM, CmsPlatforms.FIRETV),
    ProgramFireTV1920x1080("FireTV 1920x1080", "field_program_firetv_1920x1080", ProgramFireTV1920x1080.class, ContentType.TVE_PROGRAM, CmsPlatforms.FIRETV),
    ProgramFireTV2088x1576("FireTV 2088x1576 ", "field_program_firetv_2088x1576", ProgramFireTV2088x1576.class, ContentType.TVE_PROGRAM, CmsPlatforms.FIRETV),
    ProgramFireTV2560x1440("FireTV 2560x1440", "field_program_firetv_2560x1440", ProgramFireTV2560x1440.class, ContentType.TVE_PROGRAM, CmsPlatforms.FIRETV),
    ProgramFireTV680x1176("FireTV 680x1176", "field_program_firetv_680x1176", ProgramFireTV680x1176.class, ContentType.TVE_PROGRAM, CmsPlatforms.FIRETV),
    ProgramFireTVLogo("FireTV Logo", "field_program_firetv_logo", ProgramFireTVLogo.class, ContentType.TVE_PROGRAM, CmsPlatforms.FIRETV),

    ProgramXboxone1600x900("Xbox One 1600x900", "field_program_xboxone_1600x900", ProgramXboxone1600x900.class, ContentType.TVE_PROGRAM, CmsPlatforms.XBOXONE),
    ProgramXboxone2560x1440("Xbox One 2560x1440", "field_program_xboxone_2560x1440", ProgramXboxone2560x1440.class, ContentType.TVE_PROGRAM, CmsPlatforms.XBOXONE),
    PromoMedia("Media", "field_promo_media", null, ContentType.TVE_PROMO, null);


    String sourceName;
    String machineName;
    Class sourceClass;
    ContentType type;
    CmsPlatforms platform;

    SourceMatcher(String sourceName, String machineName, Class sourceClass, ContentType type, CmsPlatforms platform) {
        this.sourceName = sourceName;
        this.machineName = machineName;
        this.sourceClass = sourceClass;
        this.type = type;
        this.platform = platform;
    }

    public static Source getSourceClassInstanceByMachineName(String machineName, ContentType type) {
        for (SourceMatcher source : SourceMatcher.values()) {
            if (source.machineName.equals(machineName) && source.type.equals(type)) {
                try {
                    return (Source) source.getSourceClass().newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException("There is no source with machine name: [" + machineName + "]");
    }

    public static SourceMatcher getSource(String name, ContentType type, CmsPlatforms platform) {
        for (SourceMatcher source : SourceMatcher.values()) {
            if (source.sourceName.equals(name) && source.type.equals(type) && source.platform.equals(platform)) {
                return source;
            }
        }
        throw new RuntimeException("There is no source with name: [" + name + " " + type + " " + platform + "]");
    }

    public static SourceMatcher getSourceByMachibeName(String machineName, ContentType type) {
        for (SourceMatcher source : SourceMatcher.values()) {
            if (source.machineName.equals(machineName) && source.type.equals(type)) {
                return source;
            }
        }
        throw new RuntimeException("There is no source with machineName: [" + machineName + "]");
    }

    public static List<SourceMatcher> getAllSources() {
        return Arrays.asList(SourceMatcher.values());
    }

    public static Set<SourceMatcher> getSourcesSet() {
        Set<SourceMatcher> toReturn = new HashSet<>();
        toReturn.addAll(Arrays.asList(SourceMatcher.values()));
        return toReturn;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getMachineName() {
        return machineName;
    }

    public Class getSourceClass() {
        return sourceClass;
    }

    public CmsPlatforms getPlatform() {
        return platform;
    }

    public Source getSourceClassInstance() {
        return getSourceClassInstanceByMachineName(this.machineName, this.type);
    }

}
