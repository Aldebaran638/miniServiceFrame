package com.miniprogram_frame.miniservice3.user.query;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.miniprogram_frame.miniservice3.user.domain.Address;
import com.miniprogram_frame.miniservice3.user.domain.User;
import com.miniprogram_frame.miniservice3.user.repository.UserRepository;

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
      result.put("age", user.getAge());
      result.put("profile", user.getProfile());
      Address address = user.getAddress();
      if (address != null) {
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("city", address.getCity());
        addressMap.put("street", address.getStreet());
        addressMap.put("zipcode", address.getZipcode());
        result.put("address", addressMap);
      }
    }
    return result;
  }
}
