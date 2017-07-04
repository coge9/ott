package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities;

import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.LogoTypes;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Platform;
import com.nbcuni.test.cms.pageobjectutils.mvpd.BrandsMvpdAdmin;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.UrlUtils;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums.MvpdEntitlementsServiceVersion;


/**
 * ******************************************************************
 * EntitlementsServiceEntity
 * <p>
 * Description: entity represents entitlements srvice with different 
 * variations of versions and types.
 *
 * @author Dzianis Kulesh
 * @version 1.0
 * May 01, 2016
 *
 * Revision Log-------------- Date- , Author- , Change Description-
 * *********************************************************************
 */


public class EntitlementsServiceEntity {

    private Platform platform;

    /*
     * Sometime we would like to create entitlements serivce URL
     * for platfroms which not Included into Platfrom enum. For such
     * cases it is possible to use platformStringRepresentation field
     * which accept Strings.
     *
     * */
    private String platformStringRepresentation;
    private Instance instance;
    private BrandsMvpdAdmin brand;


    /*
     * Sometime we would like to create entitlements serivce URL
     * for Brands which not Included into BrandsMvpdAdmin enum. For such
     * cases it is possible to use brandRequestorId field
     * which accept Strings.
     *
     * */
    private String brandRequestorId;
    private MvpdEntitlementsServiceVersion version;
    private Boolean isSpanish = false;
    private String mvpdId;
    private LogoTypes logoType = LogoTypes.NONE;

    public EntitlementsServiceEntity() {
        super();
    }

    public EntitlementsServiceEntity(MvpdEntitlementsServiceVersion version, BrandsMvpdAdmin brand, Platform platform, Instance instance) {
        setVersion(version);
        setBrand(brand);
        setPlatform(platform);
        setInstance(instance);
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platformStringRepresentation = platform;
    }

    public String getPlatformStringRepresentation() {
        return platformStringRepresentation;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
        platformStringRepresentation = platform.get();
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public BrandsMvpdAdmin getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brandRequestorId = brand;
    }

    public String getBrandRequestorId() {
        return brandRequestorId;
    }

    public void setBrand(BrandsMvpdAdmin brand) {
        this.brand = brand;
        this.brandRequestorId = brand.getRequestorId();
    }

    public MvpdEntitlementsServiceVersion getVersion() {
        return version;
    }

    public void setVersion(MvpdEntitlementsServiceVersion version) {
        this.version = version;
    }

    public Boolean isSpanish() {
        return isSpanish;
    }

    public void setIsSpanish(Boolean isSpanish) {
        this.isSpanish = isSpanish;
    }

    public String getMvpdId() {
        return mvpdId;
    }

    public void setMvpdId(String mvpdId) {
        this.mvpdId = mvpdId;
    }

    public LogoTypes getLogoType() {
        return logoType;
    }

    public void setLogoType(LogoTypes logoType) {
        this.logoType = logoType;
    }


    /**
     * *********************************************************************************
     * Return URL to entitlements service. URL is lower cased. All gets parameters
     * (brand/platform/instance etc.) are sorted alphabetically.
     *
     * @return normalized entitlements service URL.
     *
     * ***********************************************************************************
     */
    public String getMvpdServiceUrlNormalized() {
        String url = getMvpdServiceUrl();
        url = UrlUtils.getUrlWithSortedGetParameters(url);
        return url.toLowerCase();
    }


    /**
     * *********************************************************************************
     * Return URL to entitlements service. All gets parameters
     * (brand/platform/instance etc.) are suffled and shown in random order. Also key and value 
     * of GET parameter has random case;
     *
     * @return get mvpd URL in random mixed case.
     *
     * ***********************************************************************************
     */
    public String getMvpdServiceUrlRandom() {
        String url = getMvpdServiceUrl();
        url = UrlUtils.getUrlKeyAndValueMixedCase(url);
        url = UrlUtils.getUrlWithShuffleGetParameters(url);
        return url;
    }

    /**
     * *********************************************************************************
     * Return URL to entitlements service based on entity parameters.
     *
     * @return entitlements service URl
     * ***********************************************************************************
     */
    public String getMvpdServiceUrl() {
        validateParameters();
        String generalUrl = null;
        // define Version of service V1 or V2 and create general URL base on this.
        switch (version) {
            case V1:
                generalUrl = Config.getInstance().getMvpdServiceGeneralUrl();
                break;
            case V2:
                generalUrl = Config.getInstance().getMvpdServiceV2GeneralUrl();
                break;
        }
        // if we are going to build spanish URL change general URL to spanish.
        if (isSpanish) {
            generalUrl = generalUrl.replace("/mvpd/", "/es/mvpd/");
        }
        // pass certain brand/platform/instance to general URL
        String serviceUrl = String.format(generalUrl, brandRequestorId, instance.get(), platformStringRepresentation);
        // if we are going to build URL for single MVPD and field_mvpd_id parameter;
        if (mvpdId != null) {
            serviceUrl = serviceUrl + "?field_mvpd_id=" + SimpleUtils.encodeStringToHTML(mvpdId);
        }
        // if we are going to build URL with logoType parameter add correspondent part into URL;
        if (logoType != null && !logoType.equals(LogoTypes.NONE)) {
            serviceUrl = serviceUrl + "&logotype=" + SimpleUtils.encodeStringToHTML(logoType.getBulkUploadName());
        }
        // for V2 version add mashery logic
        if (version.equals(MvpdEntitlementsServiceVersion.V2)) {
            serviceUrl = masheryV2Logic(serviceUrl);
        }
        return serviceUrl;
    }


    /**
     * *********************************************************************************
     * Before any processing validation of parameters will be performed.
     * If mandatory parameter is invalid RuntimeException will be thrown.
     *
     * ***********************************************************************************
     */
    private void validateParameters() {
        StringBuilder errorMessage = new StringBuilder();
        if (version == null) {
            errorMessage.append("Entitlements service version is NOT define; \n");
        }
        if (platform == null && platformStringRepresentation == null) {
            errorMessage.append("Entitlements service Platform is NOT define; \n");
        }
        if (instance == null) {
            errorMessage.append("Entitlements service Instance is NOT define; \n");
        }
        if (brand == null && brandRequestorId == null) {
            errorMessage.append("Entitlements service Brand is NOT define; \n");
        }
        if (errorMessage.length() > 0) {
            throw new RuntimeException("Unable to Identitfy Entitlements Serivce " + errorMessage.toString());
        }
    }


    /**
     * *********************************************************************************
     * Currently use for V2 service URLs only. For some services
     * mashery is used, so additional get parameters should be added into URL
     * if mashery is used.
     *
     * @param url - service URL without mashery
     * ***********************************************************************************
     */
    private String masheryV2Logic(String url) {
        if (System.getProperty("akamai") != null && System.getProperty("akamai").equals("true")) {
            String key = null;
            String propertyFormat = "mvpd.masherykey.V2.%s.%s";
            String masheryProperty = String.format(propertyFormat, brandRequestorId, platform.get());
            String masherykey = Config.getInstance().getEnvDependantProperty(masheryProperty);
            if (masherykey != null && !masherykey.isEmpty()) {
                String isEnabled = masherykey.split(";")[0];
                if (isEnabled.equals("true")) {
                    key = masherykey.split(";")[1];
                }
            }
            if (key != null) {
                url = url + "&api_key=" + key;
            }
        }
        return url;
    }


}
