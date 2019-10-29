package de.rakuten.campaign.mapper;

import de.rakuten.campaign.domain.CampaignDTO;
import de.rakuten.campaign.model.Campaign;
import org.springframework.stereotype.Component;

@Component
public class CampaignMapper extends Mapper<Campaign, CampaignDTO> {
  @Override
  public Class<Campaign> getEntityClass() {
    return Campaign.class;
  }

  @Override
  public Class<CampaignDTO> getDtoClass() {
    return CampaignDTO.class;
  }
}
