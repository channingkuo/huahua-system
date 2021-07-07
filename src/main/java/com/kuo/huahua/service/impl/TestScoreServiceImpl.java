package com.kuo.huahua.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.kuo.huahua.dto.TeacherTermDto;
import com.kuo.huahua.dto.UploadScoreDto;
import com.kuo.huahua.entity.Class;
import com.kuo.huahua.entity.Student;
import com.kuo.huahua.entity.TeacherTerm;
import com.kuo.huahua.service.IClassService;
import com.kuo.huahua.service.IStudentService;
import com.kuo.huahua.service.ITeacherTermService;
import com.kuo.huahua.utils.ServiceException;
import com.kuo.huahua.entity.TestScore;
import com.kuo.huahua.mapper.TestScoreMapper;
import com.kuo.huahua.service.ITestScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:50:58
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class TestScoreServiceImpl extends ServiceImpl<TestScoreMapper, TestScore> implements ITestScoreService {

    @Resource
    private TestScoreMapper testScoreMapper;

    @Resource
    private ITeacherTermService teacherTermService;

    @Resource
    private IClassService classService;

    @Resource
    private IStudentService studentService;

    @Override
    public void upload(List<UploadScoreDto> uploadScoreDtoList) throws ServiceException {
        List<TestScore> testScoreList = Lists.newArrayList();

        uploadScoreDtoList.forEach(uploadScoreDto -> {
            TestScore testScore = new TestScore();
            testScore.setCeaTerm(uploadScoreDto.getTerm());
            testScore.setCeaKind(uploadScoreDto.getKind());
            testScore.setClassName(uploadScoreDto.getClassName());
            testScore.setCeaTestTitle(uploadScoreDto.getTestName());
            testScore.setCeaScore(uploadScoreDto.getScore());

            QueryWrapper<Class> classQueryWrapper = new QueryWrapper<>();
            classQueryWrapper.eq("cea_class_name", uploadScoreDto.getClassName());
            Class classEntity = classService.getOne(classQueryWrapper);

            QueryWrapper<TeacherTerm> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("cea_student_name", uploadScoreDto.getName());
            queryWrapper.eq("cea_term", uploadScoreDto.getTerm());
            queryWrapper.eq("cea_class_id", classEntity.getCeaClassId());
            TeacherTerm teacherTerm = teacherTermService.getOne(queryWrapper);

            testScore.setCeaStudentId(teacherTerm.getCeaStudentId());

            testScoreList.add(testScore);
        });

        this.saveBatch(testScoreList);
    }

    @Override
    public List<Map<String, Object>> getList(JSONObject payload) throws ServiceException {
        TeacherTermDto queryPayload = new TeacherTermDto();
        queryPayload.setTerm(payload.getString("term"));
        queryPayload.setClassName(payload.getString("className"));
        queryPayload.setStudentName(payload.getString("studentName"));

        return testScoreMapper.getScoreList(queryPayload);
    }

    @Override
    public void saveScore(JSONObject payload) throws ServiceException {
        String term = payload.getString("ceaTerm");
        String studentName = payload.getString("ceaStudentName");
        String className = payload.getString("ceaClassName");
        String kind = payload.getString("ceaKind");

        if (StringUtils.isBlank(term)) {
            throw new ServiceException("学期不能为空！");
        }
        if (StringUtils.isBlank(studentName)) {
            throw new ServiceException("姓名不能为空！");
        }
        if (StringUtils.isBlank(className)) {
            throw new ServiceException("班级不能为空！");
        }
        if (StringUtils.isBlank(kind)) {
            throw new ServiceException("学科不能为空！");
        }

        QueryWrapper<Class> classQueryWrapper = new QueryWrapper<>();
        classQueryWrapper.eq("cea_class_name", className);
        Class classItem = classService.getOne(classQueryWrapper);

        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("cea_class_id", classItem.getCeaClassId());
        studentQueryWrapper.eq("cea_student_name", studentName);
        Student student = studentService.getOne(studentQueryWrapper);

        payload.remove("ceaTerm");
        payload.remove("ceaStudentName");
        payload.remove("ceaClassName");
        payload.remove("ceaKind");

        Set<String> keySets = payload.keySet();

        JSONObject testScore = new JSONObject();
        for (String key: keySets) {
            JSONObject test = payload.getJSONObject(key);
            Set<String> testKeySets = test.keySet();
            for (String testKey: testKeySets) {
                Float score = test.getFloat(testKey);

                String newKey = key + "." + testKey;

                testScore.put(newKey, score);
            }
        }

        Set<String> testKeySets = testScore.keySet();
        for (String key: testKeySets) {
            QueryWrapper<TestScore> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("cea_term", term);
            queryWrapper.eq("cea_class_name", className);
            queryWrapper.eq("cea_kind", kind);
            queryWrapper.eq("cea_student_id", student.getCeaStudentId());
            queryWrapper.eq("cea_test_title", key);

            TestScore entity = this.getOne(queryWrapper);
            if (null == entity) {
                throw new ServiceException("数据有误，请联系Kuo (♥ω♥)~♪ ！");
            }
            entity.setCeaScore(testScore.getFloat(key));
            this.updateById(entity);
        }
    }

    @Override
    public JSONObject overViewChart(UploadScoreDto payload, Long teacherId) throws ServiceException {
        JSONObject result = new JSONObject();
        if (null == teacherId) {
            throw new ServiceException("老师Id丢失！");
        }
        if (StringUtils.isBlank(payload.getTerm())) {
            throw new ServiceException("学期不能为空！");
        }
        if (StringUtils.isBlank(payload.getClassName())) {
            throw new ServiceException("班级不能为空！");
        }
        if (StringUtils.isBlank(payload.getKind())) {
            throw new ServiceException("学科不能为空！");
        }

        List<Map<String, String>> testNameList = testScoreMapper.getTestNameList(payload);
        List<String> testNames = Lists.newArrayList();
        testNameList.forEach(testName -> testNames.add(testName.get("cea_test_title")));
        result.put("testNames", testNames);

        TeacherTermDto teacherTermDto = new TeacherTermDto();
        teacherTermDto.setTerm(payload.getTerm());
        teacherTermDto.setClassName(payload.getClassName());
        teacherTermDto.setTeacherId(teacherId);
        teacherTermDto.setStudentName("");
        List<Map<String, Object>> teacherTermList = teacherTermService.getTeacherTermStudentList(teacherTermDto);
        List<String> teacherTerms = Lists.newArrayList();
        List<Long> teacherTermsStudentIdList = Lists.newArrayList();
        teacherTermList.forEach(teacherTerm -> {
            if (Integer.valueOf(teacherTerm.get("cea_student_status").toString()) == 1) {
                teacherTerms.add(teacherTerm.get("cea_student_name").toString());
                teacherTermsStudentIdList.add(Long.valueOf(teacherTerm.get("cea_student_id").toString()));
            }
        });
        result.put("studentNames", teacherTerms);

        testNames.forEach(testName -> {
            QueryWrapper<TestScore> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("cea_term", payload.getTerm());
            queryWrapper.eq("cea_class_name", payload.getClassName());
            queryWrapper.eq("cea_kind", payload.getKind());
            queryWrapper.eq("cea_test_title", testName);
            List<TestScore> testScoreList = this.list(queryWrapper);

            List<Float> testScores = Lists.newArrayList();
            teacherTermsStudentIdList.forEach(studentId -> {
                testScoreList.forEach(testScore -> {
                    if (studentId.equals(testScore.getCeaStudentId())) {
                        testScores.add(testScore.getCeaScore());
                    }
                });
            });
            result.put(testName, testScores);
        });

        // 计算每位学生的所有考试平均分
        List<Float> avgScoreList = Lists.newArrayList();
        teacherTermsStudentIdList.forEach(studentId -> {
            Float sumScore = 0F;
            for (String testName: testNames) {
                QueryWrapper<TestScore> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("cea_term", payload.getTerm());
                queryWrapper.eq("cea_class_name", payload.getClassName());
                queryWrapper.eq("cea_kind", payload.getKind());
                queryWrapper.eq("cea_test_title", testName);
                queryWrapper.eq("cea_student_id", studentId);
                TestScore score = this.getOne(queryWrapper);

                sumScore += score.getCeaScore();
            }
            avgScoreList.add(sumScore / testNames.size());
        });
        result.put("平均分", avgScoreList);

        return result;
    }

    @Override
    public JSONObject singleTestChart(UploadScoreDto payload, Long teacherId) throws ServiceException {
        JSONObject result = new JSONObject();
        if (null == teacherId) {
            throw new ServiceException("老师Id丢失！");
        }
        if (StringUtils.isBlank(payload.getTerm())) {
            throw new ServiceException("学期不能为空！");
        }
        if (StringUtils.isBlank(payload.getClassName())) {
            throw new ServiceException("班级不能为空！");
        }
        if (StringUtils.isBlank(payload.getTestName())) {
            throw new ServiceException("测试内容不能为空！");
        }
        if (StringUtils.isBlank(payload.getKind())) {
            throw new ServiceException("学科不能为空！");
        }

        List<Map<String, Object>> testScoreList = testScoreMapper.getTestScoreList(payload);
        List<String> studentList = Lists.newArrayList();
        List<Float> scoreList = Lists.newArrayList();

        float sumScore = 0F;
        for (Map<String, Object> testScore: testScoreList) {
            studentList.add(testScore.get("cea_student_name").toString());
            scoreList.add(Float.valueOf(testScore.get("cea_score").toString()));

            sumScore += Float.parseFloat(testScore.get("cea_score").toString());
        }

        result.put("studentList", studentList);
        result.put("scoreList", scoreList);
        Comparator<Float> comparator = Comparator.comparing(Float::floatValue);
        result.put("max", scoreList.stream().max(comparator));
        result.put("min", scoreList.stream().min(comparator));
        result.put("avg", sumScore == 0 ? 0 : sumScore / studentList.size());

        return result;
    }

    @Override
    public JSONObject singleStudentTestChart(UploadScoreDto payload, Long teacherId) throws ServiceException {
        JSONObject result = new JSONObject();
        if (null == teacherId) {
            throw new ServiceException("老师Id丢失！");
        }
        if (StringUtils.isBlank(payload.getTerm())) {
            throw new ServiceException("学期不能为空！");
        }
        if (StringUtils.isBlank(payload.getClassName())) {
            throw new ServiceException("班级不能为空！");
        }
        if (StringUtils.isBlank(payload.getName())) {
            throw new ServiceException("学生不能为空！");
        }
        if (StringUtils.isBlank(payload.getKind())) {
            throw new ServiceException("学科不能为空！");
        }

        List<Map<String, Object>> studentTestScoreList = testScoreMapper.getStudentTestScoreList(payload);

        List<String> testNameList = Lists.newArrayList();
        List<Float> scoreList = Lists.newArrayList();

        float sumScore = 0F;
        for (Map<String, Object> testScore: studentTestScoreList) {
            testNameList.add(testScore.get("cea_test_title").toString());
            scoreList.add(Float.valueOf(testScore.get("cea_score").toString()));

            sumScore += Float.parseFloat(testScore.get("cea_score").toString());
        }

        result.put("testNames", testNameList);
        result.put("scoreList", scoreList);
        Comparator<Float> comparator = Comparator.comparing(Float::floatValue);
        result.put("max", scoreList.stream().max(comparator));
        result.put("min", scoreList.stream().min(comparator));
        result.put("avg", sumScore == 0 ? 0 : sumScore / testNameList.size());

        return result;
    }
}
