package de.rakuten.campaign.service.impl;

import de.rakuten.campaign.domain.CampaignDTO;
import de.rakuten.campaign.mapper.CampaignMapper;
import de.rakuten.campaign.repository.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static de.rakuten.campaign.commons.TestUtils.getCampaignDTOList;
import static de.rakuten.campaign.commons.TestUtils.getCampaignList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CampaignServiceImplTest {
  @InjectMocks private CampaignServiceImpl campaignService;
  @Mock private CampaignRepository campaignRepository;
  @Mock private CampaignMapper campaignMapper;

  @Test
  public void getByCustomerEmail_validCampaignDTO_equalsDto() {
    List<CampaignDTO> campaignDTO = getCampaignDTOList();
    when(campaignRepository.getActiveCampaigns(anyString())).thenReturn(getCampaignList());
    when(campaignMapper.convertToDto(any())).thenReturn(campaignDTO.get(0));

    List<CampaignDTO> response =
        campaignService.getActiveCampaigns(campaignDTO.get(0).getStartDate());

    assertEquals(campaignDTO, response);
  }

  @Test
  public void getByCustomerEmail_invalidCampaignDTO_emptyList() {
    List<CampaignDTO> campaignDTO = getCampaignDTOList();
    when(campaignRepository.getActiveCampaigns(anyString())).thenReturn(new ArrayList<>());
    List<CampaignDTO> response =
        campaignService.getActiveCampaigns(campaignDTO.get(0).getStartDate());

    assertNotEquals(campaignDTO.size(), response.size());
  }
}
