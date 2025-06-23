package com.miniprogram_frame.miniservice3.user.repository;
import org.springframework.data.repository.CrudRepository;

import com.miniprogram_frame.miniservice3.user.domain.User;


public interface UserRepository extends CrudRepository<User, Integer> {

}
