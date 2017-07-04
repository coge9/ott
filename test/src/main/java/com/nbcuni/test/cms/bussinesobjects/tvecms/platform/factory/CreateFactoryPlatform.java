package com.nbcuni.test.cms.bussinesobjects.tvecms.platform.factory;


import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformEntity;
import com.nbcuni.test.cms.pageobjectutils.entities.Image;
import com.nbcuni.test.cms.utils.SimpleUtils;

import java.util.HashMap;
import java.util.Map;

import static com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformConstant.*;


/**
 * Created by Alena_Aukhukova on 12/24/2015.
 */
public class CreateFactoryPlatform {

    private CreateFactoryPlatform(){
        super();
    }

    /**
     * Create default platform with default images and one checked viewport
     * @return platform
     */
    public static PlatformEntity createDefaultPlatform() {
        PlatformEntity platformEntity = new PlatformEntity();
        platformEntity.setName(NAME + SimpleUtils.getRandomString(5))
                .setMachineName(MACHINE_NAME + SimpleUtils.getRandomString(5).toLowerCase())
                .setSettings(String.format(SETTINGS, SimpleUtils.getRandomStringWithRandomLength(15)))
                .setMvpdEntitlementServiceUrl(MVPD_ENTITLEMENT_SERVICE_URL)
                .setGlobalHeaderBrandLogoImage(setDefaultImage(GLOBAL_HEADER_BRAND_LOGO_IMAGE_NAME))
                .setViewPorts(VIEWPORT_MAP);
        return platformEntity;
    }

    public static PlatformEntity createPlatformWithWrongExtensionForAllImages() {
        PlatformEntity platformEntity = createDefaultPlatform();
        platformEntity.getGlobalHeaderBrandLogoImage().setExtension(EXTENSION_JPG);
        return platformEntity;
    }

    public static PlatformEntity createPlatformWithWrongMvpdEntitlementServiceUrl() {
        PlatformEntity platformEntity = createDefaultPlatform();
        platformEntity.setMvpdEntitlementServiceUrl(WRONG_MVPD_ENTITLEMENT_SERVICE_URL);
        return platformEntity;
    }

    public static PlatformEntity updateCreatedPlatform(PlatformEntity entityForUpdate) {
        entityForUpdate.setMachineName(entityForUpdate.getMachineName() + "_new")
                .setName(entityForUpdate.getName() + "_new")
                .setViewPorts(invertDefaultViewports())
                .setSettings(String.format(SETTINGS, SimpleUtils.getRandomStringWithRandomLength(15) + "_new"));
        return entityForUpdate;
    }

    /**
     * Parse default Image name [width]x[height]x.[extension]
     * @param imageName
     * @return
     */
    private static Image setDefaultImage(String imageName) {
        Image image = new Image();
        int width = Integer.parseInt(imageName.split("x")[0]);
        int height = Integer.parseInt(imageName.split("x")[1]);
        String extension = imageName.split("x")[2];
        image.setHeight(height).setWidth(width).setExtension(extension);
        return image;
    }

    private static Map<String, Boolean> invertDefaultViewports() {
        Map<String, Boolean> updatedViewportsMap = new HashMap<>(VIEWPORT_MAP);
        for (Map.Entry<String, Boolean> checkboxWithLabel : updatedViewportsMap.entrySet()) {
            boolean currentValue = checkboxWithLabel.getValue();
            checkboxWithLabel.setValue(!currentValue);
        }
        return updatedViewportsMap;
    }

}
