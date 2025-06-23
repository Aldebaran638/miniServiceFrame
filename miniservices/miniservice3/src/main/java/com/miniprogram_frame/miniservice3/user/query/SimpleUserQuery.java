package com.miniprogram_frame.miniservice3.user.query;

import com.miniprogram_frame.miniservice3.user.domain.User;
import com.miniprogram_frame.miniservice3.user.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
