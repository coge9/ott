package com.nbcuni.test.cms.utils;

import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.LogoTypes;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Platform;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageTileType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuEndpointType;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.webdriver.Utilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config implements Cloneable {

    private static final String CONFIG_TVE_CMS_FILE_NAME = "config_tve_cms.properties";
    private static final String CONFIG_MVPD_FILE_NAME = "config_mvpd.properties";
    private static Config instance;
    private Properties configProperties;
    private String environment;
    private String apiInstance = "Development";

    private Config() {
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
            instance.setConfigProperties(loadProperties(CONFIG_TVE_CMS_FILE_NAME));
            instance.configProperties.putAll(loadProperties(CONFIG_MVPD_FILE_NAME));
            if (System.getProperty("api_instance") != null) {
                instance.apiInstance = System.getProperty("api_instance").toLowerCase();
            }
        }
        return instance;
    }

    private static Properties loadProperties(final String propertiesFilePath) {
        final Properties props = new Properties();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            final String errorMessage = "Error during getting ClassLoader of the current thread for getting data from property file";
            Utilities.logSevereMessageThenFail(errorMessage);
        }
        assert classLoader != null;
        final InputStream inputStream = classLoader.getResourceAsStream(propertiesFilePath);
        try {
            if (inputStream == null) {
                final String errorMessage = "Property file '" + propertiesFilePath + "' not found in the classpath";
                Utilities.logSevereMessageThenFail(errorMessage);
            }
            props.load(inputStream);
        } catch (final IOException e) {
            Utilities.logSevereMessageThenFail("Error during getting ClassLoader of the current thread for getting data from property file");
        }
        return props;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(final String environment) {
        this.environment = environment;
    }

    public String getDropBoxAccessToken() {
        return configProperties.getProperty("dropbox.access.token");
    }

    public String getScreenshotStorage() {
        return configProperties.getProperty("screenshot.storage");
    }

    public String getDefaultScreenShotUrl() {
        return configProperties.getProperty("screenshot.default.url");
    }

    private void setConfigProperties(final Properties configProperties) {
        this.configProperties = configProperties;
    }

    public String getProperty(final String propertyName) {
        return configProperties.getProperty(propertyName);
    }

    public String getEnvDependantProperty(final String key) {

        return configProperties.getProperty(environment + "." + key);
    }

    // --- Common runtime properties ----

    public String getPathToResouces() {
        return configProperties.getProperty("PathToResources").replace("/", File.separator);
    }

    public String getPathToImages() {
        return configProperties.getProperty("PathToImages").replace("/", File.separator);
    }

    public String getPathToRokuHeaderGenerationImages(String brand) {
        return (configProperties.getProperty("PathToRokuHeaderGenerationImages") + brand + "/").replace("/", File.separator);
    }

    public String getPathToJsonSchemes() {
        return configProperties.getProperty("PathToJsonSchemes").replace("/", File.separator);
    }

    public String getPathToJS() {
        return configProperties.getProperty("PathToJS").replace("/", File.separator);
    }

    public String getPathToTveAPIJsonSchemes() {
        return configProperties.getProperty("PathToJsonSchemesTveAPI").replace("/", File.separator);
    }

    public String getPathToJsonSchemesMvpdEntitlementsService() {
        return configProperties.getProperty("PathToJsonSchemesEntitlementService").replace("/", File.separator);
    }

    public String getPathToScreenshots() {
        return configProperties.getProperty("PathToScreenshots").replace("/", File.separator);
    }

    public int getReRunOnFailureCount() {
        return Integer.parseInt(configProperties.getProperty("ReRunOnFailureCount"));
    }

    public String getPathToFreemarkerTemplates() {
        return getProperty("PathToFreeMarkerTemplates");
    }

    public String getPathToThumbnailsImages() {
        return configProperties.getProperty("PathToThumbnailsImages").replace("/",
                File.separator);
    }

    public boolean isReRunOnFailure() {
        return Boolean.valueOf(configProperties.getProperty("ReRunOnFailure"));
    }

    public String getPathToTempFiles() {
        String pathToTempResources = this.getProperty("PathToTempResources");
        return pathToTempResources.replaceAll("//", File.separator);
    }

    // ----------- Email --------------

    public String[] getDefaulttRecepientsTo() {
        String recepients = configProperties.getProperty("mail.defaultToList");
        return recepients.split(";");
    }

    public String[] getDefaulttRecepientsCc() {
        String recepients = configProperties.getProperty("mail.defaultCcList");
        if (recepients == null || recepients.equals("")) {
            return new String[0];
        } else {
            return recepients.split(";");
        }
    }

    // --------- Gmail ---------

    public String getGmailUser() {
        return getProperty("gmail.user");
    }

    public String getGmailPassword() {
        return getProperty("gmail.password");
    }

    // -------  Malinator --------

    public String getMalinatorHost() {
        return getProperty("malinator.host");
    }

    public String getMalinatorDefaultAddress() {
        return getProperty("malinator.default.address");
    }

    public String getMalinatorDefaultToken() {
        return getProperty("malinator.default.token");
    }

    // --------- Trashmail ----------------
    public String getTrashmailApiEndpoint() {
        return getProperty("trashmail.endpoint");
    }

    public String getTrashmailUser() {
        return getProperty("trashmail.user");
    }

    public String getTrashmailPassword() {
        return getProperty("trashmail.password");
    }

    // --------- MVPD ------------
    public String getMvpdAdminURL() {
        String url = getEnvDependantProperty("mvpdAdmin.url");
        if (environment.equalsIgnoreCase("pr")) {
            String prNumber = System.getProperty("prNumber");
            url = String.format(url, prNumber);
        }
        return url;
    }

    public String getMvpdServiceGeneralUrl() {
        return getMvpdAdminURL() + getProperty("mvpd.service.url.general");
    }

    public String getMvpdServiceV2GeneralUrl() {
        return getMvpdAdminURL() + getProperty("mvpd.service.v2.url.general");
    }

    public String getStitchedServiceUrl(String serviceName, Platform platform, LogoTypes brandLogoType, LogoTypes mvpdLogoType,
                                        String mvpdId, String brandId, String fallBack) {
        String urlPattern = getMvpdAdminURL() + getProperty("mvpdAdmin.mvpd.stitch.service.url");
        String url = String.format(urlPattern, serviceName, platform.get(), brandLogoType.getBulkUploadName(),
                mvpdLogoType.getBulkUploadName(), mvpdId, brandId);
        if (fallBack != null) {
            url = url + "&fallback=" + fallBack;
        }
        return url;
    }

    public String getMvpdImagesUrlPatternConstantPart() {
        return getMvpdAdminURL() + getProperty("mvpdAdmin.mvpd.logourl.pattern");
    }

    // ------- MPX: The Platform --------
    public String getMPXUrl() {
        return configProperties.getProperty("MPXUrl");
    }

    public String getMPXUsername(final String brand) {
        return getEnvDependantProperty(brand + ".mpx.account");
    }

    public String getMPXPassword(final String brand) {
        return getEnvDependantProperty(brand + ".mpx.password");
    }

    public String getAvailableEpisode() {
        return configProperties.getProperty("MPX.availableTVEpisode");
    }

    public String getAvailableShow() {
        return configProperties.getProperty("MPX.availableShow");
    }

    public double getSikuliImageWaitTime() {
        return Integer.parseInt(configProperties.getProperty("SikuliImageWaitTime"));
    }

    public int getMPXAssetBufferPause() {
        return Integer.parseInt(configProperties.getProperty("MPXAssetBufferPause"));
    }

    // -------- WebDriver properties ---------

    public int getImplicitWaitTime() {
        return Integer.parseInt(configProperties.getProperty("ImplicitWaitTime"));
    }

    public int getPageLoadWaitTime() {
        return Integer.parseInt(configProperties.getProperty("PageLoadWaitTime"));
    }

    // ---------- HSQL DB properties ----------

    public String getHSqlDbDriver() {
        return configProperties.getProperty("HsqlDB.Driver");
    }

    public String getMySqlDbDriver() {
        return configProperties.getProperty("MysqlDB.Driver");
    }

    public String getHSqlDbUrl() {
        return configProperties.getProperty("HsqlDB.URL");
    }

    public String getHSqlDbUserName() {
        return configProperties.getProperty("HsqlDB.Userid");
    }

    public String getHSqlDbPassword() {
        return configProperties.getProperty("HsqlDB.Password");
    }

    public String getHSqlDbName() {
        return configProperties.getProperty("HsqlDB.Name");
    }

    public String getHSqlDbStorageFile() {
        return configProperties.getProperty("HsqlDB.StorageFile");
    }

    public String getMySqlDbUrl() {
        return configProperties.getProperty("MysqlDB.URL");
    }

    public String getMySqlDbPort() {
        return getEnvDependantProperty("MysqlDB.Port");
    }

    public String getMySqlDbServer() {
        return getEnvDependantProperty("MysqlDB.Server");
    }

    public String getMySqlDbUserName() {
        return getEnvDependantProperty("MysqlDB.Userid");
    }

    public String getMySqlDbPassword() {
        return getEnvDependantProperty("MysqlDB.Password");
    }

    public String getMySqlDbName(String brand) {
        return getEnvDependantProperty(brand + ".MysqlDB.DBName");
    }

    // --------------- Roku CMS Properties -----------

    public String getRokuHomePage(String brand) {
        String url = getEnvDependantProperty("Url");
        if (!environment.equalsIgnoreCase("pr") && brand.equalsIgnoreCase(RokuBrandNames.USA.getBrandName())) {
            url = url.replace("tvecms", "roku");
        }
        /*
          this replacement is required for end-2-end testing, cause maven
          will not replace property with actual value located under
          QA.Url=http://qa.tvecms.${brand}.nbcuni.com/ in config file.
         */
        url = url.replace("${brand}", System.getProperty("brand"));
        return url;
    }

    public String getPRUrl(String number) {
        String url = String.format(getProperty("PR.Url"), number);
        /*
          this replacement is required for end-2-end testing, cause maven
          will not replace property with actual value located under
          PR.Url=http://tve_ott_cms-${prNumber}.${brand}.pr.tve_ott_cms.nbcuni.com/ in config file.
         */
        url = url.replace("${brand}", System.getProperty("brand"));
        url = url.replace("${prNumber}", System.getProperty("prNumber"));
        return url;
    }

    public String getRokuUserName() {
        return getProperty("roku.user.name");
    }

    public String getRokuUserPassword() {
        return getProperty("roku.user.password");
    }

    public String getRokuUserLogin() {
        return getProperty("roku.user.login");
    }

    public String getRokuMPXUsername(final String brand) {
        Instance env = null;
        if (System.getProperty("env").equalsIgnoreCase(Instance.QA.name()) ||
                System.getProperty("env").equalsIgnoreCase(Instance.DEV.name())) {
            env = Instance.STAGE;
        } else {
            env = Instance.PROD;
        }
        return getProperty(env.name() + "." + brand + ".mpx.account");
    }


    public String getRokuHeaderImageWidth() {
        return this.getProperty("roku.header.image.width");
    }

    public String getRokuHeaderImageHeight() {
        return this.getProperty("roku.header.image.height");
    }

    public String getRokuMPXPassword(final String brand) {
        Instance env = null;
        if (System.getProperty("env").equalsIgnoreCase(Instance.QA.name()) ||
                System.getProperty("env").equalsIgnoreCase(Instance.DEV.name())) {
            env = Instance.STAGE;
        } else {
            env = Instance.PROD;
        }
        return getProperty(env.name() + "." + brand + ".mpx.password");
    }

    public String getRokuMPXAccount(final String brand, Instance instance) {
        return getProperty(instance.name() + "." + brand + ".MPXAccount");
    }


    public String getRokuMPXDefaulPlayer(final String brand, Instance instance) {
        return getProperty(instance.name() + "." + brand + ".MPXDefaultPlayer");
    }

    public String getRokuMvpdDefaultLogoPath() {
        return getProperty("roku.default.mvpd.logo.path");
    }

    public String getLocalPathToRokuVideoThubnailImage(ImageTileType tileType, String name, int variant) {
        String tile;
        if (tileType.equals(ImageTileType.ONE_TILE)) {
            tile = "1_tile";
        } else {
            tile = "3_tile";
        }
        return getPathToImages() + "RokuThumbnails" + File.separator + "video" + File.separator + tile + File.separator + variant
                + File.separator + name;
    }

    // ----------------- JSON --------------------

    public String getPathToRokuJsonSchemes() {
        return configProperties.getProperty("pathToJsonSchemesRoku").replace("/", File.separator);
    }

    public String getPathToApiaryJsonSchemes() {
        return configProperties.getProperty("pathToJsonSchemesApiary").replace("/", File.separator);
    }

    public String getBaseUrlToApiarySchema() {
        return configProperties.getProperty("apiary.base.url");
    }

    public String getPathToChillerJsonSchemes() {
        return configProperties.getProperty("pathToJsonSchemesChiller").replace("/", File.separator);
    }

    public String getPathToIosJsonSchemes() {
        return configProperties.getProperty("pathToJsonSchemesIos").replace("/", File.separator);
    }


    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            Utilities.logSevereMessageThenFail("Unable to clone");
        }
        return null;
    }

    /**
     * @return property for url to internal AQA API
     */
    public String getAqaApiURL() {
        return getProperty("roku.aqa.api.url");
    }

    /**
     * @return property for url to external APIservice roku DEV instance
     */
    public String getApiURL() {
        return getProperty("roku.dev.endpoint");
    }

    /**
     * @return property for getting mashery apiKey to roku DEV instance
     */
    public String getApiKey() {
        return getProperty("roku.dev.apikey");
    }

    public String getAqaAPIUrlBuilderById(String brand, String id) {
        StringBuilder bf = new StringBuilder();
        return bf.append(getRokuHomePage(brand)).append(getAqaApiURL()).append("/").append(id).toString();
    }

    public String getAqaAPIUrlBuilderBySession(String brand, String session) {
        StringBuilder bf = new StringBuilder();
        return bf.append(getRokuHomePage(brand)).append(getAqaApiURL()).append("?parameters%5Bsession_id%5D=").append(session).toString();
    }

    public String getRokuApiCreateEndPoint(RokuEndpointType type) {
        return getRokuApiInstance() + type.getEndpointType();
    }

    public String getRokuApiInstance() {
        return apiInstance;
    }


    public String getRokuApiUpdateEndPoint(RokuEndpointType type, String id) {
        return getRokuApiInstance() + type.getEndpointType() + "/" + id;
    }

    public String getRokuMPXProgramID(final String brand, Instance instance) {
        return getProperty(instance.name() + "." + brand + ".mpx.programID");
    }

    public String getRokuMPXVideoID(final String brand, Instance instance) {
        return getProperty(instance.name() + "." + brand + ".mpx.videoID");
    }

    public String getRokuMPXOwnerID(final String brand, Instance instance) {
        return getProperty(instance.name() + "." + brand + ".mpx.ownerID");
    }

    public String getMPXTestShowName() {
        return getProperty("mpx.programName");
    }

    public String getMPXTestVideoName() {
        return getProperty("mpx.videoName");
    }

    // --------------- Chiller's database(remote) connection related items --------------

    public String getAcquiaSSHost() {
        return getProperty("acquia.ssh.host");
    }

    public String getAcquiaSSHUser() {
        return getProperty("acquia.ssh.user");
    }

    public String getAcquiaSSHPassword() {
        return getProperty("acquia.ssh.password.path");
    }

    public String getChillerDatabasePort() {
        return getProperty("chiller.database.port");
    }

    public String getLocalFreePort() {
        return getProperty("local.free.port");
    }

    public String getChillerDatabaseName() {
        return getProperty("chiller.database.name");
    }

    public String getChillerDatabaseUser() {
        return getProperty("chiller.database.user");
    }

    public String getChillerDatabasePassword() {
        return getProperty("chiller.database.password");
    }

    // --------------- Registry Service -------------

    public String getRegistryServiceDomain(Instance instance) {
        return getProperty(instance.get() + ".regsitry.serive");
    }

    public String getRegistryServiceApiKey(Instance instance) {
        return getProperty(instance.get() + ".regsitry.serive.apikey");
    }

    public String getRegistryServiceAccessKey(Instance instance, String brand) {
        return getProperty(instance.get() + "." + brand + ".regsitry.serive.accessKey");
    }

    public String getPathToFreemarkerTemplateUuid(String type) {
        String pathToUuids = ("uuidRegistry/%s").replace("/", File.separator);
        switch (type) {
            case "series":
            case "event":
                return String.format(pathToUuids, "SeriesEventUuid.ftl");
            case "season":
                return String.format(pathToUuids, "SeasonUuid.ftl");
            case "episode":
                return String.format(pathToUuids, "EpisodeUuid.ftl");
            default:
                throw new TestRuntimeException("there is no matched content type to : " + type + " to get path for Uuid freemarker");
        }


    }
}
