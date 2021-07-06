package com.kuo.huahua.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author Channing Kuo
 * @date 2021-06-28 17:33:55
 */
@Data
@TableName("cea_teacher")
public class Teacher {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cea_teacher_id", type = IdType.AUTO)
    private Long ceaTeacherId;

	@TableField("cea_teacher_name")
    private String ceaTeacherName;

	@TableField("cea_teacher_account")
    private String ceaTeacherAccount;

	@TableField("cea_teacher_password")
    private String ceaTeacherPassword;

    /**
     * 学科
     */
	@TableField("cea_teacher_kind")
    private String ceaTeacherKind;

}