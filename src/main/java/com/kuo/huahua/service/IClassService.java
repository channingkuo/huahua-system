package com.kuo.huahua.service;

import com.kuo.huahua.dto.ClassStudentDto;
import com.kuo.huahua.entity.Class;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kuo.huahua.utils.ServiceException;

import java.util.List;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:49:32
 */
public interface IClassService extends IService<Class> {

	/**
	 * 班级列表
	 * @param className 班级名称
	 * @return List
	 * @throws ServiceException ServiceException
	 */
	List<Class> classList(String className) throws ServiceException;

	/**
	 * 新建保存班级
	 * @param classPayload 班级参数
	 * @throws ServiceException ServiceException
	 */
	void saveClass(Class classPayload) throws ServiceException;

	/**
	 * 删除班级，只有未被使用的班级才能删除
	 * @param classId 班级Id
	 * @throws ServiceException ServiceException
	 */
	void deleteClass(Long classId) throws ServiceException;

	/**
	 * 获取班级下面所有的学生
	 * @param classId 班级Id
	 * @param studentName 学生名字
	 * @return ClassStudentDto
	 * @throws ServiceException ServiceException
	 */
	List<ClassStudentDto> studentListInClass(Long classId, String studentName) throws ServiceException;
}
