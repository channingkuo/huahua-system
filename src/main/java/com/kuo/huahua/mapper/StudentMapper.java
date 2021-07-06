package com.kuo.huahua.mapper;

import com.kuo.huahua.dto.TeacherTermDto;
import com.kuo.huahua.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:49:58
 * @describe Mapper类
 */
public interface StudentMapper extends BaseMapper<Student> {

	List<Map<String, Object>> getStudentList(TeacherTermDto payload);

}