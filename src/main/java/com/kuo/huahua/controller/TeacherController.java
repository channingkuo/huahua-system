package com.kuo.huahua.controller;

import com.alibaba.fastjson.JSONObject;
import com.kuo.huahua.utils.Result;
import com.kuo.huahua.utils.ResultGenerator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.kuo.huahua.service.ITeacherService;

import javax.annotation.Resource;

/**
 * @author Channing Kuo
 * @date 2021-06-28 17:33:55
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private ITeacherService teacherService;

    @RequestMapping(value = "/student/list", method = RequestMethod.POST)
    public Result studentList(@RequestBody JSONObject payload) {
        try {
            String studentName = payload.getString("name");
            Integer term = payload.getInteger("term");
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}