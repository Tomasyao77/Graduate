package com.whut.tomasyao.base.dao;

import java.util.Set;

/**
 * edu.whut.change.base.dao
 * Created by YTY on 2016/6/8.
 */
public interface IRedisDao<K,V> {

    void put(K key, V value);

    void put(K key, V value, long milliseconds);

    void delete(K key);

    V get(K key);

    Long increment(K key);

    void putToSet(K key, V... value);

    V removeFromSet(K key);

    Long getSize(K key);

    Set keys(K pattern);
}
