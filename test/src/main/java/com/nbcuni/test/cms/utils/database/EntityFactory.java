package com.nbcuni.test.cms.utils.database;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.pageobjectutils.tvecms.VoidShelf;
import java.util.*;

enum DataBaseTables {
    AUTHN("authNcredential"),
    PROVIDERSPROD("providersPROD"),
    PROVIDERSQA("providersQA"),
    MVPD("MVPD"),
    BRAND("Brand"),
    REDIRECTURLS("redirectUrls"),
    TRANSLATIONS("translations"),
    OMNITURE_TAGS("OmnitureTags"),
    RECENTLY_WATCHED_FOR_TRIALING("recentlywatched"),
    LOGOTYPE("LogoType"),
    SHELF("shelf"),
    PLATFORM("platform"),
    VOID_SHELF("voidShelf");

    private final String tableName;

    DataBaseTables(final String name) {
        tableName = name;
    }

    public String getTableName() {
        return tableName;
    }
}

public class EntityFactory {

    public static List<Shelf> getShelfsList() {
        final List<Map<String, String>> data = HSqlTestDataService.getInstance().executeQueryResultAsList(
                QueryBuilder.select().from(DataBaseTables.SHELF.getTableName()).build());
        final ArrayList<Shelf> shelfList = new ArrayList<Shelf>();
        for (final Map<String, String> row : data) {
            final String title = row.get("Title".toUpperCase());
            Slug slug = new Slug();
            slug.setSlugValue(row.get("ShelfAlias".toUpperCase())).setAutoSlug(false);
            final boolean displayTitle = row.get("DisplayTitle".toUpperCase()).equals("1") ? true : false;
            final String titleVariant = row.get("TitleVariant".toUpperCase());
            final int assetsCount = Integer.parseInt(row.get("AssetsCount".toUpperCase()));
            String shelfNodeId = row.get("ShelfNodeId".toUpperCase());
            if (shelfNodeId.isEmpty()) {
                shelfNodeId = null;
            }
            Shelf shelf = new Shelf(title, displayTitle, slug, titleVariant, assetsCount, shelfNodeId);
            final String assets = row.get("Assets".toUpperCase());
            for (String asset : assets.split(";")) {
                if (!asset.isEmpty()) {
                    shelf.addAsset(asset);
                }
            }
            shelfList.add(shelf);
            shelfList.trimToSize();
        }
        return shelfList;
    }

    public static List<VoidShelf> getVoidShelfsList() {
        final List<Map<String, String>> data = HSqlTestDataService.getInstance().executeQueryResultAsList(
                QueryBuilder.select().from(DataBaseTables.VOID_SHELF.getTableName()).build());
        final ArrayList<VoidShelf> shelfList = new ArrayList<VoidShelf>();
        for (final Map<String, String> row : data) {
            final String title = row.get("Title".toUpperCase());
            Slug slug = new Slug();
            slug.setSlugValue(row.get("ShelfAlias".toUpperCase())).setAutoSlug(false);
            final String settings = row.get("Settings".toUpperCase());
            VoidShelf shelf = new VoidShelf(title, slug, settings);
            shelfList.add(shelf);
            shelfList.trimToSize();
        }
        return shelfList;
    }

}
