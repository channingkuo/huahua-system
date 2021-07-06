package com.kuo.huahua.service;

import com.kuo.huahua.dto.UploadStudentDto;
import com.kuo.huahua.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kuo.huahua.utils.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:49:58
 */
public interface IStudentService extends IService<Student> {

	List<Map<String, Object>> getStudentList(String className, String studentName) throws ServiceException;

	void deleteStudent(Long studentId) throws ServiceException;

	List<UploadStudentDto> uploadStudents(List<UploadStudentDto> studentList) throws ServiceException;

	void saveStudent(Student student) throws ServiceException;
}
