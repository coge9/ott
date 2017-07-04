package com.nbcuni.test.cms.collectservices.program;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.SourceMatcher;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.GlobalConstants;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceEntity;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceHelper;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dzianis_Kulesh on 2/6/2017.
 * <p>
 * Implementation of interface which collect info about program node from UI. Collect data for all brands both chiller and NON-chiller.
 */
public class UiProgramDataCollector implements ProgramDataCollector {

    private static final String PUBLISHED_STATE = "Published";
    private static final String UPDATED_DATE_FORMAT = "MM/dd/yyyy - hh:mm a";
    private CustomWebDriver webDriver;
    private String brand;
    private MainRokuAdminPage mainRokuAdminPage;
    private AppLib aid;

    public UiProgramDataCollector(CustomWebDriver webDriver, AppLib aid, String brand) {
        this.webDriver = webDriver;
        this.brand = brand;
        this.aid = aid;
        mainRokuAdminPage = new MainRokuAdminPage(webDriver, aid);
    }

    /**
     * *********************************************************************************
     * Method Name: collectProgramInfo
     * Description: main method for collecting program DATA
     * the Json file path
     *
     * @param assetTitle - title of the program under test.
     * @return GlobalProgramEntity
     * ***********************************************************************************
     */
    @Override
    public GlobalProgramEntity collectProgramInfo(String assetTitle) {
        EditTVEProgramContentPage editProgram;
        // opening content page.
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        // searching for program under test.
        searchByTitle(contentPage, assetTitle);
        // collect NODE published state.
        String publishedState = contentPage.getNodePublishState(1);
        // collect node updated date.
        ZonedDateTime updatedDate = getUpdatedDate(contentPage);
        // identify implementation of Edit Program Page
        editProgram = contentPage.clickEditLink(EditTVEProgramContentPage.class, assetTitle);
        // collect program info.
        GlobalProgramEntity program = getProgram(editProgram);
        // update with last updated date and pupblish status.
        program.setUpdatedDate(updatedDate);
        program.setPublished(publishedState.equals(PUBLISHED_STATE) ? true : false);
        // identify way for collecting channel reference.
        addChannelReference(program);
        return program;
    }

    @Override
    public GlobalProgramEntity collectRandomProgramInfo() {
        throw new UnsupportedOperationException("This method is unsupported for UIProgramDataCollector.java");
    }

    // searching element on content page by title.
    private void searchByTitle(ContentPage contentPage, String assetTitle) {
        contentPage.searchByType(ContentType.TVE_PROGRAM);
        contentPage.searchByTitle(assetTitle);
        contentPage.apply();
        checkItems(contentPage, assetTitle);
    }

    // check that there is only one item with define title. In case of duplicate throw error.
    private void checkItems(ContentPage contentPage, String assetTitle) {
        List<String> content = contentPage.getAllContentList();
        Iterator<String> iterator = content.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (item.equals(assetTitle)) {
                count++;
            }
        }
        Assertion.assertTrue(count != 0, "There are 0 items with title " + assetTitle, webDriver);
        Assertion.assertTrue(count == 1, "There are more than 1 item with title " + assetTitle, webDriver);
    }

    // getting updated date from content page.
    private ZonedDateTime getUpdatedDate(ContentPage contentPage) {
        String updatedDate = contentPage.getNodeUpdatedDate(1);
        LocalDateTime localDateTime = LocalDateTime.parse(updatedDate, DateTimeFormatter.ofPattern(UPDATED_DATE_FORMAT));
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, GlobalConstants.NY_ZONE);
        return zonedDateTime;
    }

    private void addChannelReference(GlobalProgramEntity program) {
        String programUuid = null;
        if (!StringUtils.isEmpty(program.getMpxAsset().getSeriesTitle())) {
            programUuid = new RegistryServiceHelper(brand).getUuid(new RegistryServiceEntity(new GlobalProgramEntity().setTitle(program.getMpxAsset().getSeriesTitle())));
        }
        ChannelReference channelReference = new ChannelReference();
        channelReference.setSeries(programUuid);
        program.getAssociations().getChannelReferenceAssociations().setChannelReference(channelReference);
    }

    // Collecting program data from page.
    private GlobalProgramEntity getProgram(EditTVEProgramContentPage editProgramPage) {
        GlobalProgramEntity programEntity = new GlobalProgramEntity();
        Series series = editProgramPage.onBasicBlock().getData();
        programEntity.setShowColor(editProgramPage.onBasicBlock().getShowColor());
        programEntity.getGeneralInfo().setSubhead(series.getGeneralInfo().getSubhead());
        programEntity.getGeneralInfo().setLongDescription(series.getGeneralInfo().getLongDescription());
        programEntity.getGeneralInfo().setTitle(series.getGeneralInfo().getTitle());
        programEntity.setPromotional(series.getPromotional());
        programEntity.getSeriesInfo().setGenre(series.getSeriesInfo().getGenre());
        programEntity.getSeriesInfo().setStatus(series.getSeriesInfo().getStatus());
        programEntity.setMpxAsset(editProgramPage.getMpxInfo());
        programEntity.setImageSources(editProgramPage.getImageSources(brand));
        programEntity.setSlugInfo(editProgramPage.onSlugTab().getSlug());
        getInfoFromDevelPage(editProgramPage, programEntity);
        return programEntity;
    }

    // Getting info from devel page (UUID of PROGRAM and IMAGES)
    private void getInfoFromDevelPage(EditTVEProgramContentPage editVideo, GlobalProgramEntity program) {
        DevelPage develPage = editVideo.openDevelPage();
        program.getGeneralInfo().setRevision(Integer.parseInt(develPage.getVid()));
        program.getGeneralInfo().setUuid(new RegistryServiceHelper(brand).getUuid(new RegistryServiceEntity(program)));
        getMedia(develPage, program);
    }

    // getting media for devel page for NON-chiller brand.
    private void getMedia(DevelPage develPage, GlobalProgramEntity program) {
        for (ImageSource source : program.getImageSources()) {
            SourceMatcher sourceEnumConstant = SourceMatcher.getSource(source.getName(), ContentType.TVE_PROGRAM, source.getPlatform());
            String uuid = develPage.getMediaUuidByMachineName(sourceEnumConstant.getMachineName());
            source.setUuid(uuid);
        }
    }

}
