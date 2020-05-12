package com.myretail.casestudy.data.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.myretail.casestudy.data.model.ProductCurrentPrice;
import com.myretail.casestudy.data.repository.ProductCurrentPriceRepository;
import java.math.BigDecimal;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCurrentPriceServiceIntegrationTest {
  @Autowired private ProductCurrentPriceService productCurrentPriceService;
  @Autowired private ProductCurrentPriceRepository productCurrentPriceRepository;

  @Test
  void getProductInformation_ShouldReturnOkStatusWithProductInformation() {
    int productId = new Random().nextInt(100000000);

    ProductCurrentPrice productCurrentPrice =
        new ProductCurrentPrice(
            Integer.toString(productId), productId, new BigDecimal("1.00"), "USD");

    productCurrentPriceService.saveProductCurrentPrice(productCurrentPrice, productId);

    assertThat(productCurrentPriceRepository.findByProductId(productId))
        .isInstanceOf(ProductCurrentPrice.class);
  }
}
