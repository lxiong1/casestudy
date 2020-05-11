package com.myretail.casestudy.contoller;

import com.myretail.casestudy.data.model.ProductCurrentPrice;
import com.myretail.casestudy.data.model.ProductInformation;
import com.myretail.casestudy.data.service.ProductCurrentPriceService;
import com.myretail.casestudy.data.service.ProductInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ProductsControllerPath.BASE)
public class ProductsController {
  @Autowired private ProductInformationService productInformationService;
  @Autowired private ProductCurrentPriceService productCurrentPriceService;

  @GetMapping(
      value = ProductsControllerPath.PRODUCT_ID,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProductInformation> getProductInformation(@PathVariable int productId) {
    return new ResponseEntity<>(
        productInformationService.getProductInformationById(productId), HttpStatus.OK);
  }

  @PutMapping(
      value = ProductsControllerPath.PRODUCT_ID,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> updateProductCurrentPrice(
      @RequestBody ProductCurrentPrice productCurrentPrice, @PathVariable int productId) {
    if (productCurrentPriceService.verifyProductCurrentPriceExists(productId)) {
      productCurrentPriceService.saveProductCurrentPrice(productCurrentPrice, productId);

      return new ResponseEntity<>(
          "Request Body with product id " + productId + " has been updated in the database",
          HttpStatus.OK);
    }

    productCurrentPriceService.saveProductCurrentPrice(productCurrentPrice, productId);

    return new ResponseEntity<>(
        "Request Body with product id " + productId + " has been created in the database",
        HttpStatus.CREATED);
  }
}
