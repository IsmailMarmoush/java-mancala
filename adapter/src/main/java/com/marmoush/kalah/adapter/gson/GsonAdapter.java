package com.marmoush.kalah.adapter.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marmoush.kalah.core.domain.port.Json;

import java.util.Map;

public class GsonAdapter implements Json {
  private final Gson gson;

  public GsonAdapter(Gson gson) {
    this.gson = gson;
  }

  @Override
  public <T> T toObject(String str, Class<T> tClass) {
    return gson.fromJson(str, tClass);
  }

  @Override
  public Map<String, Object> toMap(String str) {
    return gson.fromJson(str, new TypeToken<Map<String, Object>>() {}.getType());
  }

  @Override
  public <T> String toJson(T t) {
    return gson.toJson(t);
  }

}
