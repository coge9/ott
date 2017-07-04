package com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators;

import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.jsonparsing.JsonSchemaValidator;
import com.nbcuni.test.webdriver.Utilities;

import java.io.IOException;

public class RokuServiceJsonValidator {


    private static RokuServiceJsonValidator instance = null;
    private JsonSchemaValidator programValidator;
    private JsonSchemaValidator pageValidator;
    private JsonSchemaValidator shelfValidator;


    private RokuServiceJsonValidator() {
        try {
            programValidator = new JsonSchemaValidator(Config.getInstance().getPathToRokuJsonSchemes() + Config.getInstance().getProperty("roku.program.schema"));
            pageValidator = new JsonSchemaValidator(Config.getInstance().getPathToRokuJsonSchemes() + Config.getInstance().getProperty("roku.page.schema"));
            shelfValidator = new JsonSchemaValidator(Config.getInstance().getPathToRokuJsonSchemes() + Config.getInstance().getProperty("roku.shelf.schema"));
        } catch (IOException e) {
            Utilities.logSevereMessageThenFail("Validator of JSON schema for TVE service was not generated");
        }
    }

    public static RokuServiceJsonValidator getInstance() {
        if (instance == null) {
            instance = new RokuServiceJsonValidator();
        }
        return instance;

    }

    public Boolean validateProgramBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = programValidator.validateBySchemaFromString(string);
        }
        return status;
    }

    public Boolean validatePageBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = pageValidator.validateBySchemaFromString(string);
        }
        return status;
    }

    public Boolean validateShelfBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = shelfValidator.validateBySchemaFromString(string);
        }
        return status;
    }

    public Boolean validateEventBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.EVENT);
        }
        return status;
    }

    // method using new logic with Apiary.
    public Boolean validateSeriesBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.SERIES);
        }
        return status;
    }

    public Boolean validatePostBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.POST);
        }
        return status;
    }

    public Boolean validateEpisodeBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.EPISODE);
        }
        return status;
    }

    public Boolean validateSeasonBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.SEASON);
        }
        return status;
    }

    public Boolean validateVideoBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.VIDEO);
        }
        return status;
    }

    // method using new logic with Apiary.
    public Boolean validateCollectionsBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.COLLECTIONS);
        }
        return status;
    }

    public Boolean validateMediaGalleryBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.MEDIA_GALLERY);
        }
        return status;
    }

    // method using new logic with Apiary.
    public Boolean validateImageBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.IMAGE);
        }
        return status;
    }

    public Boolean validatePersonBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.PERSON);
        }
        return status;
    }


    public Boolean validateRoleBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.ROLE);
        }
        return status;
    }

    public Boolean validateTaxonomyTermBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.TAXONOMY_TERM);
        }
        return status;
    }

    // method using new logic with Apiary.
    public Boolean validatePromoBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.PROMO);
        }
        return status;
    }

    // method using new logic with Apiary.
    public Boolean validateIosVideoBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.VIDEO);
        }
        return status;
    }

    // method using new logic with Apiary.
    public Boolean validatePlatformBySchema(String string) {
        Boolean status = false;
        if (string != null) {
            status = ConcertoJsonValidatorApiary.validateJson(string, ItemTypes.PLATFORM);
        }
        return status;
    }
}
