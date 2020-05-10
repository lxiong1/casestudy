package com.myretail.casestudy.data.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.myretail.casestudy.data.model.ProductCurrentPrice;
import com.myretail.casestudy.data.model.ProductInformation;
import com.myretail.casestudy.data.repository.ProductCurrentPriceRepository;
import com.myretail.casestudy.exception.JsonFieldNotFoundException;
import com.myretail.casestudy.exception.JsonParseException;
import com.myretail.casestudy.exception.ProductNotFoundException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class ProductInformationServiceTest {
  @InjectMocks ProductInformationService productInformationService;
  @Mock ProductCurrentPriceRepository productCurrentPriceRepository;
  @Mock RestTemplate restTemplate;
  @Mock ObjectMapper objectMapper;

  private final int productId = 12345;

  @Test
  void getProductInformationById_ShouldReturnProductInformationObject()
      throws JsonProcessingException {
    String title = "title";
    String titleName = "Some Title";
    String titleKey = "\"" + title + "\"";
    String titleValue = "\"" + titleName + "\"";

    ProductCurrentPrice productCurrentPrice =
        new ProductCurrentPrice(
            Integer.toString(productId), productId, new BigDecimal("1.00"), "USD");

    when(restTemplate.getForObject(anyString(), any()))
        .thenReturn("{" + titleKey + ":" + titleValue + "}");
    when(objectMapper.readTree(anyString())).thenReturn(createJsonNode(title, titleName));
    when(productCurrentPriceRepository.findByProductId(12345)).thenReturn(productCurrentPrice);

    ProductInformation productInformation =
        productInformationService.getProductInformationById(productId);

    assertThat(productInformation).isEqualToComparingOnlyGivenFields(productCurrentPrice);
    assertThat(productInformation).isEqualToComparingOnlyGivenFields(productId);
    assertThat(productInformation).isEqualToComparingOnlyGivenFields(titleValue);
  }

  @Test
  void getProductInformationById_WhenProductNonExistent_ShouldThrowProductNotFoundException() {
    when(restTemplate.getForObject(anyString(), any())).thenThrow(RestClientException.class);

    assertThatExceptionOfType(ProductNotFoundException.class)
        .isThrownBy(() -> productInformationService.getProductInformationById(productId));
  }

  @Test
  void
      getProductInformationById_WhenJsonResponseDoesNotContainTitle_ShouldThrowJsonFieldNotFoundException()
          throws JsonProcessingException {
    when(restTemplate.getForObject(anyString(), any())).thenReturn("{}");
    when(objectMapper.readTree(anyString())).thenReturn(createJsonNode("", ""));

    assertThatExceptionOfType(JsonFieldNotFoundException.class)
        .isThrownBy(() -> productInformationService.getProductInformationById(productId));
  }

  @Test
  void getProductInformationById_WhenJsonResponseUnableToBeParsed_ShouldThrowJsonParseException()
      throws JsonProcessingException {
    when(restTemplate.getForObject(anyString(), any())).thenReturn("{}");
    when(objectMapper.readTree(anyString())).thenThrow(JsonProcessingException.class);

    assertThatExceptionOfType(JsonParseException.class)
        .isThrownBy(() -> productInformationService.getProductInformationById(productId));
  }

  private ObjectNode createJsonNode(String key, String value) {
    return new ObjectMapper().createObjectNode().put(key, value);
  }
}
