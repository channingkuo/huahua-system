package com.kuo.huahua.service;

import com.alibaba.fastjson.JSONObject;
import com.kuo.huahua.dto.UploadScoreDto;
import com.kuo.huahua.entity.TestScore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kuo.huahua.utils.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:50:58
 */
public interface ITestScoreService extends IService<TestScore> {

	void upload(List<UploadScoreDto> uploadScoreDtoList) throws ServiceException;

	List<Map<String, Object>> getList(JSONObject payload) throws ServiceException;

	void saveScore(JSONObject payload) throws ServiceException;
}
