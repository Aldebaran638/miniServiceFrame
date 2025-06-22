package com.miniprogram_frame.miniservice2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 表示此类是一个控制器
@RequestMapping(path = "/demo") // 表示 URL 以 /demo 开头（应用路径之后）
public class MainController {
  @Autowired // 注入名为 userRepository 的 Bean
  // 该 Bean 由 Spring 自动生成，用于处理数据
  private UserRepository userRepository;

  @PostMapping(path = "/add") // 仅映射 POST 请求
  public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
    // @ResponseBody 表示返回的 String 是响应内容，而不是视图名称
    // @RequestParam 表示该参数来自 GET 或 POST 请求

    User n = new User();
    n.setName(name);
    n.setEmail(email);
    userRepository.save(n);
    return "Saved";
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    // 返回用户列表的 JSON 或 XML
    return userRepository.findAll();
  }
}