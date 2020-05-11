package com.myretail.casestudy.data.service;

import com.myretail.casestudy.data.model.ProductCurrentPrice;
import com.myretail.casestudy.data.repository.ProductCurrentPriceRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCurrentPriceService {
  @Autowired private ProductCurrentPriceRepository productCurrentPriceRepository;

  public boolean verifyProductCurrentPriceExists(int productId) {
    return productCurrentPriceRepository.existsByProductId(productId);
  }

  public void saveProductCurrentPrice(ProductCurrentPrice productCurrentPrice, int productId) {
    ProductCurrentPrice ProductCurrentPriceDocument =
        productCurrentPriceRepository.findByProductId(productId);

    if (ProductCurrentPriceDocument == null) {
      productCurrentPrice.setId(UUID.randomUUID().toString());
    } else {
      productCurrentPrice.setId(ProductCurrentPriceDocument.getId());
    }

    productCurrentPrice.setProductId(productId);

    productCurrentPriceRepository.save(productCurrentPrice);
  }
}
