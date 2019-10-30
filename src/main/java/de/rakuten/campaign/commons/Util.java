package de.rakuten.campaign.commons;

import de.rakuten.campaign.domain.CampaignDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Util {
  public static boolean validCampaignDateInterval(CampaignDTO campaignDTO) {
    LocalDateTime startDateTime =
        LocalDateTime.parse(campaignDTO.getStartDate(), DateTimeFormatter.ISO_DATE_TIME);
    LocalDateTime endDateTime =
        LocalDateTime.parse(campaignDTO.getEndDate(), DateTimeFormatter.ISO_DATE_TIME);
    return LocalDateTime.now().isBefore(startDateTime)
        && LocalDateTime.now().isBefore(endDateTime)
        && startDateTime.isBefore(endDateTime);
  }

  public static boolean existProductInActiveCampaign(
      CampaignDTO campaignDTO, List<CampaignDTO> activeCampaigns) {
    for (CampaignDTO activeCampaign : activeCampaigns) {
      if (activeCampaign.getProducts().stream().anyMatch(campaignDTO.getProducts()::contains)) {
        return true;
      }
    }
    return false;
  }

  public static String getFormattedDate(String stringDate) throws ParseException {
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = format1.parse(stringDate);
    return format2.format(date);
  }
}
