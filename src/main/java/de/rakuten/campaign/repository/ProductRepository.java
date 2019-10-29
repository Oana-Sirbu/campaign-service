package de.rakuten.campaign.repository;

import de.rakuten.campaign.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {}
