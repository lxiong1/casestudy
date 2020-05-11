package com.myretail.casestudy.data.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.myretail.casestudy.data.model.ProductCurrentPrice;
import com.myretail.casestudy.data.repository.ProductCurrentPriceRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductCurrentPriceServiceTest {
  @InjectMocks ProductCurrentPriceService productCurrentPriceService;
  @Mock ProductCurrentPriceRepository productCurrentPriceRepository;

  private final int productId = 12345;

  @Test
  void saveProductCurrentPrice_ShouldSaveProductCurrentPriceToDatabase() {
    ProductCurrentPrice productCurrentPrice =
        new ProductCurrentPrice(
            Integer.toString(productId), productId, new BigDecimal("1.00"), "USD");

    ArgumentCaptor<ProductCurrentPrice> productCurrentPriceCaptor =
        ArgumentCaptor.forClass(ProductCurrentPrice.class);

    when(productCurrentPriceRepository.findByProductId(anyInt())).thenReturn(productCurrentPrice);

    productCurrentPriceService.saveProductCurrentPrice(productCurrentPrice, productId);

    verify(productCurrentPriceRepository).save(productCurrentPriceCaptor.capture());
    assertThat(productCurrentPriceCaptor).isEqualToComparingOnlyGivenFields(productCurrentPrice);
  }

  @Test
  void saveProductCurrentPrice_WhenValueIsNull_ShouldThrowIllegalArgumentException() {
    ProductCurrentPrice productCurrentPrice =
        new ProductCurrentPrice(Integer.toString(productId), productId, null, "USD");

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(
            () ->
                productCurrentPriceService.saveProductCurrentPrice(productCurrentPrice, productId));
  }

  @Test
  void saveProductCurrentPrice_WhenCurrencyCodeIsNull_ShouldThrowIllegalArgumentException() {
    ProductCurrentPrice productCurrentPrice =
        new ProductCurrentPrice(
            Integer.toString(productId), productId, new BigDecimal("1.00"), null);

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(
            () ->
                productCurrentPriceService.saveProductCurrentPrice(productCurrentPrice, productId));
  }
}
