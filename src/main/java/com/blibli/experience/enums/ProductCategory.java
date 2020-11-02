package com.blibli.experience.enums;

public enum ProductCategory {

  DAILY("Daily"),
  ELECTRONIC("Electronic"),
  TOYS("Toys"),
  SPORT("Sport");

  private String productCategory;

  ProductCategory(String productCategory) {
    this.productCategory = productCategory;
  }

  public String getProductCategory() {
    return productCategory;
  }

}
