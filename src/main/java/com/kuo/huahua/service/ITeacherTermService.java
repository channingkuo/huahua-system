package com.kuo.huahua.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kuo.huahua.dto.TeacherTermDto;
import com.kuo.huahua.entity.TeacherTerm;
import com.kuo.huahua.utils.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * @author Channing Kuo
 * @date 2021-07-01 15:54:19
 */
public interface ITeacherTermService extends IService<TeacherTerm> {

	List<Map<String, Object>> getTeacherTermStudentList(TeacherTermDto payload) throws ServiceException;

	List<Map<String, Object>> getClassList(Long teacherId, String term) throws ServiceException;

	void toggleTeacherTermStudent(Long termId) throws ServiceException;

	void newTerm(Long teacherId, String term, String className) throws ServiceException;

	void deleteTerm(Long termId) throws ServiceException;

	void saveSingleTerm(JSONObject payload) throws ServiceException;
}
