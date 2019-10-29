package de.rakuten.campaign.mapper;

import de.rakuten.campaign.domain.ProductDTO;
import de.rakuten.campaign.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends Mapper<Product, ProductDTO> {
  @Override
  public Class<Product> getEntityClass() {
    return Product.class;
  }

  @Override
  public Class<ProductDTO> getDtoClass() {
    return ProductDTO.class;
  }
}
