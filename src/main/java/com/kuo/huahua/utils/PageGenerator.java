package com.kuo.huahua.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author ChanningKuo
 * @date 2020/12/23
 */
public class PageGenerator {

    public static <T> Page genPageInfo(List<T> data) {
        return new Page()
                .setCount(data.size())
                .setList(data);
    }

    public static <T> Page genPageInfo(IPage<T> data) {
        return new Page()
                .setCount(data.getTotal())
                .setList(data.getRecords());
    }
}