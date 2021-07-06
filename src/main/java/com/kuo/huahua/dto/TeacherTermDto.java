package com.kuo.huahua.dto;

import lombok.Data;

/**
 * @author Channing Kuo
 * @date 2021/7/1
 */
@Data
public class TeacherTermDto {

	private String className;

	private String studentName;

	private String term;

	private Long teacherId;
}
