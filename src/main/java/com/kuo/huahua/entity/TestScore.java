package com.kuo.huahua.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:50:58
 */
@Data
@TableName("cea_test_score")
public class TestScore {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cea_test_score_id", type = IdType.AUTO)
    private Long ceaTestScoreId;

	@TableField("cea_term")
	private String ceaTerm;

	@TableField("cea_class_name")
	private String className;

	@TableField("cea_student_id")
    private Long ceaStudentId;

    /**
     * 学科
     */
	@TableField("cea_kind")
    private String ceaKind;

    /**
     * 测试内容
     */
	@TableField("cea_test_title")
    private String ceaTestTitle;

	@TableField("cea_score")
    private Float ceaScore;

}