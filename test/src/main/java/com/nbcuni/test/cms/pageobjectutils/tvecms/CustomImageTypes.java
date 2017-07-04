package com.nbcuni.test.cms.pageobjectutils.tvecms;


import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.ProgramConcertoImageSourceType;

import java.util.*;

public enum CustomImageTypes {

    // video
    ONE_TILE_VIDEO_1("1tile_video_1", "video", ImageTileType.ONE_TILE, "1tile_video_1.jpg", ""),
    THREE_TILE_VIDEO_1("3tile_video_1", "video", ImageTileType.THREE_TILE, "3tile_video_1.jpg", ""),
    THREE_TILE_VIDEO_1_RESUME("3tile_video_1_resume", "video", ImageTileType.THREE_TILE, "3tile_video_1_resume.jpg", ""),
    THREE_TILE_VIDEO_1_NOW_PLAYING("3tile_video_1_now_playing", "video", ImageTileType.THREE_TILE, "3tile_video_1_now_playing.jpg", ""),
    THREE_TILE_VIDEO_2("3tile_video_2", "video", ImageTileType.THREE_TILE, "3tile_video_2.jpg", ""),
    THREE_TILE_VIDEO_2_RESUME("3tile_video_2_resume", "video", ImageTileType.THREE_TILE, "3tile_video_2_resume.jpg", ""),
    THREE_TILE_VIDEO_2_NOW_PLAYING("3tile_video_2_now_playing", "video", ImageTileType.THREE_TILE, "3tile_video_2_now_playing.jpg", ""),
    THREE_TILE_VIDEO_3("3tile_video_3", "video", ImageTileType.THREE_TILE, "3tile_video_3.jpg", ""),
    THREE_TILE_VIDEO_3A("3tile_video_3a", "video", ImageTileType.THREE_TILE, "3tile_video_3A.jpg", ""),
    THREE_TILE_VIDEO_3_RESUME("3tile_video_3_resume", "video", ImageTileType.THREE_TILE, "3tile_video_3_resume.jpg", ""),
    THREE_TILE_VIDEO_3A_RESUME("3tile_video_3a_resume", "video", ImageTileType.THREE_TILE, "3tile_video_3A_resume.jpg", ""),
    THREE_TILE_VIDEO_4("3tile_video_4", "video", ImageTileType.THREE_TILE, "3tile_video_4.jpg", ""),
    THREE_TILE_VIDEO_5("3tile_video_5", "video", ImageTileType.THREE_TILE, "3tile_video_5.jpg", ""),
    THREE_TILE_VIDEO_6("3tile_video_6", "video", ImageTileType.THREE_TILE, "3tile_video_6.jpg", ""),
    THREE_TILE_VIDEO_6_RESUME("3tile_video_6_resume", "video", ImageTileType.THREE_TILE, "3tile_video_6_resume.jpg", ""),
    THREE_TILE_VIDEO_7("3tile_video_7", "video", ImageTileType.THREE_TILE, "3tile_video_7.jpg", ""),
    THREE_TILE_VIDEO_8("3tile_video_8", "video", ImageTileType.THREE_TILE, "3tile_video_8.jpg", ""),

    // program
    ONE_TILE_PROGRAM_1("1tile_program_1", "program", ImageTileType.ONE_TILE, "1tile_program_1.jpg", "1620x608"),
    ONE_TILE_PROGRAM_2("1tile_program_2", "program", ImageTileType.ONE_TILE, "1tile_program_2.jpg", "1620x608"),
    THREE_TILE_PROGRAM_1("3tile_program_1", "program", ImageTileType.THREE_TILE, "3tile_program_1.jpg", "340x191"),
    THREE_TILE_PROGRAM_3("3tile_program_3", "program", ImageTileType.THREE_TILE_THREE, "3tile_program_3.jpg", "340x191"),
    PROGRAM_SOURCE_WITHLOGO("program_source_withlogo", "program", ImageTileType.THREE_TILE_THREE, "program_source_withlogo.jpg", "2560x1440");


    private static final Random RANDOM = new Random();
    private static final List<CustomImageTypes> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = CustomImageTypes.values().length;

    private static final List<CustomImageTypes> videoSourcethreeTileVideo = Arrays.asList(
            THREE_TILE_VIDEO_1, THREE_TILE_VIDEO_1_RESUME,
            THREE_TILE_VIDEO_1_NOW_PLAYING, THREE_TILE_VIDEO_2,
            THREE_TILE_VIDEO_2_RESUME, THREE_TILE_VIDEO_2_NOW_PLAYING,
            THREE_TILE_VIDEO_4, THREE_TILE_VIDEO_5, THREE_TILE_VIDEO_6,
            THREE_TILE_VIDEO_6_RESUME, THREE_TILE_VIDEO_7, THREE_TILE_VIDEO_8);

    private static final List<CustomImageTypes> programSourceThreeTileVideo = Arrays.asList(
            THREE_TILE_VIDEO_3, THREE_TILE_VIDEO_3_RESUME, THREE_TILE_VIDEO_3A, THREE_TILE_VIDEO_3A_RESUME);

    private static final List<CustomImageTypes> oneTileVideosource = Arrays.asList(ONE_TILE_VIDEO_1);
    private String name;
    private String relation;
    private String size;
    private ImageTileType type;
    private String imageFileName;

    private CustomImageTypes(String name, String relation, ImageTileType type, String imageFileName, String size) {
        this.name = name;
        this.relation = relation;
        this.type = type;
        this.imageFileName = imageFileName;
        this.size = size;
    }

    public static CustomImageTypes randomType() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static CustomImageTypes randomForProgram() {
        CustomImageTypes temp = null;
        while (temp == null) {
            temp = randomType();
            if (!temp.getRelation().equals("program")) {
                temp = null;
            }
        }
        return temp;
    }

    public static CustomImageTypes randomForVideo() {
        CustomImageTypes temp = null;
        while (temp == null) {
            temp = randomType();
            if (!temp.getRelation().equals("video")) {
                temp = null;
            }
        }
        return temp;
    }

    public static List<CustomImageTypes> getVideoOneTileTypes() {
        List<CustomImageTypes> list = new ArrayList<>();
        for (CustomImageTypes item : VALUES) {
            if (item.getType().equals(ImageTileType.ONE_TILE) && item.getRelation().equals("video")) {
                list.add(item);
            }
        }
        return list;
    }

    public static List<CustomImageTypes> getVideoThreeTileVideoSourceTypes() {
        return videoSourcethreeTileVideo;
    }

    public static List<CustomImageTypes> getVideoOneTileVideoSourceTypes() {
        return oneTileVideosource;
    }

    public static List<CustomImageTypes> getVideoThreeTileProgramTypes() {
        return programSourceThreeTileVideo;
    }

    public static List<CustomImageTypes> getProgramOneTileProgramOneTypes() {
        List<CustomImageTypes> list = new ArrayList<>();
        for (CustomImageTypes item : VALUES) {
            if (item.getType().equals(ImageTileType.ONE_TILE) && item.getRelation().equals("program")
                    && item.getName().equals(ONE_TILE_PROGRAM_1.getName())) {
                list.add(item);
            }
        }
        return list;
    }

    public static List<CustomImageTypes> getProgramOneTileProgramTwoTypes() {
        List<CustomImageTypes> list = new ArrayList<CustomImageTypes>();
        for (CustomImageTypes item : VALUES) {
            if (item.getType().equals(ImageTileType.ONE_TILE) && item.getRelation().equals("program")
                    && item.getName().equals(ONE_TILE_PROGRAM_2.getName())) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * Method get styles related to 3tile program's source only
     *
     * @return - List of image styles for 3tile program source
     */
    public static List<CustomImageTypes> getProgramThreeTileOneTypes() {
        List<CustomImageTypes> list = new ArrayList<>();
        list.add(THREE_TILE_PROGRAM_1);
        list.add(PROGRAM_SOURCE_WITHLOGO);
        return list;
    }

    public static List<CustomImageTypes> getProgramThreeTileThreeTypes() {
        List<CustomImageTypes> list = new ArrayList<>();
        list.add(THREE_TILE_PROGRAM_3);
        return list;
    }

    public static List<CustomImageTypes> getProgramThreeTileProgramThreeTypes() {
        List<CustomImageTypes> list = new ArrayList<>();
        for (CustomImageTypes item : VALUES) {
            if (item.getType().equals(ImageTileType.THREE_TILE_THREE) && item.getRelation().equals("program")) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * Method get styles related to 3tile program's source only
     *
     * @param sourceSource - type of source
     *
     * @return - List of image styles for 3tile program source
     */
    public static List<CustomImageTypes> getStyleTypes(ProgramConcertoImageSourceType sourceSource) {
        List<CustomImageTypes> list = new ArrayList<>();
        switch (sourceSource) {
            case SOURCE_3TILE:
                list.add(THREE_TILE_PROGRAM_1);
                list.add(PROGRAM_SOURCE_WITHLOGO);
                break;
            default:
                break;
        }
        return list;
    }

    public String getName() {
        return name;
    }

    public String getRelation() {
        return relation;
    }

    public ImageTileType getType() {
        return type;
    }

    public String getImageFileNmae() {
        return imageFileName;
    }

    public int getWidth() {
        return Integer.parseInt(size.substring(0, size.indexOf("x")));
    }

    public int getHeight() {
        return Integer.parseInt(size.substring(size.indexOf("x") + 1));
    }

    public String getSize() {
        return size;
    }

}
