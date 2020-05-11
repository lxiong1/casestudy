package com.myretail.casestudy.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product_current_price")
public class ProductCurrentPrice {
  @Id @JsonIgnore private String id;

  @NotNull @JsonIgnore private int productId;

  @NotNull
  @DecimalMin(value = "0.00")
  private BigDecimal value;

  @NotEmpty
  @JsonProperty("currency_code")
  private String currencyCode;

  public ProductCurrentPrice(
      String id,
      @NotEmpty int productId,
      @NotNull @DecimalMin(value = "0.00") BigDecimal value,
      @NotEmpty String currencyCode) {
    this.id = id;
    this.productId = productId;
    this.value = value;
    this.currencyCode = currencyCode;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public BigDecimal getValue() {
    return this.value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }
}
