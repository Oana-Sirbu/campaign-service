package de.rakuten.campaign.commons;

import de.rakuten.campaign.domain.CampaignDTO;
import de.rakuten.campaign.domain.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {
  public static List<CampaignDTO> getCampaignDTOList() {
    List<ProductDTO> products = new ArrayList<>();
    products.add(
        new ProductDTO()
            .builder()
            .id("0150e3ab-30b0-4ca5-affb-d8a33e70525c")
            .name("IPhone 8S")
            .price(299.5)
            .build());

    List<CampaignDTO> campaigns = new ArrayList<>();
    campaigns.add(
        new CampaignDTO()
            .builder()
            .id("945afe2b-2847-4a95-ad79-004462d2c76")
            .name("Cellphone special offers")
            .startDate("2019-12-26T00:00:00.000Z")
            .endDate("2019-12-29T00:00:00.000Z")
            .points(10)
            .products(products)
            .build());
    return campaigns;
  }
}
