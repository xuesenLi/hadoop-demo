package com.lxs.bigdata.hdfs;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义上下文， 其实就是缓存 。
 * @author Mr.Li
 * @date 2020/6/22 - 22:10
 */
public class mapContext {
    private Map<Object, Object> cacheMap = new HashMap<>();

    public Map<Object, Object> getCacheMap(){
        return cacheMap;
    }

    /**
     * 判断是否存在当前  key
     * @param key
     * @return
     */
    public boolean containsKey(Object key){
        return cacheMap.containsKey(key);
    }

    /**
     * 写数据到缓存中去
     * @param key   单词
     * @param value 次数
     */
    public void write(Object key, Object value){
        cacheMap.put(key, value);
    }

    public Object get(Object key){
        return cacheMap.get(key);
    }
}
