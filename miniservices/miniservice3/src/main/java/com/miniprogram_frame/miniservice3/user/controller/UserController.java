package com.miniprogram_frame.miniservice3.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miniprogram_frame.miniservice3.user.domain.Address;
import com.miniprogram_frame.miniservice3.user.domain.User;
import com.miniprogram_frame.miniservice3.user.repository.UserRepository;

@Controller
@RequestMapping(path = "/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping(path = "/add")
  public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email,
      @RequestParam Integer age, @RequestParam String profile,
      @RequestParam String city, @RequestParam String street, @RequestParam String zipcode,
      @RequestParam String sex) {
    User n = new User()
        .Name(name)
        .Email(email)
        .Age(age)
        .Profile(profile)
        .Sex(User.Sex.valueOf(sex))
        .Address(new Address()
            .City(city)
            .Street(street)
            .Zipcode(zipcode));
    userRepository.save(n);
    return "Saved\n";
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping(path = "/user/{id}")
  public @ResponseBody Map<String, Object> getUser(@PathVariable Integer id) {
    com.miniprogram_frame.miniservice3.user.query.UserQuery query = new com.miniprogram_frame.miniservice3.user.query.SimpleUserQuery(
        userRepository);
    return query.query(id);
  }

  @GetMapping(path = "/user/{id}/tel")
  public @ResponseBody Map<String, Object> getUserWithTel(@PathVariable Integer id) {
    com.miniprogram_frame.miniservice3.user.query.UserQuery query = new com.miniprogram_frame.miniservice3.user.query.TelUserQuery(
        new com.miniprogram_frame.miniservice3.user.query.SimpleUserQuery(userRepository));
    return query.query(id);
  }

  @GetMapping(path = "/user/{id}/tel/history")
  public @ResponseBody Map<String, Object> getUserWithTelAndHistory(@PathVariable Integer id) {
    com.miniprogram_frame.miniservice3.user.query.UserQuery query = new com.miniprogram_frame.miniservice3.user.query.HistoryUserQuery(
        new com.miniprogram_frame.miniservice3.user.query.TelUserQuery(
            new com.miniprogram_frame.miniservice3.user.query.SimpleUserQuery(userRepository)));
    return query.query(id);
  }
}