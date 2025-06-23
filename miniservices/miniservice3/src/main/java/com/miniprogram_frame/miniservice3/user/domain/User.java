package com.miniprogram_frame.miniservice3.user.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String email;
  private Integer age;
  @Embedded
  private Address address;
  private String profile;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getProfile() {
    return profile;
  }

  public void setProfile(String profile) {
    this.profile = profile;
  }

  // builder风格方法
  public User Name(String name) {
    this.name = name;
    return this;
  }

  public User Email(String email) {
    this.email = email;
    return this;
  }

  public User Age(Integer age) {
    this.age = age;
    return this;
  }

  public User Address(Address address) {
    this.address = address;
    return this;
  }

  public User Profile(String profile) {
    this.profile = profile;
    return this;
  }
}