package com.myretail.casestudy.data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.casestudy.data.model.ProductCurrentPrice;
import com.myretail.casestudy.data.model.ProductInformation;
import com.myretail.casestudy.data.repository.ProductCurrentPriceRepository;
import com.myretail.casestudy.exception.JsonFieldNotFoundException;
import com.myretail.casestudy.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductInformationService {
  @Autowired private ProductCurrentPriceRepository productCurrentPriceRepository;
  @Autowired private RestTemplate restTemplate;

  private static final String TITLE = "title";

  public ProductInformation getProductInformationById(int productId) {
    try {
      return createProductInformation(
          productId, getProductTitle(productId), getCurrentPrice(productId));
    } catch (RestClientException restClientException) {
      throw new ProductNotFoundException(
          "The product could not be found with the given product id: " + productId,
          restClientException);
    } catch (JsonProcessingException jsonProcessingException) {
      throw new JsonFieldNotFoundException(
          "The product with id " + productId + " does not have the given JSON field: " + TITLE,
          jsonProcessingException);
    }
  }

  private String getProductTitle(int productId) throws JsonProcessingException {
    String redSkyProductUrl = "https://redsky.target.com/v2/pdp/tcin/" + productId;
    String redSkyQuery =
        "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
    String redSkyProductFilteredUrl = redSkyProductUrl + redSkyQuery;
    String redSkyProductData = restTemplate.getForObject(redSkyProductFilteredUrl, String.class);

    return new ObjectMapper().readTree(redSkyProductData).findValue(TITLE).asText();
  }

  private ProductCurrentPrice getCurrentPrice(int productId) {
    return productCurrentPriceRepository.findByProductId(productId);
  }

  private ProductInformation createProductInformation(
      int productId, String title, ProductCurrentPrice productCurrentPrice) {
    ProductInformation productInformation = new ProductInformation();
    productInformation.setProductId(productId);
    productInformation.setName(title);
    productInformation.setProductCurrentPrice(productCurrentPrice);

    return productInformation;
  }
}
