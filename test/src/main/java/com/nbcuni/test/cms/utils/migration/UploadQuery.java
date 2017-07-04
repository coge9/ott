package com.nbcuni.test.cms.utils.migration;

import com.nbcuni.test.cms.pageobjectutils.tveservice.Queries;
import com.nbcuni.test.cms.utils.FileUtil;

import java.io.File;

/**
 * Created by alekca on 12.05.2016.
 */
public class UploadQuery {

    public static String getQuery(Queries queries) {
        return FileUtil.readFileToSingleString(new File("src/test/resources/sql/" + queries.getQuery()));
    }
}
