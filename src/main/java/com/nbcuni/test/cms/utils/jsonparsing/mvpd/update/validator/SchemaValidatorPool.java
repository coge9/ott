package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.validator;

import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Platform;
import com.nbcuni.test.cms.pageobjectutils.mvpd.BrandsMvpdAdmin;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.jsonparsing.JsonSchemaValidator;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums.MvpdEntitlementsServiceVersion;
import com.nbcuni.test.webdriver.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SchemaValidatorPool {

    private static final String DEFAULT_SCHEMA_NAME = "default";
    private static final String NAME_WITH_LOGO_TYPE_FILTER = "V2logoTypeFilter";
    private static String schemaPattern = "%s%s" + File.separator + "%s"
            + File.separator + "%s.json";
    private static Map<String, JsonSchemaValidator> validatorPool = new HashMap<String, JsonSchemaValidator>();

    private SchemaValidatorPool() {
    }

    public static JsonSchemaValidator getValidator(
            MvpdEntitlementsServiceVersion version, Platform platform,
            BrandsMvpdAdmin brand, Boolean... isLogoTypeFilterAdded) {
        JsonSchemaValidator validator;
        String schemaPath = findOutShemaPath(version, platform, brand, isLogoTypeFilterAdded);
        validator = validatorPool.get(schemaPath);
        if (validator != null) {
            return validator;
        } else {
            File schemaFile = new File(schemaPath);
            if (schemaFile.exists()) {
                try {
                    validator = new JsonSchemaValidator(schemaFile);
                } catch (IOException e) {
                    Utilities.logSevereMessage("There is an error during loading of the schema");
                }
                validatorPool.put(schemaPath, validator);
                return validator;
            } else {
                Utilities.logSevereMessage("Cannot getCorrect schemaValidator.");
                return null;
            }
        }
    }

    private static String findOutShemaPath(
            MvpdEntitlementsServiceVersion version, Platform platform,
            BrandsMvpdAdmin brand, Boolean... isLogoTypeFilterAdded) {
        String path = Config.getInstance()
                .getPathToJsonSchemesMvpdEntitlementsService();
        String brandId = null;
        if (brand != null) {
            brandId = brand.getName();
        }
        String verisonPath;
        if (version.equals(MvpdEntitlementsServiceVersion.V2) && isLogoTypeFilterAdded.length > 0 && isLogoTypeFilterAdded[0]) {
            verisonPath = NAME_WITH_LOGO_TYPE_FILTER;
        } else {
            verisonPath = version.name();
        }
        String schemaPath = String.format(schemaPattern, path, verisonPath,
                platform.get(), brandId);
        File schemaFile = new File(schemaPath);
        if (schemaFile.exists()) {
            return schemaPath;
        } else
            return String.format(schemaPattern, path, verisonPath,
                    platform.get(), DEFAULT_SCHEMA_NAME);
    }
}
