package com.goldhub.resource_server.product.repository;

import com.goldhub.resource_server.product.domain.Product;
import com.goldhub.resource_server.product.domain.ProductType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByType(ProductType type);
}
