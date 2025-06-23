package com.miniprogram_frame.miniservice3.user.query;

import java.util.Map;

public class HistoryUserQuery implements UserQuery {
  private final UserQuery next;

  public HistoryUserQuery(UserQuery next) {
    this.next = next;
  }

  @Override
  public Map<String, Object> query(Integer id) {
    Map<String, Object> result = next.query(id);
    // 这里应从数据库或服务获取真实浏览历史
    result.put("history", "mock-history");
    return result;
  }
}
