package com.nbcuni.test.cms.utils.jsonparsing;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.nbcuni.test.webdriver.Utilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JsonSchemaValidator {

    private JsonSchema schema;

    private JsonSchemaValidator() {

    }

    public JsonSchemaValidator(String schemaFilePath) throws IOException {
        this();
        JsonNode schemaJson = JsonLoader.fromPath(schemaFilePath);
        initSchema(schemaJson);
    }

    public JsonSchemaValidator(File schemaFile) throws IOException {
        this();
        JsonNode schemaJson = JsonLoader.fromFile(schemaFile);
        initSchema(schemaJson);
    }

    public JsonSchemaValidator(URL schemaUrl) throws IOException {
        this();
        JsonNode schemaJson = JsonLoader.fromURL(schemaUrl);
        initSchema(schemaJson);
    }

    public boolean validateBySchema(String jsonFilePath) {
        Boolean status = false;
        JsonNode validatedJson = null;
        try {
            validatedJson = JsonLoader.fromPath(jsonFilePath);
        } catch (IOException e) {
            Utilities.logSevereMessage("Unable to get JSON for validation");
        }
        if (validatedJson != null) {
            ProcessingReport report = null;
            try {
                report = schema.validate(validatedJson);
            } catch (ProcessingException e) {
                Utilities.logSevereMessageThenFail("Unable to validate JSON by schema - " + Utilities.convertStackTraceToString(e));
            }
            status = printReport(report);
        }
        return status;
    }

    public boolean validateBySchema(File jsonFile) {
        Boolean status = false;
        JsonNode validatedJson = null;
        try {
            validatedJson = JsonLoader.fromFile(jsonFile);
        } catch (IOException e) {
            Utilities.logSevereMessage("Unable to get JSON for validation");
        }
        if (validatedJson != null) {
            ProcessingReport report = null;
            try {
                report = schema.validate(validatedJson);
            } catch (ProcessingException e) {
                Utilities.logSevereMessageThenFail("Unable to validate JSON by schema - " + Utilities.convertStackTraceToString(e));
            }
            status = printReport(report);
        }
        return status;
    }

    public boolean validateBySchema(URL jsonUrl) {
        Utilities.logInfoMessage("Validation by schema is started");
        Boolean status = false;
        JsonNode validatedJson = null;
        try {
            validatedJson = JsonLoader.fromString(JsonParserHelper.getInstance().getJson(jsonUrl.toString()).toString());
        } catch (IOException e) {
            Utilities.logSevereMessage("Unable to get JSON for validation");
        }
        if (validatedJson != null) {
            ProcessingReport report = null;
            try {
                report = schema.validate(validatedJson);
            } catch (ProcessingException e) {
                Utilities.logSevereMessageThenFail("Unable to validate JSON by schema - " + Utilities.convertStackTraceToString(e));
            }
            status = printReport(report);
        }
        return status;
    }

    public boolean validateBySchemaFromString(String line) {
        Utilities.logInfoMessage("Validation by schema from String is started");
        Boolean status = false;
        JsonNode validatedJson = null;
        try {
            validatedJson = JsonLoader.fromString(line);
        } catch (IOException e) {
            Utilities.logSevereMessage("Unable to get JSON for validation");
        }
        if (validatedJson != null) {
            ProcessingReport report = null;
            try {
                report = schema.validate(validatedJson);
            } catch (ProcessingException e) {
                Utilities.logSevereMessageThenFail("Unable to validate JSON by schema - " + Utilities.convertStackTraceToString(e));
            }
            status = printReport(report);
        }
        return status;
    }

    /**
     * Pretty print a validation report to stdout
     *
     * <p>
     * Will print whether validation succeeded. In the event of a failure, dumps
     * validation error messages.
     * </p>
     *
     * @param report
     *            the report
     * @throws IOException
     *             failure to print to stdout
     */
    private Boolean printReport(final ProcessingReport report) {
        final boolean success = report.isSuccess();
        Utilities.logInfoMessage("Validation " + (success ? "succeeded" : "failed"));
        if (!success) {
            Utilities.logSevereMessage("VALIDATION REPORT: </br>" + "<PRE>" + report.toString() + "</PRE>");
            Utilities.logWarningMessage("---- END REPORT ----");
        }
        return success;
    }


    /**
     *
     * <p>
     * Common method for configuration schema factory. Can be used in future for custom configuration of validaiton.
     * </p>
     *
     * @param schemaJson - JSON schema node
     *
     */
    private void initSchema(JsonNode schemaJson) {
        try {
            schema = JsonSchemaFactory.byDefault().getJsonSchema(schemaJson);
        } catch (ProcessingException e) {
            Utilities.logSevereMessageThenFail("Unable to get JSON schema - " + Utilities.convertStackTraceToString(e));
        }
    }
}
