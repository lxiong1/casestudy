package com.myretail.casestudy.data.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.myretail.casestudy.data.model.ProductCurrentPrice;
import com.myretail.casestudy.data.model.ProductInformation;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsInformationIntegrationTest {
  @Autowired ProductInformationService productInformationService;

  @Test
  void getProductInformation_ShouldReturnOkStatusWithProductInformation() {
    int productId = 13860428;

    ProductInformation productInformation =
        productInformationService.getProductInformationById(productId);

    assertThat(productInformation.getProductId()).isEqualTo(productId);
    assertThat(productInformation.getName()).isEqualTo("The Big Lebowski (Blu-ray)");
    ProductCurrentPrice productCurrentPrice = productInformation.getProductCurrentPrice();
    assertThat(productCurrentPrice.getValue()).isEqualByComparingTo(new BigDecimal("13.49"));
    assertThat(productCurrentPrice.getCurrencyCode()).isEqualTo("USD");
  }
}
