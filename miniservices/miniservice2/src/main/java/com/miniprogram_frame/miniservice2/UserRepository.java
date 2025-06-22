package com.miniprogram_frame.miniservice2;

import org.springframework.data.repository.CrudRepository;

// 这将由 Spring 自动实现为名为 userRepository 的 Bean
// CRUD 代表 创建、读取、更新、删除

public interface UserRepository extends CrudRepository<User, Integer> {

}
