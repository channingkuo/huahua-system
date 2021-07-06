package com.kuo.huahua.dto;

import com.kuo.huahua.entity.Student;
import lombok.Data;

/**
 * @author Channing Kuo
 * @date 2021/6/29
 */
@Data
public class ClassStudentDto extends Student {

	private String ceaClassName;

	public ClassStudentDto(String ceaClassName, Student student) {
		this.ceaClassName = ceaClassName;

		this.setCeaStudentId(student.getCeaStudentId());
		this.setCeaClassId(student.getCeaClassId());
		this.setCeaStudentName(student.getCeaStudentName());
		this.setCeaStudentGender(student.getCeaStudentGender());
		this.setCeaStudentStatus(student.getCeaStudentStatus());
	}
}
