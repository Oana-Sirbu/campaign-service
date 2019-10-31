package de.rakuten.campaign.controller;

import de.rakuten.campaign.domain.CampaignDTO;
import de.rakuten.campaign.service.impl.CampaignServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.List;

import static de.rakuten.campaign.commons.Constants.*;
import static de.rakuten.campaign.commons.Util.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/campaignapi")
public class CampaignController {
  private CampaignServiceImpl campaignService;

  public CampaignController(CampaignServiceImpl campaignService) {
    this.campaignService = campaignService;
  }

  @GetMapping("/campaign/active/{date}")
  ResponseEntity<List<CampaignDTO>> getActiveCampaign(
      @PathVariable
          @Valid
          @NotNull
          @Pattern(regexp = DATE_REGEX_PATTERN, message = BAD_INPUT_ERROR_MESSAGE)
          String date) {
    List<CampaignDTO> campaignDTO = campaignService.getActiveCampaigns(date);
    return new ResponseEntity<>(campaignDTO, HttpStatus.OK);
  }

  @PostMapping("/campaign/")
  ResponseEntity<CampaignDTO> createCampaign(@RequestBody @Valid CampaignDTO campaignDTO) {
    if (validateCampaign(campaignDTO))
      throw new ConstraintViolationException(BAD_INPUT_ERROR_MESSAGE, new HashSet<>());

    CampaignDTO result = campaignService.save(campaignDTO);
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @GetMapping("/campaign/{id}")
  ResponseEntity<CampaignDTO> getCampaign(
      @PathVariable
          @Valid
          @NotNull
          @Pattern(regexp = UUID_REGEX_PATTERN, message = BAD_INPUT_ERROR_MESSAGE)
          String id) {
    CampaignDTO campaignDTO = campaignService.findById(id);
    return new ResponseEntity<>(campaignDTO, HttpStatus.OK);
  }

  @PutMapping("/campaign/{id}")
  ResponseEntity<CampaignDTO> putCampaign(
      @PathVariable
          @Valid
          @NotNull
          @Pattern(regexp = UUID_REGEX_PATTERN, message = BAD_INPUT_ERROR_MESSAGE)
          String id,
      @RequestBody @Valid CampaignDTO campaignDTO) {
    campaignDTO.setId(id);
    CampaignDTO newCampaignDTO = campaignService.update(campaignDTO);
    return new ResponseEntity<>(newCampaignDTO, HttpStatus.OK);
  }

  @DeleteMapping("/campaign/{id}")
  ResponseEntity<String> deleteCampaign(
      @PathVariable
          @Valid
          @NotNull
          @Pattern(regexp = UUID_REGEX_PATTERN, message = BAD_INPUT_ERROR_MESSAGE)
          String id) {
    campaignService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  private boolean validateCampaign(CampaignDTO campaignDTO) {
    if (!validCampaignDateInterval(campaignDTO)) {
      log.error("Invalid date interval");
      return true;
    }

    campaignDTO.setStartDate(getFormattedDate(campaignDTO.getStartDate()));
    campaignDTO.setEndDate(getFormattedDate(campaignDTO.getEndDate()));

    List<CampaignDTO> activeCampaigns =
        campaignService.getActiveCampaigns(campaignDTO.getEndDate());
    return existProductInActiveCampaign(campaignDTO, activeCampaigns);
  }
}
