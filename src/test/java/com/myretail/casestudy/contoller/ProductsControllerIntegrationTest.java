package com.myretail.casestudy.contoller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsControllerIntegrationTest {
  @Autowired TestRestTemplate testRestTemplate;
  @LocalServerPort private int port;

  @Test
  void getProductInformation_ShouldReturnOkStatusWithProductInformation() {
    int productId = 13860428;

    ResponseEntity<String> response =
        testRestTemplate.getForEntity(
            "http://localhost:" + port + "/products/" + productId, String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    assertThat(response.getBody()).contains(Integer.toString(productId));
  }

  @Test
  void getProductInformation_WhenProductNotFound_ShouldReturnNotFoundStatus() {
    int nonExistentProductId = 11111111;

    ResponseEntity<String> response =
        testRestTemplate.getForEntity(
            "http://localhost:" + port + "/products/" + nonExistentProductId, String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody())
        .contains(
            "The redsky.target.com server is unable to process the request or the product could not be found with the given product id: "
                + nonExistentProductId);
  }

  @Test
  void getProductInformation_WhenProductIdNotInteger_ShouldReturnBadRequestStatus() {
    String nonIntegerProductId = "string";

    ResponseEntity<String> response =
        testRestTemplate.getForEntity(
            "http://localhost:" + port + "/products/" + nonIntegerProductId, String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).contains("Type mismatch");
  }
}
