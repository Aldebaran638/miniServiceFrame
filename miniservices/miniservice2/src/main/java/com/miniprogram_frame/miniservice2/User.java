package com.miniprogram_frame.miniservice2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/*
 * 笔记类型：知识学习
 * 学习对象：Spring 框架
 *  
 * 已知下文@Entity注解会为User类自动建表。
 * 如果表已经存在，那么Spring会产生什么行为取决于application.properties的
 *  spring.jpa.hibernate.ddl-auto参数
 * 
 * 参数列表：
 *  none：什么都不做。✅ 绝对安全，适合生产环境
 *  validate：只验证实体类和数据库表结构是否一致，❌ 如果不一致会启动报错
 *  update：尝试更新数据库表结构，⚠️ 如果字段不存在会帮你加字段，可能不符合预期
 *  create：每次启动都会删除表然后重建，❗ 会清空所有数据
 *  create-drop：启动创建表，程序关闭时自动删除表，⚠️ 只适合临时测试
 */

/*
 * 笔记类型：知识学习
 * 学习对象：Spring 框架
 * 
 * 该注解告诉Spring:
 *  1.这是一个数据库表， Spring需要为它自动建表
 *    默认表名=实体类名（不区分大小写就纯小写，否则包含大写）。
 *    @Table注解可以自定义表名，例：@Table(name = "my_table_name")
 *  2.将此类映射为数据库表，
 *    类中的每个字段（除非加了 @Transient）都会映射为数据库表中的一列。
 */
@Entity

public class User {

  /*
   * 笔记类型：知识学习
   * 学习对象：Spring 框架
   * 
   * 这一段代码（getId()以上）等价于
   * CREATE TABLE user (
   * id INT PRIMARY KEY AUTO_INCREMENT,
   * name VARCHAR(255),
   * email VARCHAR(255)
   * );
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String email;

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
}