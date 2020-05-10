package com.myretail.casestudy.contoller;

import com.myretail.casestudy.data.model.ProductInformation;
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

  @GetMapping(
      value = ProductsControllerPath.PRODUCT_ID,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProductInformation> getProductInformation(@PathVariable int productId) {
    return new ResponseEntity<>(
        productInformationService.getProductInformationById(productId), HttpStatus.OK);
  }
}
