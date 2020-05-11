package com.myretail.casestudy.data.repository;

import com.myretail.casestudy.data.model.ProductInformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInformationRepository extends MongoRepository<ProductInformation, Integer> {
  ProductInformation existsByProductId(int productId);
}
