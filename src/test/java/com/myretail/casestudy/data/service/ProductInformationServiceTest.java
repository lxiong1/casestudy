package com.myretail.casestudy.data.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.casestudy.data.model.ProductCurrentPrice;
import com.myretail.casestudy.data.model.ProductInformation;
import com.myretail.casestudy.data.repository.ProductCurrentPriceRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class ProductInformationServiceTest {
  @InjectMocks ProductInformationService productInformationService;
  @Mock ProductCurrentPriceRepository productCurrentPriceRepository;
  @Mock RestTemplate restTemplate;
  @Mock ObjectMapper objectMapper;

  @Test
  void getProductInformationByIdShouldReturnProductInformationObject()
      throws JsonProcessingException {
    int productId = 12345;
    String title = "\"Some Title\"";

    ProductCurrentPrice productCurrentPrice =
        new ProductCurrentPrice(
            Integer.toString(productId), productId, new BigDecimal("1.00"), "USD");

    when(productCurrentPriceRepository.findByProductId(12345)).thenReturn(productCurrentPrice);
    when(restTemplate.getForObject(anyString(), any())).thenReturn("{\"title\":" + title + "}");

    ProductInformation productInformation =
        productInformationService.getProductInformationById(productId);

    assertThat(productInformation).isEqualToComparingOnlyGivenFields(productCurrentPrice);
    assertThat(productInformation).isEqualToComparingOnlyGivenFields(productId);
    assertThat(productInformation).isEqualToComparingOnlyGivenFields(title);
  }
}
