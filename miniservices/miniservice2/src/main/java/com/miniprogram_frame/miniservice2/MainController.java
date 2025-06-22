package com.miniprogram_frame.miniservice2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * 笔记类型：知识学习
 * 学习对象：Spring 框架
 * 
 * @Controller表示此类是一个控制器
 */
@Controller

/*
 * 笔记类型：知识学习
 * 学习对象：Spring 框架
 * 
 * @RequestMapping(path = "/demo")：表示 URL 以 /demo 开头（应用路径之后）
 */
@RequestMapping(path = "/demo")

public class MainController {

  /*
   * 笔记类型：知识学习
   * 学习对象：Spring 框架
   * ？？？？？
   * 注入名为 userRepository 的 Bean
   * 
   * 该 Bean 由 Spring 自动生成，用于处理数据（？不是很懂）
   */
  @Autowired
  private UserRepository userRepository;

  /*
   * 笔记类型：知识学习
   * 学习对象：Spring 框架
   * 
   * @PostMapping(path = "/add")：仅映射 POST 请求
   */
  @PostMapping(path = "/add")

  /*
   * 笔记类型：知识学习
   * 学习对象：Spring 框架
   * 
   * @ResponseBody 表示返回的 String 是响应内容，而不是视图名称
   *  响应内容：返回的是字符串或对象本身，直接作为 HTTP 响应体
   *  视图名称：返回的是一个页面路径或模板名称，Spring 会去找对应的 HTML 页面
   * 
   * @RequestParam 表示该参数来自 GET 或 POST 请求
   */
  public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {

    User n = new User();
    n.setName(name);
    n.setEmail(email);
    userRepository.save(n);
    return "Saved";
  }

  /*
   * 笔记类型：知识学习
   * 学习对象：Spring 框架
   * 
   * @GetMapping(path = "/all")：仅映射 GET 请求
   */
  @GetMapping(path = "/all")

  public @ResponseBody Iterable<User> getAllUsers() {

    /*
     * 笔记类型：知识学习
     * 学习对象：Spring 框架
     * 
     * 返回用户列表的 JSON 或 XML
     */
    return userRepository.findAll();
    
  }
}