package com.kuo.huahua.mapper;

import com.kuo.huahua.dto.TeacherTermDto;
import com.kuo.huahua.entity.TeacherTerm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author Channing Kuo
 * @date 2021-07-01 15:54:19
 */
public interface TeacherTermMapper extends BaseMapper<TeacherTerm> {

	/**
	 * 获取学期所有的学生
	 * @param payload 筛选条件
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> getTeacherTermStudentList(TeacherTermDto payload);

	List<Map<String, Object>> getClassList(TeacherTermDto payload);
}