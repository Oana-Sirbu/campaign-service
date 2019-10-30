package de.rakuten.campaign.service.impl;

import de.rakuten.campaign.domain.ProductDTO;
import de.rakuten.campaign.mapper.ProductMapper;
import de.rakuten.campaign.model.Product;
import de.rakuten.campaign.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl
    extends ServiceImpl<Product, ProductDTO, ProductRepository, ProductMapper> {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public ProductServiceImpl(ProductRepository repository, ProductMapper mapper) {
    super(repository, mapper);
    this.productRepository = repository;
    this.productMapper = mapper;
  }
}
