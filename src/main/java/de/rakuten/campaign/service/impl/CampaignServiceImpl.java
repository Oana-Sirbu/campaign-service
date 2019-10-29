package de.rakuten.campaign.service.impl;

import de.rakuten.campaign.domain.CampaignDTO;
import de.rakuten.campaign.mapper.CampaignMapper;
import de.rakuten.campaign.model.Campaign;
import de.rakuten.campaign.repository.CampaignRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CampaignServiceImpl
    extends ServiceImpl<Campaign, CampaignDTO, CampaignRepository, CampaignMapper> {
  private final CampaignRepository campaignRepository;
  private final CampaignMapper campaignMapper;

  protected CampaignServiceImpl(CampaignRepository repository, CampaignMapper mapper) {
    super(repository, mapper);
    this.campaignRepository = repository;
    this.campaignMapper = mapper;
  }

  public List<CampaignDTO> getActiveCampaigns(String date) {
    log.info("Finding active campaign by the following date : {}", date);
    List<CampaignDTO> result = new ArrayList<>();
    campaignRepository
        .getActiveCampaigns(date)
        .forEach(activeCampaign -> result.add(campaignMapper.convertToDto(activeCampaign)));
    return result;
  }
}
