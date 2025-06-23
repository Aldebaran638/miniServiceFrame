package com.miniprogram_frame.miniservice3.user.domain;

@jakarta.persistence.Embeddable
public class Address {
  private String city;
  private String street;
  private String zipcode;

  public String getCity() {
    return city;
  }

  public Address City(String city) {
    this.city = city;
    return this;
  }

  public String getStreet() {
    return street;
  }

  public Address Street(String street) {
    this.street = street;
    return this;
  }

  public String getZipcode() {
    return zipcode;
  }

  public Address Zipcode(String zipcode) {
    this.zipcode = zipcode;
    return this;
  }
}
