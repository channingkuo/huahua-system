package com.kuo.huahua.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuo.huahua.entity.Teacher;
import com.kuo.huahua.mapper.TeacherMapper;
import com.kuo.huahua.service.ITeacherService;
import com.kuo.huahua.utils.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Base64;

/**
 * @author Channing Kuo
 * @date 2021-06-28 17:33:55
 * @describe 服务实现类
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public String login(String username, String password) throws ServiceException {
        if (StringUtils.isBlank(username)) {
            throw new ServiceException("用户名不能为空！");
        }
        if (StringUtils.isBlank(password)) {
            throw new ServiceException("密码错误！");
        }
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("cea_teacher_account", username);
        Teacher teacher = this.getOne(wrapper);
        if (null == teacher) {
            throw new ServiceException("用户名错误！");
        }
        if (!teacher.getCeaTeacherPassword().equalsIgnoreCase(password)) {
            throw new ServiceException("密码错误！");
        }
        String result = JSONObject.toJSONString(teacher);
        return Base64.getEncoder().encodeToString(result.getBytes());
    }

    @Override
    public boolean checkToken(String token) {
        try {
            if (StringUtils.isBlank(token)) {
                return false;
            }
            String teacherJson = new String(Base64.getDecoder().decode(token));
            Teacher teacher = JSONObject.parseObject(teacherJson, Teacher.class);
            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            wrapper.eq("cea_teacher_account", teacher.getCeaTeacherAccount());
            wrapper.eq("cea_teacher_password", teacher.getCeaTeacherPassword());
            Teacher teacherExist = this.getOne(wrapper);
            return null != teacherExist;
        } catch (Exception e) {
            return false;
        }
    }
}
