package com.miniprogram_frame.miniservice2;

import org.springframework.data.repository.CrudRepository;

import com.miniprogram_frame.miniservice2.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {

}
