本微服务使用静态工厂设计模式、Stream流+Lambda表达式和限制符编码技巧，完成了极简且可拓展的任务管理接口。

具体实现如下：

# 1.架构设计

## (1).领域模型

本微服务共一个领域模型，分别为
- Task：任务信息领域模型

## (2).查询与管理接口

本微服务提供任务的增删改查接口，并支持按状态、优先级等条件查询。

# 2.静态工厂设计模式+枚举类+限制符编码方式编写领域模型

## (1).TaskStatus与TaskPriority枚举

```java
public enum TaskStatus {
  TODO, IN_PROGRESS, DONE
}

public enum TaskPriority {
  LOW, MEDIUM, HIGH
}
```

## (2).Task领域模型（限制符编码方式）

```java
@Entity
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  private String description;
  private TaskStatus status;
  private TaskPriority priority;
  private LocalDateTime createdAt;
  // 所有字段均为private，体现封装性
  // ...getter/setter均为public
  // 构造方法为private，仅允许工厂创建
  private Task() {}
  // ...省略getter/setter...
}
```

## (3).TaskFactory静态工厂

```java
public class TaskFactory {
  public static Task create(String title, String description, TaskPriority priority) {
    Task task = new Task();
    task.setTitle(title);
    task.setDescription(description);
    task.setPriority(priority);
    task.setStatus(TaskStatus.TODO);
    task.setCreatedAt(LocalDateTime.now());
    return task;
  }
}
```

## (4).泛型限制符的实际应用

```java
// TaskService接口
<T extends Comparable<? super T>> List<Task> findTopNTasks(List<Task> tasks, int n);
```
该方法通过泛型上界限制，保证传入的类型T必须具备可比较性，体现了类型安全和灵活性。

# 3.Service层用Stream流+Lambda表达式

```java
public List<Task> findByStatus(TaskStatus status) {
  return taskRepository.findAll()
    .stream()
    .filter(task -> task.getStatus() == status)
    .collect(Collectors.toList());
}
```

# 4.controller接口的实现

## (1).新增任务

```java
@PostMapping("/tasks")
public String addTask(@RequestParam String title, @RequestParam String description, @RequestParam TaskPriority priority) {
  Task task = TaskFactory.create(title, description, priority);
  taskRepository.save(task);
  return "Saved";
}
```

## (2).查询任务

```java
@GetMapping("/tasks/{id}")
public Task getTask(@PathVariable Integer id) {
  return taskRepository.findById(id).orElse(null);
}

@GetMapping("/tasks")
public List<Task> getTasksByStatus(@RequestParam TaskStatus status) {
  return taskService.findByStatus(status);
}
```

# 5.示例访问数据（URL）

- 新增任务（POST）

指令：
  `curl http://localhost:8080/tasks -d title=Test -d description=Demo -d priority=HIGH`

输出：
```bash
Saved
```

- 查询任务

指令：
  `curl http://localhost:8080/tasks/1`

输出：
```bash
{
  "id":1,
  "title":"Test",
  "description":"Demo",
  "status":"TODO",
  "priority":"HIGH",
  "createdAt":"2025-06-24T02:23:33.093128"
}
```

- 查询指定状态的任务

指令：
  `curl http://localhost:8080/tasks?status=TODO`

输出：
```bash
[
  {
    "id":1,
    "title":"Test",
    "description":"Demo",
    "status":"TODO",
    "priority":"HIGH",
    "createdAt":"2025-06-24T02:23:33.093128"
  }
]
```
