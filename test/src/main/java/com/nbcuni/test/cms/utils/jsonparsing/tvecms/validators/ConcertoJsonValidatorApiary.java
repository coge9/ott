package com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.FileUtil;
import com.nbcuni.test.cms.utils.jsonparsing.JsonParserHelper;
import com.nbcuni.test.cms.utils.jsonparsing.JsonSchemaValidator;
import com.nbcuni.test.webdriver.Utilities;

import java.io.File;
import java.io.IOException;

/**
 * Class is design for schema validation of CMS content type publishing.
 * <p>
 * Scheme will be picked up dynamically from apiary (http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/) and JSONs are validated against it.
 */

public class ConcertoJsonValidatorApiary {

    // private constructor because we just have static methods of class.
    private ConcertoJsonValidatorApiary() {

    }

    /**
     * This method will validate any JSON (passed as string) against correspondent apiary scheme.
     * Schema will be picked up automatically based on ItemType.
     * <p>
     *
     * @param json - JSON to validate as String.
     * @param type - item type of object the JSON is correspond to.
     * @return boolean - is validation passed.
     */
    public static Boolean validateJson(String json, ItemTypes type) {
        JsonSchemaValidator validator = null;
        try {
            validator = new JsonSchemaValidator(prepareSchema(type));
        } catch (IOException e) {
            Utilities.logSevereMessageThenFail("Validator of JSON schema for TVE service was not generated");
        }
        return validator.validateBySchemaFromString(json);
    }

    /**
     * Method will download a scheme locally, do some modification, write it to file and return absolute
     * path to that file.
     * <p>
     *
     * @param type - item type of object the JSON is correspond to.
     * @return String - absolute path to schema file.
     */
    private static String prepareSchema(ItemTypes type) {
        // Prepare path for schema file based on its content type.
        // Adding System.currentTimeMillis() to allow each test has its own scheme for validation.
        String schemaPath = Config.getInstance().getPathToApiaryJsonSchemes() + type.getApiaryEndpoint() + "_" + System.currentTimeMillis() + ".json";
        // Define correspondent scheme URL in Apiary
        String schemaUrl = Config.getInstance().getBaseUrlToApiarySchema() + type.getApiaryEndpoint();
        if (type.equals(ItemTypes.TAXONOMY_TERM)) {
            schemaUrl = schemaUrl.replace("/json+schema/", "/schema+json/");
        }
        Utilities.logInfoMessage("Take scheme from validation from " + schemaUrl);
        // Read scheme from URL to String.
        JsonElement schemaJson = JsonParserHelper.getInstance().getJson(schemaUrl);

        // For video type remove duplicate entries in schema (temporary solution).
        if (type.equals(ItemTypes.VIDEO)) {
            removeDuplicates(schemaJson);
        }
        String schemaString = schemaJson.toString();
        // Modify scheme that way to change all String JSON fields accept NULL as well.
        // If any JSON has NULL value instead of string it will be consider as acceptable JSON.
        schemaString = schemaString.replaceAll("\"type\":\"string\"", "\"type\":[\"string\", \"null\"]");
        schemaString = schemaString.replaceAll("\"type\":\"number\"", "\"type\":[\"number\", \"null\"]");
        File schemaFile = new File(schemaPath);
        // Mark scheme file to be deleted on exit.
        schemaFile.deleteOnExit();
        // Create all directories existed in Path (in case directory was not created before).
        schemaFile.getParentFile().mkdirs();
        // Write scheme (its string representation to file)
        FileUtil.writeToFile(schemaFile, schemaString, false);
        return schemaFile.getAbsolutePath();
    }


    /**
     * Temporary solution, because in current apiary schema there are two duplicates in mandatory fields array for mpxMetadata
     * Object.
     */
    private static void removeDuplicates(JsonElement schemaJson) {
        // take properties field
        JsonElement properties = schemaJson.getAsJsonObject().get("properties");
        if (properties != null && properties.isJsonObject()) {
            // select mpxMetadata property
            JsonElement mpxMetadata = properties.getAsJsonObject().get("mpxMetadata");
            if (mpxMetadata != null && mpxMetadata.isJsonObject()) {
                // take required field from mpxMetadata.
                JsonElement required = mpxMetadata.getAsJsonObject().get("required");
                // Delete one closedCaption value in case it present twice.
                if (required != null && required.isJsonArray()) {
                    JsonArray requiredFields = required.getAsJsonArray();
                    int numberOfClosedCaption = 0;
                    int indexOfClosedCaption = 0;
                    int curentIndex = 0;
                    for (JsonElement element : requiredFields) {
                        if (element.isJsonPrimitive() && element.toString().equals("\"closedCaptioning\"")) {
                            numberOfClosedCaption++;
                            indexOfClosedCaption = curentIndex;
                        }
                        curentIndex++;
                    }
                    if (numberOfClosedCaption > 1) {
                        requiredFields.remove(indexOfClosedCaption);
                    }
                }
            }
        }
    }
}
