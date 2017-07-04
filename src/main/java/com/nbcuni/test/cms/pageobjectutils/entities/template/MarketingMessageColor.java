package com.nbcuni.test.cms.pageobjectutils.entities.template;

public enum MarketingMessageColor {

    //           NbcNews     NbcUni     Sprout     Telemundo  Esquire    Eonline    CNBC
    COLOR_1("#2d648a", "#ff3366", "#dfbe25", "#9c0001", "#15a5ee", "#00ecff", "#2d648a"),
    COLOR_2("#3aacd7", "#01ccb9", "#7dba52", "#fe0000", "#bbcdcf", "#03cdff", "#3aacd7"),
    COLOR_3("#427a92", "#45b4ae", "#458940", "#fd781d", "#427b8c", "#ffed4c", "#427a92"),
    COLOR_4("#c34c28", "#9a9b9d", "#2aa5aa", "#f99815", "#999999", "#ff24d1", "#c34c28"),
    COLOR_5("#3f9ccd", "#df191a", "#e43b96", "#fdcb52", "#fef200", "#46ba37", "#3f9ccd"),
    COLOR_6("#c7d759", "#36beee", "#ca3d33", "#b5195a", "#e95433", "#9900ff", "#c7d759"),
    COLOR_7("#ffa500", "#f9d658", "#fffc01", "#773686", "#6ac5e0", "#9999ff", "#ffa500"),
    COLOR_8("#ccd6db", "#f591a9", "#ff9024", "#009c39", "#cdd6db", "#ab1f2a", "#ccd6db"),
    COLOR_9("#bf2e1a", "#5ca031", "#95d6d2", "#00b4e1", "#d91900", "#bdbdbd", "#bf2e1a"),
    COLOR_10("#cfdde6", "#ee541e", "#2b85ba", "#9eafcb", "#b7eafb", "#bdbdbd", "#cfdde6"),
    COLOR_11("#558f38", "#7a9960", "#75459b", "#c3c7ca", "#15a5ee", "#cfdeff", "#2d648a"),
    COLOR_12("#c20b20", "#0063e4", "#aebff3", "#687073", "#bbcdcf", "#00ecff", "#3aacd7"),
    COLOR_13("#aed6e9", "#cfdeff", "#fbb762", "#1bb0aa", "#427b8c", "#03cdff", "#427a92"),
    COLOR_14("#558fb4", "#ff3366", "#d34f42", "#94abb3", "#999999", "#ffed4c", "#c34c28"),
    COLOR_15("#2d648a", "#01ccb9", "#fefe9c", "#9c0001", "#fef200", "#ff24d1", "#3f9ccd"),
    COLOR_16("#3aacd7", "#45b4ae", "#53ecf2", "#fe0000", "#e95433", "#46ba37", "#c7d759"),
    DEFAULT_COLOR("", "", "", "", "", "", "");

    private String colorCodeNbcNew;
    private String colorCodeNbcUniverso;
    private String colorCodeSprout;
    private String colorCodeTelemundo;
    private String colorCodeEsquire;
    private String colorCodeEonline;
    private String colorCodeCNBC;

    MarketingMessageColor(final String colorCodeNbcNew,
                          final String colorCodeNbcUniverso,
                          final String colorCodeSprout,
                          final String colorCodeTelemundo,
                          final String colorCodeEsquire,
                          final String colorCodeEonline,
                          final String colorCodeCNBC) {
        this.colorCodeCNBC = colorCodeCNBC;
        this.colorCodeEonline = colorCodeEonline;
        this.colorCodeEsquire = colorCodeEsquire;
        this.colorCodeNbcUniverso = colorCodeNbcUniverso;
        this.colorCodeNbcNew = colorCodeNbcNew;
        this.colorCodeTelemundo = colorCodeTelemundo;
        this.colorCodeSprout = colorCodeSprout;
    }

    public String getCode(String brand) {

        switch (brand) {
            case "nbcnews":
                return this.colorCodeNbcNew;
            case "nbcUniverso":
                return this.colorCodeNbcUniverso;
            case "sprout":
                return this.colorCodeSprout;
            case "telemundo":
                return this.colorCodeTelemundo;
            case "esquire":
                return this.colorCodeEsquire;
            case "eonline":
                return this.colorCodeEonline;
            case "cnbc":
                return this.colorCodeCNBC;
            default:
                return this.colorCodeEonline;
        }

    }

    public String getRandom(String brand) {
//        MarketingMessageColor.values().length;
        return null;
    }
}