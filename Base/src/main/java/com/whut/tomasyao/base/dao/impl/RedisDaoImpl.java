package com.whut.tomasyao.base.dao.impl;

import com.whut.tomasyao.base.dao.IRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * edu.whut.change.base.dao.impl
 * Created by YTY on 2016/6/8.
 */
@Component("RedisDaoImpl")
public class RedisDaoImpl<K, V> implements IRedisDao<K, V> {
    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    public RedisDaoImpl() {
    }

    @Override
    public void put(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void put(K key, V value, long seconds) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void delete(K key) {
        redisTemplate.delete(key);
    }

    @Override
    public V get(K key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Long increment(K key){
        return redisTemplate.opsForValue().increment(key,1);
    }

    @Override
    public void putToSet(K key, V... value) {
        redisTemplate.opsForSet().add(key,value);
    }

    @Override
    public V removeFromSet(K key) {
        V value = redisTemplate.opsForSet().randomMember(key);
        redisTemplate.opsForSet().remove(key,value);
        return value;
    }

    @Override
    public Long getSize(K key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public Set<K> keys(K pattern) {
        return redisTemplate.keys(pattern);
    }


}
