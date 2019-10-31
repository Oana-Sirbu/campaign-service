package de.rakuten.campaign.commons;

import de.rakuten.campaign.domain.CampaignDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static de.rakuten.campaign.commons.Constants.*;

@Slf4j
public class Util {
  public static boolean validCampaignDateInterval(CampaignDTO campaignDTO) {
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    try {
      startDateTime =
          LocalDateTime.parse(campaignDTO.getStartDate(), DateTimeFormatter.ISO_DATE_TIME);
      endDateTime = LocalDateTime.parse(campaignDTO.getEndDate(), DateTimeFormatter.ISO_DATE_TIME);
    } catch (DateTimeParseException e) {
      log.error("validCampaignDateInterval " + e.getMessage());
      throw new ConstraintViolationException(BAD_INPUT_ERROR_MESSAGE, new HashSet<>());
    }

    return LocalDateTime.now().isBefore(startDateTime)
        && LocalDateTime.now().isBefore(endDateTime)
        && startDateTime.isBefore(endDateTime);
  }

  public static boolean existProductInActiveCampaign(
      CampaignDTO campaignDTO, List<CampaignDTO> activeCampaigns) {
    for (CampaignDTO activeCampaign : activeCampaigns) {
      if (activeCampaign.getProducts().stream().anyMatch(campaignDTO.getProducts()::contains))
        throw new HttpClientErrorException(HttpStatus.CONFLICT, CAMPAIGN_EXIST_ERROR_MESSAGE);
    }
    return false;
  }

  public static String getFormattedDate(String stringDate) {
    SimpleDateFormat format1 = new SimpleDateFormat(RECEIVED_DATE_TIME_FORMAT);
    SimpleDateFormat format2 = new SimpleDateFormat(DATE_TIME_FORMAT);
    Date date;
    try {
      date = format1.parse(stringDate);
    } catch (ParseException e) {
      log.error("getFormattedDate " + e.getMessage());
      throw new ConstraintViolationException(BAD_INPUT_ERROR_MESSAGE, new HashSet<>());
    }
    return format2.format(date);
  }
}
