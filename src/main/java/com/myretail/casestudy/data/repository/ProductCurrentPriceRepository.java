package com.myretail.casestudy.data.repository;

import com.myretail.casestudy.data.model.ProductCurrentPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCurrentPriceRepository
    extends MongoRepository<ProductCurrentPrice, Integer> {
  ProductCurrentPrice findByProductId(int productId);

  boolean existsByProductId(int productId);
}
