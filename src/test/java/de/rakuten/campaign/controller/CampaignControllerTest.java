package de.rakuten.campaign.controller;

import de.rakuten.campaign.domain.CampaignDTO;
import de.rakuten.campaign.service.impl.CampaignServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static de.rakuten.campaign.commons.TestUtils.getCampaignDTOList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CampaignControllerTest {
  @InjectMocks private CampaignController controller;

  @Mock private CampaignServiceImpl service;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    controller = new CampaignController(service);
  }

  @Test
  public void getActiveCampaign_validDate_responseStatusOk() {
    when(service.getActiveCampaigns(any())).thenReturn(getCampaignDTOList());

    ResponseEntity<List<CampaignDTO>> response = controller.getActiveCampaign("2019-12-26");

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void createCampaign_validCampaignDTO_responseStatusOK() throws ParseException {
    CampaignDTO campaign = getCampaignDTOList().get(0);
    when(service.save(any())).thenReturn(getCampaignDTOList().get(0));
    when(service.getActiveCampaigns(any())).thenReturn(new ArrayList<>());

    ResponseEntity<CampaignDTO> response = controller.createCampaign(campaign);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(campaign.getName(), response.getBody().getName());
  }

  @Test
  public void createCampaign_invalidDateCampaignDTO_responseStatusOK() throws ParseException {
    CampaignDTO campaign = getCampaignDTOList().get(0);
    campaign.setStartDate("2019-09-26T00:00:00.000Z");
    campaign.setEndDate("2019-09-29T00:00:00.000Z");

    when(service.save(any())).thenReturn(getCampaignDTOList().get(0));
    when(service.getActiveCampaigns(any())).thenReturn(new ArrayList<>());

    ResponseEntity<CampaignDTO> response = controller.createCampaign(campaign);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void createCampaign_alreadyActiveCampaignDTO_responseStatusBadRequest()
      throws ParseException {
    CampaignDTO campaign = getCampaignDTOList().get(0);
    when(service.save(any())).thenReturn(campaign);
    when(service.getActiveCampaigns(any())).thenReturn(getCampaignDTOList());

    ResponseEntity<CampaignDTO> response = controller.createCampaign(campaign);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void getCampaignById_campaignId_responseStatusOK() {
    CampaignDTO campaign = getCampaignDTOList().get(0);
    when(service.findById(campaign.getId())).thenReturn(campaign);

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
    when(service.update(any())).thenReturn(campaign);

    ResponseEntity<CampaignDTO> response = controller.putCampaign(campaign.getId(), campaign);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(campaign, response.getBody());
  }
}
