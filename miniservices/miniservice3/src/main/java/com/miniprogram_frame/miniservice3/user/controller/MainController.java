package com.miniprogram_frame.miniservice3.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miniprogram_frame.miniservice3.user.domain.User;
import com.miniprogram_frame.miniservice3.user.repository.UserRepository;

@Controller

@RequestMapping(path = "/demo")

public class MainController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping(path = "/add")

  public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {

    User n = new User();
    n.setName(name);
    n.setEmail(email);
    userRepository.save(n);
    return "Saved";
  }

  @GetMapping(path = "/all")

  public @ResponseBody Iterable<User> getAllUsers() {

    return userRepository.findAll();
    
  }
}