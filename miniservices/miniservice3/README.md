本微服务使用转发类，也就是装饰器模式（Decorator Pattern），完成了极简的，可拓展性强的用户信息查询接口。

具体实现如下：

# 1.架构设计
本微服务分为一个查询用户信息的接口和三个查询用户信息的模块，分别为
- UserQuery：查询用户信息接口
- SimpleQuery：查询用户信息的基础模块，可以查询用户id，用户名name和email三种信息
- HistoryUserQuery：模拟查询用户信息的第二个模块，可以查询history一种信息
- TelUserQuery：模拟查询用户信息的第三个模块，可以查询tel一种信息。
# 2.转发类的编写

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
    /*
     * 笔记类型：代码补充说明
     * 
     * 这里应从数据库或服务获取真实浏览历史，暂时不实现
     */
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
    /*
     * 笔记类型：代码补充说明
     * 
     * 这里应从数据库或服务获取真实电话号码，暂时不实现
     */
    result.put("tel", "mock-tel-123456");
    
    return result;
  }
}
```

### 功能

补充查询tel信息

# 3.controller接口的实现
针对三个模块的不同查询方式，装饰器修饰模式使得controller接口能够轻松应对各种排列组合模式下的用户信息查询。具体代码如下：

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
# 4.可优化点

举个例子
针对/user/{id}/tel/history，需要写一个方法
针对/user/{id}/history/tel，却还需要写另一个方法
要是程序能够自动根据不同的路径，组装不同类型的query就好了，但是现在很显然没法实现