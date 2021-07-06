package com.kuo.huahua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuo.huahua.entity.Teacher;
import com.kuo.huahua.utils.ServiceException;

/**
 * @author Channing Kuo
 * @date 2021-06-28 17:33:55
 * @describe Service类
 */
public interface ITeacherService extends IService<Teacher> {

	/**
	 * login
	 * @param username 用户名
	 * @param password 密码
	 * @return token
	 * @throws ServiceException ServiceException
	 */
	String login(String username, String password) throws ServiceException;

	/**
	 * token 简单验证
	 * @param token token
	 * @return boolean
	 */
	boolean checkToken(String token);
}
