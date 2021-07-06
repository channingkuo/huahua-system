package com.kuo.huahua.controller;

import com.alibaba.fastjson.JSONObject;
import com.kuo.huahua.dto.ClassStudentDto;
import com.kuo.huahua.entity.Class;
import com.kuo.huahua.utils.Result;
import com.kuo.huahua.utils.ResultGenerator;
import org.springframework.web.bind.annotation.*;
import com.kuo.huahua.service.IClassService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:49:32
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    @Resource
    private IClassService classService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result classList(@RequestBody JSONObject payload) {
        try {
            String className = payload.getString("name");
            return ResultGenerator.genSuccessResult(classService.classList(className));
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result saveClass(@RequestBody Class classPayload) {
        try {
            classService.saveClass(classPayload);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/delete/{classId}", method = RequestMethod.POST)
    public Result saveClass(@PathVariable Long classId) {
        try {
            classService.deleteClass(classId);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/student/list/{classId}/{name}", method = RequestMethod.POST)
    public Result studentListInClass(@PathVariable Long classId, @PathVariable String name) {
        try {
            List<ClassStudentDto> list = classService.studentListInClass(classId, name);
            return ResultGenerator.genSuccessResult(list);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}