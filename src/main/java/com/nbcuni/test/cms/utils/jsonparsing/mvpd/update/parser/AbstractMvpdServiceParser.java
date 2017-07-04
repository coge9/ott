package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.MvpdErrorMessage;
import com.nbcuni.test.cms.utils.GlobalConstants;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums.ErrorMessageFields;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public abstract class AbstractMvpdServiceParser {


    public static final String ONE = "1";
    public static final String ZERO = "0";

    // parsing 'adobePassErrorMappings' section
    protected void parseErrorMessages(JsonElement errorsElement,
                                      List<MvpdErrorMessage> errors) {
        if (errorsElement.isJsonObject()) {
            Set<Entry<String, JsonElement>> setOfErrors = errorsElement
                    .getAsJsonObject().entrySet();
            for (Entry<String, JsonElement> singleJsonError : setOfErrors) {
                MvpdErrorMessage error = parseSingleError(singleJsonError);
                if (error != null) {
                    errors.add(error);
                }
            }
        }
    }

    // parsing single error message in 'adobePassErrorMappings' section
    protected MvpdErrorMessage parseSingleError(
            Entry<String, JsonElement> singleJsonError) {
        MvpdErrorMessage errorMessage = new MvpdErrorMessage();
        errorMessage.setMessageId(singleJsonError.getKey());
        JsonObject singleErrorObject = singleJsonError.getValue()
                .getAsJsonObject();
        errorMessage.setMessageTitle(singleErrorObject.get(
                ErrorMessageFields.TITLE.getFieldNameInJson()).getAsString());
        errorMessage.setMessageBody(singleErrorObject.get(
                ErrorMessageFields.BODY.getFieldNameInJson()).getAsString());
        String useAdobeDesk = singleErrorObject.get(
                ErrorMessageFields.USE_ADOBE_DESC.getFieldNameInJson())
                .getAsString();
        if (useAdobeDesk.equals(ONE) || useAdobeDesk.equals(GlobalConstants.TRUE_STRING)) {
            errorMessage.setUseAdobeDesc(true);
        } else if (useAdobeDesk.equals(ZERO) || useAdobeDesk.equals(GlobalConstants.FALSE_STRING)) {
            errorMessage.setUseAdobeDesc(false);
        } else {
            throw new TestRuntimeException("Wrong value in JSON");
        }
        return errorMessage;
    }
}
