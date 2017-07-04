package com.nbcuni.test.cms.verification.chiller;


import com.nbcuni.test.cms.utils.SoftAssert;

import java.util.List;

public abstract class BaseVerificatorForPublishing {
    private String uuidPattern = "[a-z,0-9]{8}-[a-z,0-9]{4}-[a-z,0-9]{4}-[a-z,0-9]{4}-[a-z,0-9]{12}";
    private String revisionPattern = "[0-9]+.";

    public void verifyUuids(SoftAssert softAssert, List<String> uuids, String name) {
        for (String uuid : uuids) {
            softAssert.assertTrue(uuid.matches(uuidPattern), "The " + name + ": " + uuid + " does not meet pattern" + uuidPattern);
        }
    }

    public void verifyUuid(SoftAssert softAssert, String uuid, String name) {
        softAssert.assertTrue(uuid.matches(uuidPattern), "The " + name + ": actual result '" + uuid + "' does not meet pattern" + uuidPattern);
    }

    public void verifyRevision(SoftAssert softAssert, String revision) {
        softAssert.assertTrue(revision.matches(revisionPattern), "The revision: '" + revision + "' does not meet pattern" + revisionPattern);
    }

}
