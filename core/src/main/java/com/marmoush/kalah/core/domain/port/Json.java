package com.marmoush.kalah.core.domain.port;

import java.util.Map;

public interface Json {
  <T> T toObject(String str, Class<T> tClass);

  Map<String, Object> toMap(String str);

  <T> String toJson(T tclass);

}
