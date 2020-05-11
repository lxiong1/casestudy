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
    ProductInformation productInformation =
        productInformationService.getProductInformationById(13860428);

    assertThat(productInformation.getProductId()).isInstanceOf(Integer.class);
    assertThat(productInformation.getName()).isInstanceOf(String.class);
    ProductCurrentPrice productCurrentPrice = productInformation.getProductCurrentPrice();
    assertThat(productCurrentPrice.getValue()).isInstanceOf(BigDecimal.class);
    assertThat(productCurrentPrice.getCurrencyCode()).isInstanceOf(String.class);
  }
}
