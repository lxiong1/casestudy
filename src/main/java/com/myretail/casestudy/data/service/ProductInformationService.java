package com.myretail.casestudy.data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.casestudy.data.model.ProductCurrentPrice;
import com.myretail.casestudy.data.model.ProductInformation;
import com.myretail.casestudy.data.repository.ProductCurrentPriceRepository;
import com.myretail.casestudy.exception.JsonFieldNotFoundException;
import com.myretail.casestudy.exception.JsonParseException;
import com.myretail.casestudy.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductInformationService {
  @Autowired private ProductCurrentPriceRepository productCurrentPriceRepository;
  @Autowired private RestTemplate restTemplate;
  @Autowired ObjectMapper objectMapper;

  private static final String TITLE = "title";

  public ProductInformation getProductInformationById(int productId) {
    try {
      return createProductInformation(
          productId, getProductTitle(productId), getCurrentPrice(productId));
    } catch (RestClientException restClientException) {
      throw new ProductNotFoundException(
          "The redsky.target.com server is unable to process the request or the product could not be found with the given product id: "
              + productId,
          restClientException);
    }
  }

  private String getProductTitle(int productId) {
    String redSkyProductUrl = "https://redsky.target.com/v2/pdp/tcin/" + productId;
    String redSkyQuery =
        "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
    String redSkyProductFilteredUrl = redSkyProductUrl + redSkyQuery;
    String response = restTemplate.getForObject(redSkyProductFilteredUrl, String.class);

    JsonNode rootNode;

    try {
      rootNode = objectMapper.readTree(response);
    } catch (JsonProcessingException jsonProcessingException) {
      throw new JsonParseException(
          "Unable to parse the redsky.target.com server response", jsonProcessingException);
    }

    JsonNode titleNode = rootNode.findValue(TITLE);
    if (titleNode == null) {
      throw new JsonFieldNotFoundException(
          "The product with id " + productId + " does not have the given JSON field: " + TITLE);
    }

    return titleNode.asText();
  }

  private ProductCurrentPrice getCurrentPrice(int productId) {
    return productCurrentPriceRepository.findByProductId(productId);
  }

  private ProductInformation createProductInformation(
      int productId, String title, ProductCurrentPrice productCurrentPrice) {
    return new ProductInformation(productId, title, productCurrentPrice);
  }
}
