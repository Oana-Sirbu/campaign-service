package de.rakuten.campaign.controller;

import de.rakuten.campaign.domain.CampaignDTO;
import de.rakuten.campaign.service.impl.CampaignServiceImpl;
import de.rakuten.campaign.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static de.rakuten.campaign.commons.TestUtils.getCampaignDTOList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CampaignControllerTest {
  @InjectMocks private CampaignController controller;

  @Mock private CampaignServiceImpl campaignService;
  @Mock private ProductServiceImpl productService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    controller = new CampaignController(campaignService, productService);
  }

  @Test
  public void getActiveCampaign_validDate_responseStatusOk() {
    when(campaignService.getActiveCampaigns(any())).thenReturn(getCampaignDTOList());

    ResponseEntity<List<CampaignDTO>> response = controller.getActiveCampaign("2019-12-26");

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void createCampaign_validCampaignDTO_responseStatusCreated() {
    CampaignDTO campaign = getCampaignDTOList().get(0);
    when(campaignService.save(any())).thenReturn(getCampaignDTOList().get(0));
    when(campaignService.getActiveCampaigns(any())).thenReturn(new ArrayList<>());

    ResponseEntity<CampaignDTO> response = controller.createCampaign(campaign);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(campaign.getName(), response.getBody().getName());
  }

  @Test
  public void createCampaign_invalidDateCampaignDTO_throwException() {
    CampaignDTO campaign = getCampaignDTOList().get(0);
    campaign.setStartDate("2019-09-26T00:00:00.000Z");
    campaign.setEndDate("2019-09-29T00:00:00.000Z");

    when(campaignService.save(any())).thenReturn(getCampaignDTOList().get(0));
    when(campaignService.getActiveCampaigns(any())).thenReturn(new ArrayList<>());

    assertThrows(ConstraintViolationException.class, () -> controller.createCampaign(campaign));
  }

  @Test
  public void createCampaign_alreadyActiveCampaignDTO_throwException() {
    CampaignDTO campaign = getCampaignDTOList().get(0);
    when(campaignService.save(any())).thenReturn(campaign);
    when(campaignService.getActiveCampaigns(any())).thenReturn(getCampaignDTOList());

    assertThrows(HttpClientErrorException.class, () -> controller.createCampaign(campaign));
  }

  @Test
  public void getCampaign_campaignId_responseStatusOK() {
    CampaignDTO campaign = getCampaignDTOList().get(0);
    when(campaignService.findById(campaign.getId())).thenReturn(campaign);

    ResponseEntity<CampaignDTO> response = controller.getCampaign(campaign.getId());

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(campaign.getId(), response.getBody().getId());
  }

  @Test
  public void deleteCampaign_campaignId_responseStatusOK() {
    ResponseEntity<String> response =
        controller.deleteCampaign(getCampaignDTOList().get(0).getId());

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void putCampaign_campaignIdAndCampaignDTO_responseStatusOK() {
    CampaignDTO campaign = getCampaignDTOList().get(0);
    when(campaignService.update(any())).thenReturn(campaign);

    ResponseEntity<CampaignDTO> response = controller.putCampaign(campaign.getId(), campaign);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(campaign, response.getBody());
  }

  @Test
  public void verifyCampaignDto_invalidName_returnViolation() {
    CampaignDTO campaignDTO = getCampaignDTOList().get(0);
    campaignDTO.setName("");

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<CampaignDTO>> violations = validator.validate(campaignDTO);

    assertFalse(violations.isEmpty());
  }
}
