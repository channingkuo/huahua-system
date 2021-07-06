package com.kuo.huahua.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:50:33
 */
@Data
@TableName("cea_teacher_class")
public class TeacherClass {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cea_teacher_class_id", type = IdType.AUTO)
    private Long ceaTeacherClassId;

	@TableField("cea_teacher_id")
    private Long ceaTeacherId;

	@TableField("cea_class_id")
    private Long ceaClassId;

    /**
     * 0:班主任，1:任课老师
     */
	@TableField("cea_class_rank")
    private Integer ceaClassRank;

    /**
     * 学年：2021、2022、2023...
     */
	@TableField("cea_term")
    private Integer ceaTerm;

}