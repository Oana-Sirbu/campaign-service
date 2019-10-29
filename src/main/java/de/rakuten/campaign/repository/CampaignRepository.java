package de.rakuten.campaign.repository;

import de.rakuten.campaign.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, String> {
  @Query(
      value =
          "SELECT * FROM Campaign WHERE to_date(start_date, 'yyyy-MM-dd') <= to_date('2019-09-26', 'yyyy-MM-dd')"
              + "AND to_date(end_date, 'yyyy-MM-dd') >= to_date('2019-09-26', 'yyyy-MM-dd')",
      nativeQuery = true)
  List<Campaign> getActiveCampaigns(String date);
}
