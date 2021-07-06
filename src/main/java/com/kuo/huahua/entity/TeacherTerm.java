package com.kuo.huahua.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author Channing Kuo
 * @date 2021-07-01 15:54:19
 */
@Data
@TableName("cea_teacher_term")
public class TeacherTerm {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cea_teacher_term_id", type = IdType.AUTO)
    private Long ceaTeacherTermId;

	@TableField("cea_teacher_id")
    private Long ceaTeacherId;

	@TableField("cea_term")
    private String ceaTerm;

	@TableField("cea_class_id")
    private Long ceaClassId;

	@TableField("cea_student_id")
    private Long ceaStudentId;

	@TableField("cea_student_name")
    private String ceaStudentName;

	@TableField("cea_student_gender")
    private Integer ceaStudentGender;

	@TableField("cea_student_status")
    private Integer ceaStudentStatus;

}