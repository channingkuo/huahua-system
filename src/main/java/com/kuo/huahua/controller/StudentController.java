package com.kuo.huahua.controller;

import com.alibaba.fastjson.JSONObject;
import com.kuo.huahua.dto.UploadStudentDto;
import com.kuo.huahua.entity.Student;
import com.kuo.huahua.utils.Result;
import com.kuo.huahua.utils.ResultGenerator;
import org.springframework.web.bind.annotation.*;
import com.kuo.huahua.service.IStudentService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:49:58
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private IStudentService studentService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getStudentList(@RequestBody JSONObject payload) {
        try {
            String className = payload.getString("className");
            String studentName = payload.getString("studentName");
            return ResultGenerator.genSuccessResult(studentService.getStudentList(className, studentName));
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/delete/{studentId}", method = RequestMethod.POST)
    public Result deleteStudent(@PathVariable Long studentId) {
        try {
            studentService.deleteStudent(studentId);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result uploadStudents(@RequestBody List<UploadStudentDto> studentList) {
        try {
            List<UploadStudentDto> errorList = studentService.uploadStudents(studentList);
            return ResultGenerator.genSuccessResult(errorList);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result saveStudent(@RequestBody Student student) {
        try {
            studentService.saveStudent(student);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}