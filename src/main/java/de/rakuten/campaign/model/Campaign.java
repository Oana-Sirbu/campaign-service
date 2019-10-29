package de.rakuten.campaign.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "campaign")
public class Campaign {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String startDate;

  @Column(nullable = false)
  private String endDate;

  @Column(nullable = false)
  private Integer points;

  @Column(nullable = false)
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "campaign_product",
      joinColumns = @JoinColumn(name = "campaign_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> products;
}
