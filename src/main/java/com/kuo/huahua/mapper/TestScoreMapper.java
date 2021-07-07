package com.kuo.huahua.mapper;

import com.kuo.huahua.dto.TeacherTermDto;
import com.kuo.huahua.dto.UploadScoreDto;
import com.kuo.huahua.entity.TestScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuo.huahua.utils.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:50:58
 */
public interface TestScoreMapper extends BaseMapper<TestScore> {

	List<Map<String, Object>> getScoreList(TeacherTermDto payload);

	List<Map<String, String>> getTestNameList(UploadScoreDto payload);

	List<Map<String, Object>> getTestScoreList(UploadScoreDto payload);

	List<Map<String, Object>> getStudentTestScoreList(UploadScoreDto payload);
}