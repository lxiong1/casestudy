package com.myretail.casestudy.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product_information")
public class ProductInformation {
  @NotNull
  @JsonProperty("product_id")
  @Id
  private int productId;

  @NotEmpty private String name;

  @NotNull
  @JsonProperty("current_price")
  private ProductCurrentPrice productCurrentPrice;

  public ProductInformation(
      @NotNull int productId,
      @NotEmpty String name,
      @NotNull ProductCurrentPrice productCurrentPrice) {
    this.productId = productId;
    this.name = name;
    this.productCurrentPrice = productCurrentPrice;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProductCurrentPrice getProductCurrentPrice() {
    return productCurrentPrice;
  }

  public void setProductCurrentPrice(ProductCurrentPrice productCurrentPrice) {
    this.productCurrentPrice = productCurrentPrice;
  }
}
