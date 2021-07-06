package com.kuo.huahua.controller;

import org.springframework.web.bind.annotation.RestController;
import com.kuo.huahua.service.ITeacherClassService;

import javax.annotation.Resource;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:50:33
 * @describe 
 */
@RestController
public class TeacherClassController {

    @Resource
    private ITeacherClassService teacherClassService;
}