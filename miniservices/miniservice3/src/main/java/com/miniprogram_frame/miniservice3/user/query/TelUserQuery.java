package com.miniprogram_frame.miniservice3.user.query;

import java.util.Map;

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
