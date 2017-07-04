package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.validator;

import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Platform;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums.MvpdEntitlementsServiceVersion;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.validator.v1.*;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.validator.v2.DesktopV2;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.validator.v2.OthersV2;

public class ValueValidatorFactory {

    public static IValueValidator getValueValidator(
            MvpdEntitlementsServiceVersion version, Platform platform, Instance instance) {
        switch (version) {
            case V1:
                switch (platform) {
                    case DESKTOP:
                        return new DesktopV1(instance);
                    case MOBILE:
                        return new MobileV1(instance);
                    case APPLETV:
                        return new AppleTvV1(instance);
                    case XBOXONE:
                        return new XboxoneV1(instance);
                    case WIN8:
                        return new Win8V1(instance);
                    case IOS:
                        return new IosV1(instance);
                }
                break;
            case V2:
                switch (platform) {
                    case DESKTOP:
                        return new DesktopV2(instance);
                    default:
                        return new OthersV2(platform, instance);
                }
        }
        return null;
    }
}
