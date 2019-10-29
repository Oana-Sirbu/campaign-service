package de.rakuten.campaign.controller;

import de.rakuten.campaign.domain.CampaignDTO;
import de.rakuten.campaign.service.impl.CampaignServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

import static de.rakuten.campaign.commons.Constants.*;

@Validated
@RestController
@RequestMapping("/campaignapi")
public class CampaignController {
  private CampaignServiceImpl campaignService;

  @Autowired
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
    if (!validCampaignDateInterval(campaignDTO)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    CampaignDTO result = campaignService.save(campaignDTO);
    return new ResponseEntity<>(result, HttpStatus.OK);
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
      CampaignDTO newCampaignDTO = campaignService.update(campaignDTO);
    return new ResponseEntity<>(newCampaignDTO, HttpStatus.OK);
  }

  @DeleteMapping("/campaign/{id}")
  ResponseEntity<CampaignDTO> deleteCampaign(
      @PathVariable
          @Valid
          @NotNull
          @Pattern(regexp = UUID_REGEX_PATTERN, message = BAD_INPUT_ERROR_MESSAGE)
          String id) {
    campaignService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  private boolean validCampaignDateInterval(CampaignDTO campaignDTO) {
    LocalDateTime startDateTime = LocalDateTime.parse(campaignDTO.getStartDate());
    LocalDateTime endDateTime = LocalDateTime.parse(campaignDTO.getEndDate());
    return LocalDateTime.now().isBefore(startDateTime)
        || LocalDateTime.now().isBefore(endDateTime)
        || startDateTime.isBefore(endDateTime);
  }
}
