package com.kuo.huahua.controller;

import com.alibaba.fastjson.JSONObject;
import com.kuo.huahua.dto.TeacherTermDto;
import com.kuo.huahua.service.ITeacherTermService;
import com.kuo.huahua.utils.Result;
import com.kuo.huahua.utils.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Channing Kuo
 * @date 2021-07-01 15:54:19
 */
@RestController
@RequestMapping("/teacher/term")
public class TeacherTermController {

    @Resource
    private ITeacherTermService teacherTermService;

    @RequestMapping(value = "/student/list", method = RequestMethod.POST)
    public Result getTeacherTermStudentList(@RequestBody TeacherTermDto payload) {
        try {
            List<Map<String, Object>> list = teacherTermService.getTeacherTermStudentList(payload);
            return ResultGenerator.genSuccessResult(list);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/class/list/{teacherId}/{term}", method = RequestMethod.POST)
    public Result getClassList(@PathVariable Long teacherId, @PathVariable String term) {
        try {
            List<Map<String, Object>> list = teacherTermService.getClassList(teacherId, term);
            return ResultGenerator.genSuccessResult(list);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/toggle/{termId}", method = RequestMethod.POST)
    public Result toggleTeacherTermStudent(@PathVariable Long termId) {
        try {
            teacherTermService.toggleTeacherTermStudent(termId);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Result newTerm(@RequestBody JSONObject payload) {
        try {
            Long teacherId = payload.getLong("teacherId");
            String term = payload.getString("term");
            String className = payload.getString("className");
            teacherTermService.newTerm(teacherId, term, className);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/delete/{termId}", method = RequestMethod.POST)
    public Result deleteTerm(@PathVariable Long termId) {
        try {
            teacherTermService.deleteTerm(termId);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result saveSingleTerm(@RequestBody JSONObject payload) {
        try {
            teacherTermService.saveSingleTerm(payload);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}