package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.images;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.iosstyles.factory.FilesMetadataProgramConcertoCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.ProgramConcertoImageSourceType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.transformers.ImageTransformer;
import com.nbcuni.test.cms.utils.transformers.ProgramJsonTransformer;
import com.nbcuni.test.cms.verification.tvecms.concertoapi.ImageListConcertoVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Aliaksei_Klimenka1 on 7/6/2016.
 */
public class TC15164_PublishProgramImageByCronIngest extends BaseAuthFlowTest {

    /**
     * Pre-conditions:
     * Make Sure IOS Concerto API instance is configured on brand
     * There is a Program 1
     * Make some changes within Program 1 on MPX
     *
     * Steps:
     * Step 1: Go to CMS as User
     * Verify: CMS Panel is present
     * Step 2: Run Cron
     * Verify: The cron run success
     *          The Program 1 from pre-condition is ingested
     *          There is a link on Publish Log
     * Step 3: Check publish log
     * Verify:  1 The POST request for Program 1 send to IOS Amazon Endpoint
     *          2 POST requests for program related Images are send to IOS Amazon Endpoint
     *              1.for 1965x1108 image source
     *              2.for 1600x900 image source
     *              3.for 3tile source roku
     *          The response status for both is success
     * Validation:
     * Get POST Image request and validate by scheme
     * Expected result:
     * The Scheme matched scheme below http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/image
     * {
     * uuid: "70db102c-52d8-4a78-8cb0-0cdf79713b3f",
     * itemType: "image",
     * revision: 3541,
     * title: "file_name",
     * href: "href for original image",
     * imageStyle: [{type: null,href: null}],
     * alt: "alt label for images object",
     * caption: null,
     * categories: [null],
     * tags: [null],
     * copyright: null,
     * credit: null,
     * description: null,
     * highRes: true,
     * source: "image source",
     * published: true,
     * programs: [{programUuid: null,
     * programItemType: "series",seasonUuid: null,
     * episodeUuid: null}]
     * }
     */

    private List<FilesMetadata> filesMetadataListIos;
    private List<FilesMetadata> filesMetadataListRoku;
    private MainRokuAdminPage mainRokuAdminPage;
    private String mpxIdOfTheProgram;
    private String updatedTitle;
    private String initialTitle;
    private MPXLayer mpxLayer;
    private List<FilesMetadata> filesMetadataListUpdated;

    @Autowired
    @Qualifier("imageStyles")
    private FilesMetadataProgramConcertoCreationStrategy metadataCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initFileMetadata() {
        filesMetadataListIos = metadataCreationStrategy.getListOfSourcesMetadaObject(
                ProgramConcertoImageSourceType.SOURCE_1600x900,
                ProgramConcertoImageSourceType.SOURCE_1965x1108
        );
        filesMetadataListRoku = metadataCreationStrategy.getListOfSourcesMetadaObject(
                ProgramConcertoImageSourceType.SOURCE_3TILE
        );
    }

    private void getMPXIdOfTheProgram() {
        Utilities.logInfoMessage("Check that android thumbnail is generated for OTT Program");
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        initialTitle = contentPage.getRandomAsset(ContentType.TVE_PROGRAM);
        mpxIdOfTheProgram = contentPage.getMPXId(initialTitle);
        //update program title in MPX API
        mpxLayer = new MPXLayer(brand, Config.getInstance().getRokuMPXOwnerID(brand, Instance.STAGE), mpxIdOfTheProgram);
        updatedTitle = initialTitle + "_new";
        mpxLayer.updateAssetTitleByAssetId(updatedTitle, mpxIdOfTheProgram);
    }

    @Test(groups = {"image_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void CheckImageAfterPublishByCronIngest(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.runCron(brand);
        //Pre-condition
        getMPXIdOfTheProgram();
        //Step 1-2
        mainRokuAdminPage.runCron(brand);
        String url = rokuBackEndLayer.getLogURL(brand);

        NodeApi NodeApi = new NodeApi(brand);
        GlobalProgramEntity globalProgramEntity = ProgramJsonTransformer.getGlobalProgramEntity(NodeApi.getProgramNodeByName(updatedTitle), brand);
        List<ImagesJson> expectedImagesJsons = ImageTransformer.getConcertoApiForProgram(globalProgramEntity.getImageSources(), brand);

        //Get Actual Post Request
        List<ImagesJson> actualImagesJsons = requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.FILE_IMAGE);

        softAssert.assertEquals(expectedImagesJsons, actualImagesJsons,
                "Image jsons are not equals", "Image jsons are equals", new ImageListConcertoVerificator());
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void returnUpdatedProgramTitle() {
        mpxLayer.updateAssetTitleByAssetId(initialTitle, mpxIdOfTheProgram);
        rokuBackEndLayer.updateMPXAssetById(mpxIdOfTheProgram);
    }
}
