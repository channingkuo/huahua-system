package com.kuo.huahua.controller;

import com.alibaba.fastjson.JSONObject;
import com.kuo.huahua.service.ITeacherService;
import com.kuo.huahua.utils.Result;
import com.kuo.huahua.utils.ResultGenerator;
import com.kuo.huahua.utils.ServiceException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Channing Kuo
 * @date 2021/6/28
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Resource
	private ITeacherService teacherService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(@RequestBody JSONObject payload) {
		try {
			String username = payload.getString("username");
			String password = payload.getString("password");
			String token = teacherService.login(username, password);
			return ResultGenerator.genSuccessResult(token);
		} catch (Exception e) {
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
}
