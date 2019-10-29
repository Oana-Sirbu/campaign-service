package de.rakuten.campaign.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDTO implements Serializable {
  private String id;

  @NotEmpty private String name;

  @NotEmpty private String startDate;

  @NotEmpty private String endDate;

  @NotNull private Integer points;

  @NotNull @Valid private List<ProductDTO> products;
}