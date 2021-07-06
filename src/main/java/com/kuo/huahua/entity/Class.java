package com.kuo.huahua.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:49:32
 */
@Data
@TableName("cea_class")
public class Class {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cea_class_id", type = IdType.AUTO)
    private Long ceaClassId;

	@TableField("cea_class_name")
    private String ceaClassName;

}