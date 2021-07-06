package com.kuo.huahua.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.kuo.huahua.dto.TeacherTermDto;
import com.kuo.huahua.entity.Class;
import com.kuo.huahua.entity.Student;
import com.kuo.huahua.entity.TeacherTerm;
import com.kuo.huahua.mapper.TeacherTermMapper;
import com.kuo.huahua.service.IClassService;
import com.kuo.huahua.service.IStudentService;
import com.kuo.huahua.service.ITeacherTermService;
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
 * @date 2021-07-01 15:54:19
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class TeacherTermServiceImpl extends ServiceImpl<TeacherTermMapper, TeacherTerm> implements ITeacherTermService {

    @Resource
    private TeacherTermMapper teacherTermMapper;

    @Resource
    private IClassService classService;

    @Resource
    private IStudentService studentService;

    @Override
    public List<Map<String, Object>> getTeacherTermStudentList(TeacherTermDto payload) throws ServiceException {
        return teacherTermMapper.getTeacherTermStudentList(payload);
    }

    @Override
    public List<Map<String, Object>> getClassList(Long teacherId, String term) throws ServiceException {
        if (null == teacherId) {
            throw new ServiceException("教师Id丢失！");
        }
        if (StringUtils.isBlank(term)) {
            throw new ServiceException("学期不能为空！");
        }
        TeacherTermDto payload = new TeacherTermDto();
        payload.setTerm(term);
        payload.setTeacherId(teacherId);
        return teacherTermMapper.getClassList(payload);
    }

    @Override
    public void toggleTeacherTermStudent(Long termId) throws ServiceException {
        if (null == termId) {
            throw new ServiceException("记录Id丢失！");
        }

        TeacherTerm teacherTerm = this.getById(termId);
        if (null == teacherTerm) {
            throw new ServiceException("找不到记录！");
        }
        if (teacherTerm.getCeaStudentStatus() == 0) {
            teacherTerm.setCeaStudentStatus(1);
        } else {
            teacherTerm.setCeaStudentStatus(0);
        }
        this.updateById(teacherTerm);
    }

    @Override
    public void newTerm(Long teacherId, String term, String className) throws ServiceException {
        if (null == teacherId) {
            throw new ServiceException("老师Id丢失！");
        }
        if (StringUtils.isBlank(term)) {
            throw new ServiceException("学期不能为空！");
        }
        if (StringUtils.isBlank(className)) {
            throw new ServiceException("班级不能为空！");
        }

        List<Class> classList = classService.classList(className);
        Class newClass;
        if (CollectionUtils.isEmpty(classList)) {
            // 新建班级
            Class classEntity = new Class();
            classEntity.setCeaClassName(className);
            classService.saveClass(classEntity);

            QueryWrapper<Class> tmp = new QueryWrapper<>();
            tmp.eq("cea_class_name", className);
            newClass = classService.getOne(tmp);

            // step 3: 若是新建的班级，把原班级的学生带到新建的班级(新建学生，不是修改原有的学生)
            List<Class> fullClassList = classService.classList("");
            Long topClassId = fullClassList.get(1).getCeaClassId();
            String topClassName = fullClassList.get(1).getCeaClassName();

            List<Map<String, Object>> topStudentList = studentService.getStudentList(topClassName, "");

            List<Student> toSave = Lists.newArrayList();
            topStudentList.forEach(stringObjectMap -> {
                Student student = new Student();
                student.setCeaClassId(newClass.getCeaClassId());
                student.setCeaStudentName(stringObjectMap.get("cea_student_name").toString());
                student.setCeaStudentGender(Integer.valueOf(stringObjectMap.get("cea_student_gender").toString()));
                student.setCeaStudentStatus(1);

                toSave.add(student);
            });
            studentService.saveBatch(toSave);
        } else {
            newClass = classList.get(0);
        }

        // step 1: 检查老师、学期、班级条件下是不是已经有记录
        QueryWrapper<TeacherTerm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cea_teacher_id", teacherId);
        queryWrapper.eq("cea_term", term);
        queryWrapper.eq("cea_class_id", newClass.getCeaClassId());
        List<TeacherTerm> teacherTermList = this.list(queryWrapper);
        if (!CollectionUtils.isEmpty(teacherTermList)) {
            throw new ServiceException(term + "已经有记录了！");
        }
        // step 4: 根据班级和学生新建学期记录
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("cea_class_id", newClass.getCeaClassId());
        List<Student> studentList = studentService.list(studentQueryWrapper);

        List<TeacherTerm> newTeacherTermList = Lists.newArrayList();
        studentList.forEach(student -> {
            TeacherTerm teacherTerm = new TeacherTerm();
            teacherTerm.setCeaClassId(newClass.getCeaClassId());
            teacherTerm.setCeaTeacherId(teacherId);
            teacherTerm.setCeaTerm(term);
            teacherTerm.setCeaStudentId(student.getCeaStudentId());
            teacherTerm.setCeaStudentName(student.getCeaStudentName());
            teacherTerm.setCeaStudentGender(student.getCeaStudentGender());
            teacherTerm.setCeaStudentStatus(student.getCeaStudentStatus());

            newTeacherTermList.add(teacherTerm);
        });

        this.saveBatch(newTeacherTermList);
    }

    @Override
    public void deleteTerm(Long termId) throws ServiceException {
        if (null == termId) {
            throw new ServiceException("学期学生Id丢失！");
        }
        TeacherTerm teacherTerm = this.getById(termId);
        if (null == teacherTerm) {
            throw new ServiceException("找不到对应的学期学生！");
        }
        this.removeById(termId);
    }

    @Override
    public void saveSingleTerm(JSONObject payload) throws ServiceException {
        //ceaTerm/ceaClassId/ceaStudentName/ceaStudentGender/ceaStudentStatus
        Long teacherId = payload.getLong("teacherId");
        String term = payload.getString("ceaTerm");
        Long classId = payload.getLong("ceaClassId");
        String studentName = payload.getString("ceaStudentName");
        Integer gender = payload.getInteger("ceaStudentGender");
        Integer status = payload.getInteger("ceaStudentStatus");

        if (null == teacherId) {
            throw new ServiceException("老师Id丢失！");
        }
        if (StringUtils.isBlank(term)) {
            throw new ServiceException("学期不能为空！");
        }
        if (null == classId) {
            throw new ServiceException("班级Id丢失！");
        }
        if (StringUtils.isBlank(studentName)) {
            throw new ServiceException("学生姓名不能为空！");
        }
        if (null == gender) {
            throw new ServiceException("性别不能用空！");
        }
        if (null == status) {
            throw new ServiceException("状态不能用空！");
        }

        TeacherTerm entity = new TeacherTerm();
        entity.setCeaTeacherId(teacherId);
        entity.setCeaTerm(term);
        entity.setCeaClassId(classId);

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cea_class_id", classId);
        queryWrapper.eq("cea_student_name", studentName);
        Student student = studentService.getOne(queryWrapper);

        entity.setCeaStudentId(student.getCeaStudentId());
        entity.setCeaStudentName(studentName);
        entity.setCeaStudentGender(gender);
        entity.setCeaStudentStatus(status);

        this.save(entity);
    }
}
