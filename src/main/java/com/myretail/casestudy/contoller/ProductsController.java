package com.myretail.casestudy.contoller;

import com.myretail.casestudy.data.service.ProductInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductsController {
  @Autowired private ProductInformationService productInformationService;

  @GetMapping(value = "/{productId}")
  public ResponseEntity getProductInformation(@PathVariable("productId") int productId) {
    return new ResponseEntity<>(
        productInformationService.getProductInformationById(productId), HttpStatus.OK);
  }
}
