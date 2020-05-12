package com.myretail.casestudy.contoller;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsControllerIntegrationTest {
  @Autowired private TestRestTemplate testRestTemplate;
  @LocalServerPort private int port;

  @Test
  void getProductInformation_ShouldReturnOkStatusWithProductInformation() {
    int productId = 54456119;

    ResponseEntity<String> response =
        testRestTemplate.getForEntity(
            "http://localhost:" + port + ProductsControllerPath.BASE + "/" + productId,
            String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    assertThat(response.getBody()).contains(Integer.toString(productId));
  }

  @Test
  void getProductInformation_WhenProductNotFound_ShouldReturnNotFoundStatus() {
    int nonExistentProductId = 11111111;

    ResponseEntity<String> response =
        testRestTemplate.getForEntity(
            "http://localhost:" + port + ProductsControllerPath.BASE + "/" + nonExistentProductId,
            String.class);

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
            "http://localhost:" + port + ProductsControllerPath.BASE + "/" + nonIntegerProductId,
            String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).contains("Type mismatch");
  }

  @Test
  void updateProductCurrentPrice_WhenProductCurrentPriceAlreadyExists_ShouldReturnOkStatus() {
    int productId = 13860428;

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

    RequestEntity<String> requestEntity =
        new RequestEntity<>(
            "{ \"value\": 14.49, \"currency_code\": \"USD\" }",
            httpHeaders,
            HttpMethod.PUT,
            URI.create("http://localhost:" + port + ProductsControllerPath.BASE + "/" + productId),
            String.class);

    ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    assertThat(response.getBody())
        .isEqualTo(
            "Request Body with product id " + productId + " has been updated in the database");
  }

  @Test
  void updateProductCurrentPrice_WhenProductCurrentPriceNonExistent_ShouldReturnCreatedStatus() {
    int productId = new Random().nextInt(1000000000);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

    RequestEntity<String> requestEntity =
        new RequestEntity<>(
            "{ \"value\": 14.49, \"currency_code\": \"USD\" }",
            httpHeaders,
            HttpMethod.PUT,
            URI.create("http://localhost:" + port + ProductsControllerPath.BASE + "/" + productId),
            String.class);

    ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
    assertThat(response.getBody())
        .isEqualTo(
            "Request Body with product id " + productId + " has been created in the database");
  }

  @Test
  void updateProductCurrentPrice_WhenProductIdNonInteger_ShouldReturnBadRequestStatus() {
    String nonIntegerProductId = "string";

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

    RequestEntity<String> requestEntity =
        new RequestEntity<>(
            "{ \"value\": 14.49, \"currency_code\": \"USD\" }",
            httpHeaders,
            HttpMethod.PUT,
            URI.create(
                "http://localhost:"
                    + port
                    + ProductsControllerPath.BASE
                    + "/"
                    + nonIntegerProductId),
            String.class);

    ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).contains("Type mismatch");
  }

  @Test
  void updateProductCurrentPrice_WhenProductCurrentPriceEmptyJson_ShouldReturnBadRequestStatus() {
    int productId = new Random().nextInt(1000000000);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

    RequestEntity<String> requestEntity =
        new RequestEntity<>(
            "{ }",
            httpHeaders,
            HttpMethod.PUT,
            URI.create("http://localhost:" + port + ProductsControllerPath.BASE + "/" + productId),
            String.class);

    ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody())
        .contains("Value and/or Currency Code in request body cannot be null");
  }

  @Test
  void updateProductCurrentPrice_WhenProductCurrentPriceNull_ShouldReturnBadRequestStatus() {
    int productId = new Random().nextInt(1000000000);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

    RequestEntity<String> requestEntity =
        new RequestEntity<>(
            null,
            httpHeaders,
            HttpMethod.PUT,
            URI.create("http://localhost:" + port + ProductsControllerPath.BASE + "/" + productId),
            String.class);

    ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).contains("Request body is missing");
  }

  @Test
  void updateProductCurrentPrice_WhenProductCurrentPriceBlank_ShouldReturnBadRequestStatus() {
    int productId = new Random().nextInt(1000000000);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

    RequestEntity<String> requestEntity =
        new RequestEntity<>(
            " ",
            httpHeaders,
            HttpMethod.PUT,
            URI.create("http://localhost:" + port + ProductsControllerPath.BASE + "/" + productId),
            String.class);

    ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).contains("Failed to read HTTP message");
  }

  @Test
  void updateProductCurrentPrice_WhenProductCurrentPriceEmpty_ShouldReturnBadRequestStatus() {
    int productId = new Random().nextInt(1000000000);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

    RequestEntity<String> requestEntity =
        new RequestEntity<>(
            "",
            httpHeaders,
            HttpMethod.PUT,
            URI.create("http://localhost:" + port + ProductsControllerPath.BASE + "/" + productId),
            String.class);

    ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).contains("Request body is missing");
  }
}
