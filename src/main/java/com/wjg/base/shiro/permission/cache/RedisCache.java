package com.wjg.base.shiro.permission.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.ServletRequestUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Created by wjg on 2017/6/12.
 */
public class RedisCache<K,V> implements Cache<K,V> {

    private RedisTemplate<K,V> redisTemplate;

    private final static  String KEY_PREFIX = "shiro_redis_session:";

    public RedisCache(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public V get(K k) throws CacheException {
        return redisTemplate.opsForValue().get(getKey(k));
    }

    @Override
    public V put(K k, V v) throws CacheException {
        redisTemplate.opsForValue().set(getKey(k),v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V v= get(getKey(k));
        redisTemplate.delete(getKey(k));
        return v;
    }

    @Override
    public void clear() throws CacheException {
        //实现有何用
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys((K)(KEY_PREFIX+"*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        if(!CollectionUtils.isEmpty(keys)){
            List<V> values = new ArrayList<>();
            return keys.stream().map(k -> redisTemplate.opsForValue().get(k)).collect(toList());
        }
        return null;
    }


    private K getKey(K k){
        if(k instanceof String){
            return (K)(KEY_PREFIX+k);
        }
        return  k;
    }
}
