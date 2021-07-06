package com.kuo.huahua.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.kuo.huahua.dto.TeacherTermDto;
import com.kuo.huahua.dto.UploadStudentDto;
import com.kuo.huahua.entity.Class;
import com.kuo.huahua.entity.Student;
import com.kuo.huahua.mapper.StudentMapper;
import com.kuo.huahua.service.IClassService;
import com.kuo.huahua.service.IStudentService;
import com.kuo.huahua.utils.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:49:58
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private IClassService classService;

    @Override
    public List<Map<String, Object>> getStudentList(String className, String studentName) throws ServiceException {
        TeacherTermDto payload = new TeacherTermDto();
        payload.setClassName(className);
        payload.setStudentName(studentName);
        return studentMapper.getStudentList(payload);
    }

    @Override
    public void deleteStudent(Long studentId) throws ServiceException {
        if (null == studentId) {
            throw new ServiceException("学生Id丢失！");
        }
        Student student = this.getById(studentId);
        if (null == student) {
            throw new ServiceException("找不到对应的学生！");
        }
        this.removeById(studentId);
    }

    @Override
    public List<UploadStudentDto> uploadStudents(List<UploadStudentDto> studentList) throws ServiceException {
        List<UploadStudentDto> errorList = Lists.newArrayList();
        studentList.forEach(student -> {
            List<Class> classList = classService.classList(student.getClassName());

            if (CollectionUtils.isEmpty(classList)) {
                errorList.add(student);
            } else {
                Class classEntity = classList.get(0);
                Student entity = new Student();
                entity.setCeaClassId(classEntity.getCeaClassId());
                entity.setCeaStudentName(student.getName());
                entity.setCeaStudentGender("男".equals(student.getGender()) ? 0 : 1);
                entity.setCeaStudentStatus(1);

                boolean result = this.save(entity);
                if (!result) {
                    errorList.add(student);
                }
            }
        });

        return errorList;
    }

    @Override
    public void saveStudent(Student student) throws ServiceException {
        if (null == student.getCeaClassId()) {
            throw new ServiceException("班级不能为空！");
        }
        if (StringUtils.isBlank(student.getCeaStudentName())) {
            throw new ServiceException("姓名不能为空！");
        }
        if (null == student.getCeaStudentGender()) {
            throw new ServiceException("性别不能为空！");
        }
        if (null == student.getCeaStudentId()) {
            this.save(student);
        } else {
            Student entity = this.getById(student.getCeaStudentId());
            if (null == entity) {
                throw new ServiceException("找不到对应的学生！");
            }
            this.updateById(student);
        }
    }
}
