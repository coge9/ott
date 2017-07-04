package com.nbcuni.test.cms.utils.thumbnails;

import com.nbcuni.test.cms.utils.Config;

import java.io.File;

/**
 * Created by Ivan_Karnilau on 20-Oct-15.
 */
public class GettingImages {

    private static final String thumbnailsImagePath = Config.getInstance().getPathToThumbnailsImages();
    private static final String programPath = thumbnailsImagePath + "program" + File.separator;
    private static final String oneTileProgramOnePath = programPath + "1tile_program_1" + File.separator;
    private static final String oneTileProgramTwoPath = programPath + "1tile_program_2" + File.separator;
    private static final String threeTilePath = programPath + "3_tile" + File.separator;
    private static final String customSource = programPath + "customSource" + File.separator;

    //  1 tile program 1
    private static final String INITIAL_1TILE_PROGRAM_1 = oneTileProgramOnePath + "initial_thumbnail.jpeg";
    private static final String ALL_FIELDS_UPDATED_1TILE_PROGRAM_1 = oneTileProgramOnePath + "all_fields_updated_thumbnail.jpeg";
    private static final String TITLE_FIELD_UPDATED_1TILE_PROGRAM_1 = oneTileProgramOnePath + "title_field_updated_thumbnail.jpeg";
    private static final String CTA_FIELD_UPDATED_1TILE_PROGRAM_1 = oneTileProgramOnePath + "cta_field_updated_thumbnail.jpeg";
    private static final String HEADLINE_FIELD_UPDATED_1TILE_PROGRAM_1 = oneTileProgramOnePath + "headline_field_updated_thumbnail.jpeg";
    private static final String IMAGE_UPDATED_1TILE_PROGRAM_1 = oneTileProgramOnePath + "only_image_updated_thumbnail.jpeg";
    private static final String ALL_FIELDS_AND_IMAGE_UPDATED_1TILE_PROGRAM_1 = oneTileProgramOnePath + "all_fields_and_image_updated_thumbnail.jpeg";

    //  1 tile program 2
    private static final String INITIAL_1TILE_PROGRAM_2 = oneTileProgramTwoPath + "initial_1tile_program_2.jpeg";
    private static final String UPDATED_LOGO_1TILE_PROGRAM_2 = oneTileProgramTwoPath + "updated_logo_1tile_program_2.jpeg";
    private static final String CTA_FIELD_UPDATED_1TILE_PROGRAM_2 = oneTileProgramTwoPath + "updated_cta_1tile_program_2.jpeg";
    private static final String IMAGE_UPDATED_1TILE_PROGRAM_2 = oneTileProgramTwoPath + "updated_image_1tile_program_2.jpeg";
    private static final String MULTIUPDATING_1TILE_PROGRAM_2 = oneTileProgramTwoPath + "multiupdating_1tile_program_2.jpeg";


    //  3 tile
    private static final String INITIAL_THREE_TILE_PROGRAM_ONE_THUMBNAIL = threeTilePath + "initial_3tile_program_1.jpeg";
    private static final String INITIAL_THREE_TILE_PROGRAM_THREE_THUMBNAIL = threeTilePath + "initial_3tile_program_3.jpeg";
    private static final String ONLY_IMAGE_UPDATED_THREE_TILE_PROGRAM_ONE_THUMBNAIL = threeTilePath + "only_image_updated_3tile_program_1.jpeg";
    private static final String ONLY_IMAGE_UPDATED_THREE_TILE_PROGRAM_THREE_THUMBNAIL = threeTilePath + "only_image_updated_3tile_program_3.jpeg";

    //  Custom Source
    private static final String INITIAL_CUSTOM_1TILE_SOURCE = customSource + "initial_custom_1tile_source.jpeg";
    private static final String INITIAL_CUSTOM_1TILE_PROGRAM_1_THUMBNAIL = customSource + "initial_custom_1tile_program_1_thumbnail.jpeg";

    private static final String UPDATED_CUSTOM_1TILE_SOURCE = customSource + "updated_custom_1tile_source.jpeg";
    private static final String UPDATED_CUSTOM_1TILE_PROGRAM_1_THUMBNAIL = customSource + "updated_custom_1tile_program_1_thumbnail.jpeg";

    //  1 tile program 1
    public static String getInitial1TileProgram1() {
        return INITIAL_1TILE_PROGRAM_1;
    }

    public static String getAllFieldsUpdated1TileProgram1() {
        return ALL_FIELDS_UPDATED_1TILE_PROGRAM_1;
    }

    public static String getTitleFieldUpdated1TileProgram1() {
        return TITLE_FIELD_UPDATED_1TILE_PROGRAM_1;
    }

    public static String getCTAFieldUpdated1TileProgram1() {
        return CTA_FIELD_UPDATED_1TILE_PROGRAM_1;
    }

    public static String getHeadlineFieldUpdated1TileProgram1() {
        return HEADLINE_FIELD_UPDATED_1TILE_PROGRAM_1;
    }

    public static String getOnlyImageUpdated1TileProgram1() {
        return IMAGE_UPDATED_1TILE_PROGRAM_1;
    }

    public static String getAllFieldsAndImageUpdated1TileProgram1() {
        return ALL_FIELDS_AND_IMAGE_UPDATED_1TILE_PROGRAM_1;
    }

    //  1 tile program 2
    public static String getInitial1TileProgram2() {
        return INITIAL_1TILE_PROGRAM_2;
    }

    public static String getMultiupdating1TileProgram2() {
        return MULTIUPDATING_1TILE_PROGRAM_2;
    }

    public static String getTUpdatedLogo1TileProgram2() {
        return UPDATED_LOGO_1TILE_PROGRAM_2;
    }

    public static String getCTAFieldUpdated1TileProgram2() {
        return CTA_FIELD_UPDATED_1TILE_PROGRAM_2;
    }

    public static String getImageUpdated1TileProgram2() {
        return IMAGE_UPDATED_1TILE_PROGRAM_2;
    }

    //  3 tile program
    public static String getInitialThreeTileProgramOneThumbnails() {
        return INITIAL_THREE_TILE_PROGRAM_ONE_THUMBNAIL;
    }

    public static String getInitialThreeTileProgramThreeThumbnails() {
        return INITIAL_THREE_TILE_PROGRAM_THREE_THUMBNAIL;
    }

    public static String getOnlyImageUpdatedThreeTileProgramOneThumbnails() {
        return ONLY_IMAGE_UPDATED_THREE_TILE_PROGRAM_ONE_THUMBNAIL;
    }

    public static String getOnlyImageUpdatedThreeTileProgramThreeThumbnails() {
        return ONLY_IMAGE_UPDATED_THREE_TILE_PROGRAM_THREE_THUMBNAIL;
    }

    //  Custom Source
    public static String getInitialCustom1tileSource() {
        return INITIAL_CUSTOM_1TILE_SOURCE;
    }

    public static String getInitialCustom1tileProgram1Thumbnail() {
        return INITIAL_CUSTOM_1TILE_PROGRAM_1_THUMBNAIL;
    }

    public static String getUpdatedCustom1tileSource() {
        return UPDATED_CUSTOM_1TILE_SOURCE;
    }

    public static String getUpdatedCustom1tileProgram1Thumbnail() {
        return UPDATED_CUSTOM_1TILE_PROGRAM_1_THUMBNAIL;
    }
}
