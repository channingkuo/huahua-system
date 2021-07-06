package com.kuo.huahua.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author ChanningKuo
 * @date 2020/12/23
 */
public class Page<T> {
    private long count;
    private T list;

    public Page setCount(long count) {
        this.count = count;
        return this;
    }

    public Page setList(T list) {
        this.list = list;
        return this;
    }

    public long getCount() {
        return count;
    }

    public T getList() {
        return list;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
