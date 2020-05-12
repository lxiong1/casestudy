package com.myretail.casestudy.data.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.myretail.casestudy.data.model.ProductCurrentPrice;
import com.myretail.casestudy.data.model.ProductInformation;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsInformationServiceIntegrationTest {
  @Autowired private ProductInformationService productInformationService;
  @Autowired private ProductCurrentPriceService productCurrentPriceService;

  private static final int PRODUCT_ID = 54456119;
  private static final BigDecimal VALUE = new BigDecimal("1.00");
  private static final String CURRENCY_CODE = "USD";

  @BeforeEach
  void setUp() {
    ProductCurrentPrice productCurrentPrice = new ProductCurrentPrice();
    productCurrentPrice.setValue(VALUE);
    productCurrentPrice.setCurrencyCode(CURRENCY_CODE);
    productCurrentPriceService.saveProductCurrentPrice(productCurrentPrice, PRODUCT_ID);
  }

  @Test
  void getProductInformation_ShouldReturnOkStatusWithProductInformation() {
    ProductInformation productInformation =
        productInformationService.getProductInformationById(PRODUCT_ID);

    assertThat(productInformation.getProductId()).isEqualTo(PRODUCT_ID);
    assertThat(productInformation.getName())
        .isEqualTo("Creamy Peanut Butter 40oz - Good &#38; Gather&#8482;");
    ProductCurrentPrice productCurrentPrice = productInformation.getProductCurrentPrice();
    assertThat(productCurrentPrice.getValue()).isEqualTo(VALUE);
    assertThat(productCurrentPrice.getCurrencyCode()).isEqualTo(CURRENCY_CODE);
  }
}
