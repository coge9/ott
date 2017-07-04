package com.nbcuni.test.cms.utils.logging.listeners;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nbcuni.rallyintegration.RallyQuery;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.GetRequest;
import com.rallydev.rest.response.GetResponse;
import com.rallydev.rest.util.Ref;

import java.util.*;

public class CustomRallyUpdate {

    public static void updateTestCaseResults(RallyRestApi rallyservice,
                                             Map<String, String> testcaseResultMap, String workspaceName)
            throws Exception {

        Calendar calendar = Calendar.getInstance();

        JsonElement workspaceObj =
                RallyQuery.getWorkspaceObject(rallyservice, workspaceName);
        String workspaceid =
                workspaceObj.getAsJsonObject().get("ObjectID").getAsString();

        JsonArray testCaseQuery =
                RallyQuery.getTestCaseObject(rallyservice, workspaceid,
                        testcaseResultMap.get("testcaseid"));
        String testcaseobjid =
                testCaseQuery.get(0).getAsJsonObject().get("ObjectID")
                        .getAsString();

        // Read Test Case
        String ref = Ref.getRelativeRef("/testcase/" + testcaseobjid);
        GetRequest getRequest = new GetRequest(ref);
        GetResponse getResponse = rallyservice.get(getRequest);
        JsonObject testCaseobj = getResponse.getObject();

        if (!testCaseobj.isJsonNull()) {
            // Create Test Case Result
            JsonObject newTcResult = new JsonObject();
            newTcResult
                    .addProperty("Verdict", testcaseResultMap.get("verdict"));

            String tester =
                    RallyQuery
                            .getUserObjectViaEmail(rallyservice,
                                    testcaseResultMap.get("tester")).get(0)
                            .getAsJsonObject().get("_ref").getAsString();
            newTcResult.addProperty("Tester", tester);

            newTcResult.addProperty("TestCase", testCaseobj.getAsJsonObject()
                    .get("_ref").getAsString());
            newTcResult.addProperty("Build", testcaseResultMap.get("build"));

            newTcResult.addProperty("Workspace", workspaceid);

            newTcResult.addProperty("Date",
                    convertDatetoISO(new Date(Long.valueOf(testcaseResultMap.get("date")))));
            newTcResult.addProperty("Notes", testcaseResultMap.get("notes"));
            newTcResult.addProperty("Duration",
                    testcaseResultMap.get("duration"));

            CreateRequest createRequest =
                    new CreateRequest("testcaseresult", newTcResult);
            rallyservice.create(createRequest);
        }
    }

    public static String cleanupComments(String sRallyComments) {

        String tempString = "";
        if (sRallyComments != null) {
            if (sRallyComments.indexOf(")") > -1
                    && sRallyComments.indexOf("Comment") > -1) {
                tempString =
                        sRallyComments
                                .substring(sRallyComments.indexOf(')') + 1);
            } else {
                tempString = sRallyComments;
            }

            tempString = tempString.replace("<br>", "");
            tempString = tempString.replace("\n", "");
            tempString = tempString.replace(" ", "");
            tempString = tempString.replace("<br />", "");
            tempString = tempString.replace("<br/>", "");
            tempString = tempString.replace("<br/>", "");
            tempString = tempString.replace("<div>", "");
            tempString = tempString.replace("</div>", "");
            tempString = tempString.replace("&nbsp;", "");
            tempString = tempString.replaceAll("\\<.*?\\>", "");

        }

        return tempString;
    }

    @SuppressWarnings("restriction")
    private static String convertDatetoISO(Date dte) {

        Calendar c = GregorianCalendar.getInstance();
        TimeZone zone = TimeZone.getTimeZone("America/New_York");
        c.setTimeZone(zone);
        c.setTime(dte);
        return javax.xml.bind.DatatypeConverter.printDateTime(c);

    }
}
