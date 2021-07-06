package com.kuo.huahua.service.impl;

import com.kuo.huahua.utils.ServiceException;
import com.kuo.huahua.entity.TeacherClass;
import com.kuo.huahua.mapper.TeacherClassMapper;
import com.kuo.huahua.service.ITeacherClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:50:33
 * @describe 服务实现类
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class TeacherClassServiceImpl extends ServiceImpl<TeacherClassMapper, TeacherClass> implements ITeacherClassService {

    @Resource
    private TeacherClassMapper teacherClassMapper;
}
