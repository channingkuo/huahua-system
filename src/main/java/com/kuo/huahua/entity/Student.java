package com.kuo.huahua.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:49:58
 */
@Data
@TableName("cea_student")
public class Student {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cea_student_id", type = IdType.AUTO)
    private Long ceaStudentId;

	@TableField("cea_class_id")
    private Long ceaClassId;

	@TableField("cea_student_name")
    private String ceaStudentName;

    /**
     * 0:male,1:female
     */
	@TableField("cea_student_gender")
    private Integer ceaStudentGender;

    /**
     * 0:disable,1:enable
     */
	@TableField("cea_student_status")
    private Integer ceaStudentStatus;

}