package com.kuo.huahua.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.kuo.huahua.dto.ClassStudentDto;
import com.kuo.huahua.entity.Class;
import com.kuo.huahua.entity.Student;
import com.kuo.huahua.entity.TeacherClass;
import com.kuo.huahua.mapper.ClassMapper;
import com.kuo.huahua.service.IClassService;
import com.kuo.huahua.service.IStudentService;
import com.kuo.huahua.service.ITeacherClassService;
import com.kuo.huahua.utils.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:49:32
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements IClassService {

    @Resource
    private ClassMapper classMapper;

    @Resource
    private ITeacherClassService teacherClassService;

    @Resource
    private IStudentService studentService;

    @Override
    public List<Class> classList(String className) throws ServiceException {
        QueryWrapper<Class> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(className)) {
            wrapper.like("cea_class_name", className);
        }
        wrapper.orderByDesc("cea_class_name");

        return this.list(wrapper);
    }

    @Override
    public void saveClass(Class classPayload) throws ServiceException {
        if (StringUtils.isBlank(classPayload.getCeaClassName())) {
            throw new ServiceException("班级名称不能为空！");
        }

        QueryWrapper<Class> wrapper = new QueryWrapper<>();
        wrapper.eq("cea_class_name", classPayload.getCeaClassName());
        List<Class> classList = this.list(wrapper);

        if (!CollectionUtils.isEmpty(classList)) {
            throw new ServiceException("班级名称已经存在！");
        }

        if (null == classPayload.getCeaClassId()) {
            Class entity = new Class();
            entity.setCeaClassName(classPayload.getCeaClassName());

            this.save(entity);
        } else {
            Class entity = this.getById(classPayload.getCeaClassId());
            if (null == entity) {
                throw new ServiceException("找不到对应班级！");
            }
            entity.setCeaClassName(classPayload.getCeaClassName());
            this.updateById(entity);
        }
    }

    @Override
    public void deleteClass(Long classId) throws ServiceException {
        if (null == classId) {
            throw new ServiceException("班级Id丢失！");
        }

        Class entity = this.getById(classId);
        if (null == entity) {
            throw new ServiceException("未找到对应班级！");
        }

        // 检查是否有老师关联了班级，有的话不能删除班级
        QueryWrapper<TeacherClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cea_class_id", classId);
        List<TeacherClass> teacherClassList = teacherClassService.list(queryWrapper);

        if (!CollectionUtils.isEmpty(teacherClassList)) {
            throw new ServiceException("请先解除任课老师或班主任，才能删除班级！");
        }

        // 班级删除，关联在班级下面的所有学生自动变成未关联班级
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("cea_class_id", classId);
        List<Student> studentList = studentService.list(studentQueryWrapper);
        if (!CollectionUtils.isEmpty(studentList)) {
            studentList.forEach(student -> student.setCeaClassId(null));
            studentService.updateBatchById(studentList);
        }

        this.removeById(classId);
    }

    @Override
    public List<ClassStudentDto> studentListInClass(Long classId, String studentName) throws ServiceException {
        // studentName: "null" 表示不过滤名字
        if (null == classId) {
            throw new ServiceException("班级Id丢失！");
        }

        Class entity = this.getById(classId);
        if (null == entity) {
            throw new ServiceException("未找到对应班级！");
        }

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cea_class_id", classId);
        if (!"null".equals(studentName)) {
            queryWrapper.eq("cea_student_name", studentName);
        }
        List<Student> studentList = studentService.list(queryWrapper);
        if (CollectionUtils.isEmpty(studentList)) {
            return Lists.newArrayList();
        }
        List<ClassStudentDto> result = Lists.newArrayList();
        studentList.forEach(student -> result.add(new ClassStudentDto(entity.getCeaClassName(), student)));
        return result;
    }
}
