package com.fetch.receiptprocessor.dao.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store<K, V> {
  private final Map<K, V> cache = new ConcurrentHashMap<>();

  public V get(K key) {
    return cache.get(key);
  }

  public void put(K key, V value) {
    cache.put(key, value);
  }

}
