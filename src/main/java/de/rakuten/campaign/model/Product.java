package de.rakuten.campaign.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "product")
public class Product {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Double price;

  @ManyToMany(mappedBy = "products")
  @NotFound(action = NotFoundAction.IGNORE)
  private List<Campaign> campaigns;
}
