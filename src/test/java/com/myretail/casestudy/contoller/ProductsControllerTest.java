package com.myretail.casestudy.contoller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.myretail.casestudy.data.model.ProductCurrentPrice;
import com.myretail.casestudy.data.model.ProductInformation;
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
  @InjectMocks ProductsController productsController;
  @Mock ProductInformationService productInformationService;

  @Test
  void getProductInformationShouldReturnOkStatusWithProductInformation() {
    int productId = 12345;

    ProductCurrentPrice productCurrentPrice =
        new ProductCurrentPrice(
            Integer.toString(productId), productId, new BigDecimal("1.00"), "USD");
    ProductInformation productInformation =
        new ProductInformation(productId, "name", productCurrentPrice);

    when(productInformationService.getProductInformationById(anyInt()))
        .thenReturn(productInformation);

    ResponseEntity<ProductInformation> responseEntity =
        productsController.getProductInformation(productId);

    assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isEqualToComparingFieldByField(productInformation);
  }
}
