package com.myretail.casestudy.contoller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.myretail.casestudy.data.model.ProductCurrentPrice;
import com.myretail.casestudy.data.model.ProductInformation;
import com.myretail.casestudy.data.service.ProductCurrentPriceService;
import com.myretail.casestudy.data.service.ProductInformationService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {
  @InjectMocks private ProductsController productsController;
  @Mock private ProductInformationService productInformationService;
  @Mock private ProductCurrentPriceService productCurrentPriceService;

  @Test
  void getProductInformation_ShouldReturnOkStatusWithProductInformation() {
    int productId = 12345;

    ProductCurrentPrice productCurrentPrice =
        new ProductCurrentPrice(
            Integer.toString(productId), productId, new BigDecimal("1.00"), "USD");
    ProductInformation productInformation =
        new ProductInformation(productId, "name", productCurrentPrice);

    when(productInformationService.getProductInformationById(anyInt()))
        .thenReturn(productInformation);

    ResponseEntity<ProductInformation> response =
        productsController.getProductInformation(productId);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualToComparingFieldByField(productInformation);
  }

  @Test
  void
      updateProductInformation_WhenProductCurrentPriceAlreadyExistsInDatabase_ShouldReturnOkStatus() {
    int productId = 12345;

    ProductCurrentPrice productCurrentPrice =
        new ProductCurrentPrice(
            Integer.toString(productId), productId, new BigDecimal("1.00"), "USD");

    when(productCurrentPriceService.verifyProductCurrentPriceExists(anyInt())).thenReturn(true);

    ResponseEntity<String> response =
        productsController.updateProductCurrentPrice(productCurrentPrice, productId);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    assertThat(response.getBody())
        .isEqualTo(
            "Request Body with product id " + productId + " has been updated in the database");
  }

  @Test
  void
      updateProductInformation_WhenProductCurrentPriceNonExistentInDatabase_ShouldReturnCreatedStatus() {
    int productId = 12345;

    ProductCurrentPrice productCurrentPrice =
        new ProductCurrentPrice(
            Integer.toString(productId), productId, new BigDecimal("1.00"), "USD");

    when(productCurrentPriceService.verifyProductCurrentPriceExists(anyInt())).thenReturn(false);

    ResponseEntity<String> response =
        productsController.updateProductCurrentPrice(productCurrentPrice, productId);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
    assertThat(response.getBody())
        .isEqualTo(
            "Request Body with product id " + productId + " has been created in the database");
  }
}
