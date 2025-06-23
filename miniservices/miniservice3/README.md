本微服务使用转发类，也就是装饰器模式（Decorator Pattern），和builder设计模式，完成了极简的，可拓展性强的用户信息查询接口。

具体实现如下：

# 1.架构设计

## (1).领域模型

本微服务共一个领域模型，分别为
- User：用户信息领域模型

## (2).查询接口

本微服务分为一个查询用户信息的接口和三个查询用户信息的模块，分别为
- UserQuery：查询用户信息接口
- SimpleQuery：查询用户信息的基础模块，可以查询用户id，用户名name和email三种信息
- HistoryUserQuery：模拟查询用户信息的第二个模块，可以查询history一种信息
- TelUserQuery：模拟查询用户信息的第三个模块，可以查询tel一种信息。

# 2.builder设计模式编写领域模型

## (1).Address领域模型（为User类的一个字段）

### 核心代码
```java
public class Address {
  private String city;
  private String street;
  private String zipcode;

  // builder风格方法
  public Address City(String city) {this.city = city;return this;}
  public Address Street(String street) {this.street = street;return this;}
  public Address Zipcode(String zipcode) {this.zipcode = zipcode;return this;}
}
```
## (2).User领域模型（为User类的一个字段）

### 核心代码
```java
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

  // builder风格方法
  public User Name(String name) {this.name = name;return this;}
  public User Email(String email) {this.email = email;return this;}
  public User Age(Integer age) {this.age = age;return this;}
  public User Address(Address address) {this.address = address;return this;}
  public User Profile(String profile) {this.profile = profile;return this;}
}
```

# 3.转发类的编写

## (1).UserQuery接口

### 核心代码

```java
public interface UserQuery {
  Map<String, Object> query(Integer id);
}
```

## (2).SimpleUserQuery

### 核心代码

```java
public class SimpleUserQuery implements UserQuery {
  private final UserRepository userRepository;

  public SimpleUserQuery(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Map<String, Object> query(Integer id) {
    Map<String, Object> result = new HashMap<>();
    Optional<User> userOpt = userRepository.findById(id);
    if (userOpt.isPresent()) {
      User user = userOpt.get();
      result.put("id", user.getId());
      result.put("name", user.getName());
      result.put("email", user.getEmail());
    }
    return result;
  }
}
```

### 功能

查询用户基础信息（id、name、email）。

## (3).HistoryUserQuery

### 核心代码

```java
public class HistoryUserQuery implements UserQuery {
  private final UserQuery next;

  public HistoryUserQuery(UserQuery next) {
    this.next = next;
  }

  @Override
  public Map<String, Object> query(Integer id) {
    Map<String, Object> result = next.query(id);
    // 这里应从数据库或服务获取真实浏览历史，暂时不实现
    result.put("history", "mock-history");
    return result;
  }
}
```

### 功能

补充查询history信息。

### 功能

定义所有用户查询装饰器的统一接口。

## (4).TelUserQuery

### 核心代码

```java
public class TelUserQuery implements UserQuery {
  private final UserQuery next;

  public TelUserQuery(UserQuery next) {
    this.next = next;
  }

  @Override
  public Map<String, Object> query(Integer id) {
    Map<String, Object> result = next.query(id);
    // 这里应从数据库或服务获取真实电话号码，暂时不实现
    result.put("tel", "mock-tel-123456");
    
    return result;
  }
}
```

### 功能

补充查询tel信息

# 4.controller接口的实现

## (1).针对User领域模型，使用builder风格的构造代码构造User实例。具体代码如下：

```java
  @PostMapping(path = "/add")
  public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email,
      @RequestParam Integer age, @RequestParam String profile,
      @RequestParam String city, @RequestParam String street, @RequestParam String zipcode) {
    User n = new User()
        .Name(name)
        .Email(email)
        .Age(age)
        .Profile(profile)
        .Address(new Address()
            .City(city)
            .Street(street)
            .Zipcode(zipcode));
    userRepository.save(n);
    return "Saved";
  }
```

## (2).针对三个模块的不同查询方式，装饰器修饰模式使得controller接口能够轻松应对各种排列组合模式下的用户信息查询。具体代码如下：

```java
  @GetMapping(path = "/user/{id}")
  public @ResponseBody Map<String, Object> getUser(@PathVariable Integer id) {
    com.miniprogram_frame.miniservice3.user.query.UserQuery query =
        new com.miniprogram_frame.miniservice3.user.query.SimpleUserQuery(userRepository);
    return query.query(id);
  }

  @GetMapping(path = "/user/{id}/tel")
  public @ResponseBody Map<String, Object> getUserWithTel(@PathVariable Integer id) {
    com.miniprogram_frame.miniservice3.user.query.UserQuery query =
        new com.miniprogram_frame.miniservice3.user.query.TelUserQuery(
            new com.miniprogram_frame.miniservice3.user.query.SimpleUserQuery(userRepository));
    return query.query(id);
  }

  @GetMapping(path = "/user/{id}/tel/history")
  public @ResponseBody Map<String, Object> getUserWithTelAndHistory(@PathVariable Integer id) {
    com.miniprogram_frame.miniservice3.user.query.UserQuery query =
        new com.miniprogram_frame.miniservice3.user.query.HistoryUserQuery(
            new com.miniprogram_frame.miniservice3.user.query.TelUserQuery(
                new com.miniprogram_frame.miniservice3.user.query.SimpleUserQuery(userRepository)));
    return query.query(id);
  }
```
# 5.可优化点

## (1)

举个例子
针对/user/{id}/tel/history，需要写一个方法
针对/user/{id}/history/tel，却还需要写另一个方法
要是程序能够自动根据不同的路径，组装不同类型的query就好了，但是现在很显然没法实现

# 6.拓展代码指南
## (1).为User表添加新的字段sex

### ①.在domain/User.java文件中添加字段及builder方法
```java
private String sex;
public String getSex() { return sex; }
public void setSex(String sex) { this.sex = sex; }
public User Sex(String sex) { this.sex = sex; return this; }
```

### ②.在controller/UserController.java文件中添加参数支持
```java
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
      .Sex(sex) // 新增字段
      .Address(new Address()
          .City(city)
          .Street(street)
          .Zipcode(zipcode));
  userRepository.save(n);
  return "Saved";
}
```

## (2).假设要编写一个新的路径接口/user/{id}/history/tel，应该如何做

### ①.在controller/UserController.java文件中添加如下方法
```java
@GetMapping(path = "/user/{id}/history/tel")
public @ResponseBody Map<String, Object> getUserWithHistoryAndTel(@PathVariable Integer id) {
  com.miniprogram_frame.miniservice3.user.query.UserQuery query =
      new com.miniprogram_frame.miniservice3.user.query.TelUserQuery(
          new com.miniprogram_frame.miniservice3.user.query.HistoryUserQuery(
              new com.miniprogram_frame.miniservice3.user.query.SimpleUserQuery(userRepository)));
  return query.query(id);
}
```

### ②.如需删除某个接口，直接在controller/UserController.java文件中删除对应方法即可
```java
// 删除如下方法即可
@GetMapping(path = "/user/{id}/tel/history")
public @ResponseBody Map<String, Object> getUserWithTelAndHistory(@PathVariable Integer id) {
  com.miniprogram_frame.miniservice3.user.query.UserQuery query =
      new com.miniprogram_frame.miniservice3.user.query.HistoryUserQuery(
          new com.miniprogram_frame.miniservice3.user.query.TelUserQuery(
              new com.miniprogram_frame.miniservice3.user.query.SimpleUserQuery(userRepository)));
  return query.query(id);
}
```

# 7.示例访问数据（URL）

- 新增用户（POST）

指令：

  `curl http://localhost:8080/demo/add -d name=zhangsan -d email=zhangsan@example.com -d age=20 -d profile=student -d city=Beijing -d street=zhongguancun -d zipcode=100080`

输出：
```bash
Saved
```

- 查询基础用户信息

指令：
  `curl http://localhost:8080/demo/user/1`

输出：
```bash
{
  "address":
    {
      "zipcode":"100080",
      "city":"Beijing",
      "street":"zhongguancun"
    },
  "profile":"student",
  "name":"zhangsan",
  "id":1,
  "email":"zhangsan@example.com",
  "age":20
}
```

- 查询基础信息+电话

指令：
  `curl http://localhost:8080/demo/user/1/tel`

输出：
```bash
{
  "address":
    {
      "zipcode":"100080",
      "city":"Beijing",
      "street":"zhongguancun"
    },
  "profile":"student",
  "name":"zhangsan",
  "tel":"mock-tel-123456",
  "id":1,
  "history":"mock-history",
  "email":"zhangsan@example.com",
  "age":20
}
```

- 查询基础信息+电话+历史

指令：
  `curl http://localhost:8080/demo/user/1/tel/history`

输出：
```bash
{
  "address":
    {
      "zipcode":"100080",
      "city":"Beijing",
      "street":"zhongguancun"
    },
  "profile":"student",
  "name":"zhangsan",
  "tel":"mock-tel-123456",
  "id":1,
  "history":"mock-history",
  "email":"zhangsan@example.com",
  "age":20
}
```
