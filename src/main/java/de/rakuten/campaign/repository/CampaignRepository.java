package de.rakuten.campaign.repository;

import de.rakuten.campaign.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, String> {
  @Query(
      value =
          "SELECT * FROM Campaign WHERE to_timestamp(start_date, 'YYYY-MM-DD HH24:MI:SS') <= to_timestamp(?1, 'YYYY-MM-DD HH24:MI:SS')"
              + "AND to_timestamp(end_date, 'YYYY-MM-DD HH24:MI:SS') >= to_timestamp(?1, 'YYYY-MM-DD HH24:MI:SS')",
      nativeQuery = true)
  List<Campaign> getActiveCampaigns(String date);
}
